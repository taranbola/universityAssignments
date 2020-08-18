from django.db import models
from django.contrib.auth.models import User

class Professor (models.Model):
    professor_code = models.CharField (max_length=3, unique=True)
    name = models.CharField (max_length=64)

    def __str__(self):
        return self.name

class Module (models.Model):
    module_code = models.CharField (max_length=3, unique=True)
    mod_name = models.CharField (max_length=64)

    def __str__(self):
        return self.mod_name

class Teaching (models.Model):
    SEMESTERS = ((1, 1),(2, 2),)
    professors = models.ManyToManyField(Professor)
    module = models.ForeignKey (Module, on_delete = models.CASCADE)
    year = models.PositiveSmallIntegerField ()
    semester = models.IntegerField (choices=SEMESTERS)

    class Meta:
        unique_together = ['module','year','semester']

    def __str__(self):
        return self.module.module_code

class Rating (models.Model):
    RATE = ((1, 1),(2, 2),(3, 3),(4, 4),(5, 5),)
    teaching = models.ForeignKey (Teaching , on_delete = models.CASCADE)
    professor = models.ForeignKey (Professor, on_delete = models.CASCADE)
    rating = models.IntegerField (choices=RATE)
    user = models.ForeignKey (User , on_delete = models.CASCADE)

    class Meta:
        unique_together = ['teaching','user','professor']

    def __str__(self):
        return self.user.username
