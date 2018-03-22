from flask import Flask, request, jsonify
from flask_sqlalchemy import SQLAlchemy
from flask_marshmallow import Marshmallow
import os

app = Flask(__name__)
basedir = os.path.abspath(os.path.dirname(__file__))
app.config['SQLALCHEMY_DATABASE_URI'] = 'sql:///' + os.path.join(basedir, 'crud.sqlite')
db = SQLAlchemy(app)
ma= Marshmallow(app)

#---------------------     		TABLES     		-----------------------#

#TEACHES TABLE
class Teaches(db.Model):
	crn = db.Column(db.String(12), primary_key=True)
	days = db.Column(db.String(10))
	time = db.Column(db.String(20))
	email = db.Column(db.String(25))
	dest_ID = db.Column(db.String(10))
	
	def __init__(self, crn, days, time, email, dest_ID):
		self.crn = crn
		self.days = days
		self.email = email
		self.dest_ID = dest_ID
	
#PROFESSOR TABLE
class Professor(db.Model):
	email = db.Column(db.String(25), primary_key=True)
	name = db.Column(db.String(25))
	
	def __init__(self, email, name):
		self.email = email
		self.name = name
	
	
#DESTINATION TABLE
class Destination(db.Model):
	category = db.Column(db.String(20))
	dest_ID = db.Column(db.Integer, primary_key=True)
	building = db.Column(db.String(5))
	
	def __init__(self, category, dest_ID, building):
		self.category = category
		self.dest_ID = dest_ID
		self.building = building
	

#COURSE TABLE
class Course(db.Model):
	name = db.Column(db.String(20), unique=True)
	course_id = db.Column(db.String(15), primary_key=True)
	description = db.Column(db.String(100))
	
	def __init__(self, name, course_id, desc):
		self.name=name
		self.course_id = course_id
		self.desc = desc
		
		
#OFFICE HOURS TABLE
class Office(db.Model):
	email = db.Column(db.String(20))
	dest_ID = db.Column(db.String(20))
	day = db.Column(db.String(20))
	start = db.Column(db.String(20))
	end = db.Column(db.String(20))
	
	def __init__(self, email, dest_ID, day, start, end):
		self.email = email
		self.dest_ID = dest_ID
		self.day = day
		self.start = start
		self.end = end



#---------------------     		SCHEMA     		-----------------------#	
	
#TEACHER SCHEMA
class TeachesSchema(ma.Schema):
	class Meta:
		#Fields to expose
		fields = ('crn', 'days', 'time','email','destination')
				
#PROFESSOR SCHEMA
class ProfessorSchema(ma.Schema):
	class Meta:
		#Fields to expose
		fields = ('name', 'email')
		
#DESTINATION SCHEMA
class DestinationSchema(ma.Schema):
	class Meta:
		#Fields to expose
		fields = ('category', 'dest_ID', 'building')

#COURSE SCHEMA		
class CourseSchema(ma.Schema):
	class Meta:
		#Fields to expose
		fields = ('name', 'course_id', 'description')

#OFFICE HOURS SCHEMA
class OfficeSchema(ma.Schema):
	class Meta:
		#Fields to expose
		fields = ('dest_ID', 'email', 'day', 'start', 'end')

#-----------------     		INIT SCHEMA     		-------------------#	

professor_schema = ProfessorSchema()
professor_schema = ProfessorSchema(many=true)
		
teaches_schema = TeachesSchema()
teaches_schema = TeachesSchema(many=true)

office_schema = OfficeSchema()
office_schema = OfficeSchema(many=true)

course_schema = CourseSchema()
course_schema = CourseSchema(many=true)

destination_schema = DestinationSchema()
destination_schema = DestinationSchema(many=true)

#---------------------     	GET	ROUTES     		-----------------------#	

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

#------------------     	POST ROUTES    		-----------------------#	

@app.route("/teaches", methods=["POST"])
def add_teaches():
	crn = request.json['crn']	
	days = request.json['days']
	time = request.json['time']
	email = request.json['email']
	dest_ID = request.json['dest_ID']
	
	new_teaches = Teaches(crn, days, time, email, dest_ID)
	
	db.session.add(new_teaches)
	db.session.commit()
	
	return jsonify(new_teaches)
	
@app.route("/professor", methods=["POST"])
def add_professor():
	email = request.json['email']	
	name = request.json['name']
	
	new_professor = Professor(email, name)
	
	db.session.add(new_professor)
	db.session.commit()
	
	return jsonify(new_professor) 
	
@app.route("/destination", methods=["POST"])
def add_destination():
	category = request.json['category']	
	dest_ID = request.json['dest_ID']
	building = request.json['building']
	
	new_dest = Destination(category, dest_ID, building)
	
	db.session.add(new_dest)
	db.commit()
	
	return jsonify(new_dest)
	
@app.route("/course", methods=["POST"])
def add_course():
	name = request.json['name']	
	course_id = request.json['course_id']
	description = request.json['description']
	
	new_course = Course(name, course_id, description)
	
	db.session.add(new_course)
	db.commit()
	
	return jsonify(new_course)
	
@app.route("/office", methods=["POST"])
def add_office():
	email = request.json['email']
	dest_ID = request.json['dest_ID']
	day = request.json['day']
	start = request.json['start']
	end = request.json['end']
	
	db.session.add(new_course)
	db.commit()
	
	return jsonify(new_course)
	
