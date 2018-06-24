WTF_CSRF_ENABLED = True
SECRET_KEY = 'a-very-secret-secret' #Determines if the CSTG should be enabled

import os
basedir = os.path.abspath(os.path.dirname(__file__))

SQLALCHEMY_DATABASE_URI = 'sqlite:///' + os.path.join(basedir, 'app.db')
SQLALCHEMY_MIGRATE_REPO = os.path.join(basedir, 'db_repository')
SQLALCHEMY_TRACK_MODIFICATIONS = True
#establishes the app.db path and folder which migrate changes are saved to.
