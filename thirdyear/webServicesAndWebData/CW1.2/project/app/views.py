from django.shortcuts import render
from django.http import HttpResponse
from django.contrib.auth.models import User
from django.contrib.auth import authenticate, login, logout
from django.views.decorators.csrf import csrf_exempt
from django.views.decorators.http import require_http_methods
from app.models import Professor, Module, Teaching, Rating
import re
import decimal

#This is the function that handles all the reigster information.
#It checks if the username is already taken else it will add the details
#to the user table.
@csrf_exempt
@require_http_methods(["POST"])
def HandleRegisterRequest(request):
    if User.objects.filter(username=request.POST.get('username')).exists():
        return HttpResponse ('Username Already Taken',status=406, content_type='text/plain')
    else:
        User.objects.create_user(request.POST.get('username'), request.POST.get('email'), request.POST.get('password'))
        return HttpResponse ('Registration Successful',status=200, content_type='text/plain')

#This will make sure the user has logged in. It will make sure the account
#hasn't been disables and it is a valid login and will then login.
#It makes uses of the built in login functions.
@csrf_exempt
@require_http_methods(["POST"])
def HandleLoginRequest(request):
    account = authenticate(username=request.POST.get('username'), password=request.POST.get('password'))
    if account is not None:
        if account.is_active:
            login(request, account)
            return HttpResponse ('Login Successful',status=200, content_type='text/plain')
        else:
            return HttpResponse ('This account is disabled',status=403, content_type='text/plain')
    else:
        return HttpResponse ('Unable to Login',status=401, content_type='text/plain')

#This will simply call the built in logout function, no validation is needed.
@csrf_exempt
@require_http_methods(["POST"])
def HandleLogoutRequest(request):
    logout(request)
    return HttpResponse ("Logout Successful",status=200, content_type='text/plain')

#This will return all the module instances (teaching) and will output all the
#professors. It will get extra information from the module and professor tables
#It will also output in a nice looking format.
@csrf_exempt
@require_http_methods(["GET"])
def HandleListRequest(request):
    if request.user.is_authenticated:
        teaching = Teaching.objects.all()
        string_return = ""
        string_return += str("\n" + "Code" + "Name".center(39) + "Year".center(6) + "Semester".center(12) + "Taught By")
        for each in teaching.values():
            listOfLecturers = {}
            lectureOutputFormat = "| "
            instance = Teaching.objects.get(id=each['id'])
            mod = Module.objects.get(id=each['module_id'])
            for e in instance.professors.all():
                listOfLecturers.update({str(e.professor_code) : str(e.name)})
            for k in listOfLecturers:
                lectureOutputFormat += k + " "
                lectureOutputFormat += listOfLecturers[k] + " | "
            string_return += str("\n" + mod.module_code + mod.mod_name.center(40) + str(instance.year).center(6) + str(instance.semester).center(12) + lectureOutputFormat )
        return HttpResponse(string_return,status=200, content_type='text/plain')
    else:
        return HttpResponse ("Not authenticated, you can't view the list.",status=503, content_type='text/plain')

#It will get all the professors and get all their ratings, then match the ratings
#to the appropriate professor. It will then calculate the average for them.
#This will then be outputted in a nice looking format to the client.
@csrf_exempt
@require_http_methods(["GET"])
def HandleViewRequest(request):
    if request.user.is_authenticated:
        string_return = ""
        professors = Professor.objects.all()
        for each in professors.values():
            counter = 0
            cumulative = 0
            average = 0
            ratings = Rating.objects.all().filter(professor_id=each['id'])
            for eachr in ratings.values():
                cumulative += int(eachr["rating"])
                counter += 1
            if (counter != 0):
                average = decimal.Decimal(cumulative/counter).quantize(decimal.Decimal(0), rounding=decimal.ROUND_HALF_UP)
                # average = round(cumulative / counter)
                string_return += str("\nThe Rating of " + each['name'] + " (" + each['professor_code'] + ") is "+ str(average) + "/5")
            else:
                string_return += str("\n" + each['name'] + " (" + each['professor_code'] + ") is currently unrated")
        return HttpResponse(string_return,status=200, content_type='text/plain')
    else:
        return HttpResponse ("Not authenticated, you can't view the professor ratings.",status=503, content_type='text/plain')

