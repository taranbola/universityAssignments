from django.contrib import admin

# Register your models here.

from .models import Professor, Module, Teaching, Rating

admin.site.register(Professor)
admin.site.register(Module)
admin.site.register(Teaching)
admin.site.register(Rating)
