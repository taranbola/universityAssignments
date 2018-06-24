from app import db

rel = db.Table('rel',
    db.Column('userID', db.Integer, db.ForeignKey('user.userID')),        #the relationship table
    db.Column('taskID', db.Integer, db.ForeignKey('task.taskID'))
    )

class Task(db.Model):
    taskID = db.Column(db.Integer, primary_key=True)
    description = db.Column(db.String(500), index=True, unique=True)    #the columns of db
    kanban = db.Column(db.Integer)
    date = db.Column(db.DateTime)

    def __repr__(self):                                 #these initialised the columns
            return '' % (self.taskID,self.description, self.kanban, self.date)

class User(db.Model):
    userID = db.Column(db.Integer, primary_key=True)
    username = db.Column(db.String(500), index=True, unique=True)
    password = db.Column(db.String(500), index=True, unique=True)
    rel = db.relationship('Task', secondary=rel, backref=db.backref('rel', lazy='dynamic'))

    def __repr__(self):                                 #these initialised the columns
            return '' % (self.userID,self.username)
