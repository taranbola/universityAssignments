# Generated by Django 2.1.5 on 2020-03-03 16:33

from django.conf import settings
from django.db import migrations, models
import django.db.models.deletion


class Migration(migrations.Migration):

    initial = True

    dependencies = [
        migrations.swappable_dependency(settings.AUTH_USER_MODEL),
    ]

    operations = [
        migrations.CreateModel(
            name='Module',
            fields=[
                ('id', models.AutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('module_code', models.CharField(max_length=3, unique=True)),
                ('mod_name', models.CharField(max_length=64)),
            ],
        ),
        migrations.CreateModel(
            name='Professor',
            fields=[
                ('id', models.AutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('professor_code', models.CharField(max_length=3, unique=True)),
                ('name', models.CharField(max_length=64)),
            ],
        ),
        migrations.CreateModel(
            name='Rating',
            fields=[
                ('id', models.AutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('rating', models.IntegerField(choices=[(1, 1), (2, 2), (3, 3), (4, 4), (5, 5)])),
                ('professor', models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, to='app.Professor')),
            ],
        ),
        migrations.CreateModel(
            name='Teaching',
            fields=[
                ('id', models.AutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('year', models.PositiveSmallIntegerField()),
                ('semester', models.IntegerField(choices=[(1, 1), (2, 2)])),
                ('module', models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, to='app.Module')),
                ('professors', models.ManyToManyField(to='app.Professor')),
            ],
        ),
        migrations.AddField(
            model_name='rating',
            name='teaching',
            field=models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, to='app.Teaching'),
        ),
        migrations.AddField(
            model_name='rating',
            name='user',
            field=models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, to=settings.AUTH_USER_MODEL),
        ),
        migrations.AlterUniqueTogether(
            name='teaching',
            unique_together={('module', 'year', 'semester')},
        ),
        migrations.AlterUniqueTogether(
            name='rating',
            unique_together={('teaching', 'user', 'professor')},
        ),
    ]
