from flask import Flask, request, jsonify
from flask_sqlalchemy import SQLAlchemy
from flask_marshmallow import Marshmallow
import os

app = Flask(__name__)
basedir = os.path.abspath(os.path.dirname(__file__))
app.config['SQLALCHEMY_DATABASE_URI'] = 'sql:///' + os.path.join(basedir, 'crud.sqlite')
db = SQLAlchemy(app)
ma= Marshmallow(app)

class Teaches(db.Model):
	crn = db.Column
	destination = db.Column
	email = db.Column
	courseName = db.Column
	dest_ID = db.Column
	
	def __init__(self, crn, destination, email, courseName, dest_ID):
		self.crn = crn
		self.destination = destination
		self.email = email
		self.courseName = courseName
		self.dest_ID = dest_ID
	

class Professor(db.Model):
	email = db.Column
	name = db.Column
	
	def __init__(self, email, name):
		self.email = email
		self.name = name
	
	
	
class Destination(db.Model):
	category = db.Column
	dest_ID = db.Column
	
	def __init__(self, category, dest_ID)
		self.category = category
		self.dest_ID = dest_ID
	


class Course(db.Model):
	days = db.Column
	times = db.Column
	email = db.Column
	dest_ID = db.Column
	
	def __init__(self, days, times, email dest_ID):
		self.days = days
		self.times = times
		self.email = email
		self.dest_ID = dest_ID 

class Office(db.Model):
	email = db.Column
	dest_ID = db.Column
	day = db.Column
	start = db.Column
	end = db.Column
	
	


