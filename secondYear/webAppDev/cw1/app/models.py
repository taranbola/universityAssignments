from app import db

class Task(db.Model):
    id = db.Column(db.Integer, primary_key=True)
    title = db.Column(db.String(500), index=True, unique=True)
    description = db.Column(db.String(500), index=True, unique=True)    #the columns of db
    completed = db.Column(db.Boolean)
    date = db.Column(db.DateTime)

    def __repr__(self):                                 #these initialised the columns
            return '' % (self.id, self.title, self.description, self.completed, self.date)
