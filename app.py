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
	
class TeachesSchema(ma.Schema):
	class Meta:
		#Fields to expose
		fields = ('crn')
		
teaches_schema = TeachesSchema()
teaches_schema = TeachesSchema(many=true)
		
class ProfessorSchema(ma.Schema):
	class Meta:
		#Fields to expose
		fields = ('name', 'email')
		
professor_schema = ProfessorSchema()
professor_schema = ProfessorSchema(many=true)
		
class DestinationSchema(ma.Schema):
	class Meta:
		#Fields to expose
		fields = ('category', 'dest_ID')
		
destination_schema = DestinationSchema()
destination_schema = DestinationSchema(many=true)		
		
class CourseSchema(ma.Schema):
	class Meta:
		#Fields to expose
		fields = ('dest_ID', 'days', 'times')
		
course_schema = CourseSchema()
course_schema = CourseSchema(many=true)
		
class OfficeSchema(ma.Schema):
	class Meta:
		#Fields to expose
		fields = ('dest_ID', 'email', 'day', 'start', 'end')
		
office_schema = OfficeSchema()
office_schema = OfficeSchema(many=true)

@app.route("/teaches", methods=["GET"])
def get_teaches():
	all_crn = Teaches.query.all()
	result = teaches_schema.dump(all_crn)
	return jsonify(result.data)
	
@app.route("/professor", methods=["GET"])
def get_professor():
	all_professor = Professor.query.all()
	result = professor_schema.dump(all_professor)
	return jsonify(result.data)
	
@app.route("/destination", methods=["GET"])
def get_destination():
	all_destination = Destination.query.all()
	result = destination_schema.dump(all_destination)
	return jsonify(result.data)
	
@app.route("/course", methods=["GET"])
def get_course():
	all_course = Course.query.all()
	result = course_schema.dump(all_course)
	return jsonify(result.data)
	
@app.route("/office", methods=["GET"])
def get_office():
	all_office = Office.query.all()
	result = office_schema.dump(all_office)
	return jsonify(result.data)