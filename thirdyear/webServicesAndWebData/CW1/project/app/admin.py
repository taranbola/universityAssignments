from django.contrib import admin

# Register your models here.

from .models import Author, Story

admin.site.register(Author)
admin.site.register(Story)
