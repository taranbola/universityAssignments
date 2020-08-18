from django.shortcuts import render
from django.http import HttpRequest, HttpResponse
from django.views.decorators.csrf import csrf_exempt
from django.views.decorators.http import require_http_methods
from django.contrib.auth import authenticate, login, logout
from django.contrib.auth.models import User
from app.models import Story, Author
import json
import datetime

@csrf_exempt
@require_http_methods(["POST"])
def HandleLoginRequest (request):
    if request.method == 'POST':
        user = authenticate(username=request.POST.get('username'), password=request.POST.get('password'))
        if user is not None:
            if user.is_active:
                login(request, user)
                # request.session.modified = True
                # request.session.set_expiry(86400)
                return HttpResponse ('Login Successful',status=200, content_type='text/plain')
            else:
                return HttpResponse ('Unauthorized Access for Disabled Accounts',status=403, content_type='text/plain')
        else:
            return HttpResponse ('Unable to Login',status=401, content_type='text/plain')

    else:
        return HttpResponse (status=405, content_type='text/plain')

@csrf_exempt
@require_http_methods(["POST"])
def HandleLogoutRequest (request):
    logout(request)
    return HttpResponse ("Logout Successful. Goodbye",status=200, content_type='text/plain')

@csrf_exempt
@require_http_methods(["POST"])
def HandlePostStoryRequest (request):
    if request.method == 'POST':
        authorValue = Author.objects.get(username=request.user)
        received_json_data = json.loads(request.body.decode("utf-8"))
        if request.user.is_authenticated:
            if(received_json_data.get('category') in ['tech','pol','art','trivia']):
                if(received_json_data.get('region') in ['uk','w','eu']):
                    row = Story(headline=received_json_data.get('headline'), category=received_json_data.get('category'),
                    auth=authorValue,details=received_json_data.get('details'),region=received_json_data.get('region'))
                    row.save()
                    return HttpResponse ("CREATED",status=201, content_type='text/plain')
                else:
                    return HttpResponse ("The region value is invalid",status=503, content_type='text/plain')
            else:
                return HttpResponse ("The category value is invalid",status=503, content_type='text/plain')
        else:
            return HttpResponse ("Not authenticated, unable to post story",status=503, content_type='text/plain')
    else:
        return HttpResponse (status=405,content_type='text/plain')

@require_http_methods(["GET"])
def HandleGetStoryRequest (request):
    if request.method == 'GET':
        received_json_data = json.loads(request.body.decode("utf-8"))
        stories = Story.objects.all()
        if (received_json_data.get('story_cat') != "*"):
            if(received_json_data.get('story_cat') in ['tech','pol','art','trivia']):
                stories = stories.filter(category=received_json_data.get("story_cat"))
            else:
                return HttpResponse("The story_cat value is invalid",status=404, content_type='text/plain')
                pass

        if (received_json_data.get('story_region') != "*"):
            if(received_json_data.get('story_region') in ['uk','w','eu']):
                stories = stories.filter(region=received_json_data.get("story_region"))
            else:
                return HttpResponse("The story_region value is invalid",status=404, content_type='text/plain')
                pass

        if (received_json_data.get('story_date') != "*"):
            try:
                dateval = datetime.datetime.strptime(received_json_data.get('story_date'), "%d/%m/%Y").strftime("%Y-%m-%d")
                stories = stories.filter(date=dateval)
            except ValueError:
                return HttpResponse("The story_date value is invalid",status=404, content_type='text/plain')
                pass

        if stories.count() == 0:
            return HttpResponse("No stories can be found",status=404, content_type='text/plain')
        else:
            array=[]
            for each in stories.values():
                item ={}
                item.update({'key': each['id']})
                item.update({'headline': each['headline']})
                item.update({'story_cat': each['category']})
                item.update({'story_region': each['region']})
                authname = Author.objects.get(user=each['auth_id'])
                item.update({'author': authname.username})
                item.update({'story_date': each['date']})
                item.update({'story_details': each['details']})
                array.append(item)
            payload = {'stories' : array}

            res = HttpResponse (json.dumps(payload,indent=4, default=str))
            res['Content-Type'] = 'application/json'
            res.status_code = 200
            res.reason_phrase = 'OK'
            return res
    else:
        return HttpResponse(status=405)

@csrf_exempt
@require_http_methods(["POST"])
def HandleDeleteStoryRequest (request):
    if request.method == 'POST':
        if request.user.is_authenticated:
            received_json_data = json.loads(request.body.decode("utf-8"))
            instance = Story.objects.filter(pk=received_json_data.get('story_key')).exists()
            if instance == True:
                Story.objects.filter(pk=received_json_data.get('story_key')).delete()
                return HttpResponse ("CREATED",status=201, content_type='text/plain')
            else:
                return HttpResponse ("Service Unavailable",status=503,content_type='text/plain')
        else:
            return HttpResponse ("Not authenticated, unable to delete story",status=503, content_type='text/plain')
    else:
        return HttpResponse (status=405, content_type='text/plain')
