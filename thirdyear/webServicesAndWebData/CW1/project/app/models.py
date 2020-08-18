from django.db import models

# Create your models here.

from django.contrib.auth.models import User

class Author (models.Model):
    user = models.ForeignKey (User , on_delete = models.CASCADE)
    username = models.CharField (max_length = 64, unique=True)

    def __str__(self):
        return self.user.username

class Story (models.Model):
    CATEGORIES = (
        ('pol', 'Politics'),
        ('art', 'Art'),
        ('tech', 'Technology'),
        ('trivia', 'Trivial'),
    )
    REGIONS = (
        ('uk', 'UK News'),
        ('eu', 'European News'),
        ('w', 'World News'),
    )
    headline = models.CharField (max_length = 64)
    auth = models.ForeignKey (Author, on_delete = models.CASCADE)
    category = models.CharField(max_length=6, choices=CATEGORIES)
    region = models.CharField(max_length=2, choices=REGIONS)
    date = models.DateField(auto_now_add=True)
    details = models.CharField (max_length=512)