#This will check the professor_id and module id are correct. It will then check
#if that professor teaches that module. Once it finds that it will then look
#for all ratings for that instance. It will then calculate the average, this
#will then be returned back to the client. It will display an appropriate
#message if it isn't rated at all.
@csrf_exempt
@require_http_methods(["GET"])
def HandleAverageRequest(request):
    if request.user.is_authenticated:
        string_return = ""
        moduleinfo = ""
        counter = 0
        cumulative = 0
        average = 0
        if Professor.objects.filter(professor_code=request.GET.get('professor_code')).exists():
            modprof = Professor.objects.get(professor_code=request.GET.get('professor_code'))
            modules = Module.objects.all().filter(module_code=request.GET.get('module_code'))
            if not modules:
                return HttpResponse ("That Module doesn't exist",status=503, content_type='text/plain')
            for each in modules.values():
                moduleinfo = each["mod_name"] + " (" + each['module_code'] +")"
                if Teaching.objects.filter(professors=modprof.id).filter(module_id=each['id']).exists():
                    instance = Teaching.objects.all().filter(professors=modprof.id,module_id=each['id'])
                    for r in instance.values():
                        ratings = Rating.objects.all().filter(teaching_id=r['id']).filter(professor_id=modprof.id)
                        for eachr in ratings.values():
                            cumulative += int(eachr['rating'])
                            counter += 1
            if (counter != 0):
                average = decimal.Decimal(cumulative/counter).quantize(decimal.Decimal(0), rounding=decimal.ROUND_HALF_UP)
                string_return += str("\nThe Rating of " + modprof.name + " (" + modprof.professor_code + ") in module "+ moduleinfo +" is "+ str(average) + "/5")
            else:
                string_return += str("\n" + modprof.name + " (" + modprof.professor_code + ") in module "+ moduleinfo +" is currently unrated.")
            return HttpResponse(string_return,status=200, content_type='text/plain')
        else:
            return HttpResponse ("Professor Code is invalid.",status=503, content_type='text/plain')
    else:
        return HttpResponse ("Not authenticated, you can't get the average of the professors module",status=503, content_type='text/plain')

#First it will check that all the paramaters are valid. It will then check all
#the paramaters together are a valid instance. It will then check that the user
#hasn't already rated the module. If they havn't then it will submit the
#rating to the database.
@csrf_exempt
@require_http_methods(["POST"])
def HandleRateRequest(request):
    if request.user.is_authenticated:
        x = re.search(r'[12]\d{3}', request.POST.get('year'))
        if(request.POST.get("rating") not in ['1','2','3','4','5']):
            return HttpResponse ("The rating value is invalid. Needs to be between 1 and 5.",status=503, content_type='text/plain')
        elif(request.POST.get("semester") not in ['1','2','3']):
            return HttpResponse ("The semester value is invalid. Needs to be 1,2 or 3.",status=503, content_type='text/plain')
        elif (x is None):
            return HttpResponse ("The year value is invalid.",status=503, content_type='text/plain')
        else:
            if Professor.objects.filter(professor_code=request.POST.get('professor_code')).exists():
                modprof = Professor.objects.get(professor_code=request.POST.get('professor_code'))
                if Module.objects.filter(module_code=request.POST.get('module_code')).exists():
                    mod = Module.objects.get(module_code=request.POST.get('module_code'))
                    if Teaching.objects.filter(module_id=mod.id,semester=request.POST.get('semester'),year=request.POST.get('year')).exists():
                        instance = Teaching.objects.get(module_id=mod.id,semester=request.POST.get('semester'),year=request.POST.get('year'))
                        for num,each in enumerate(instance.professors.all(),start = 1):
                            if (each.professor_code == request.POST.get('professor_code')):
                                if Rating.objects.filter(teaching_id=instance.id).filter(user_id=request.user.id).filter(professor_id=each.id).exists():
                                    return HttpResponse("Unable to add rating as you have already rated it.",status=503, content_type='text/plain')
                                else:
                                    record = Rating(rating=request.POST.get('rating'),teaching_id=instance.id,user_id=request.user.id,professor_id=each.id)
                                    record.save()
                                    return HttpResponse ("Rating Added",status=201, content_type='text/plain')
                            else:
                                print(len(instance.professors.all()))
                                if(num < len(instance.professors.all())):
                                    continue
                                return HttpResponse ("Professor does not teach that module.",status=503, content_type='text/plain')
                    else:
                        return HttpResponse ("Module Information is invalid..",status=503, content_type='text/plain')
                else:
                    return HttpResponse ("Module Code is invalid.",status=503, content_type='text/plain')
            else:
                return HttpResponse ("Professor Code is invalid.",status=503, content_type='text/plain')
    else:
        return HttpResponse ("Not authenticated, you can't rate a professor's module",status=503, content_type='text/plain')
