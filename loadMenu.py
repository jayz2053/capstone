import requests

def menu():
	ans = input("1 add class\n2 add teaches\n3 add professor\n4 add office hours\n5 add destination\n6 quit\n")
	return int(ans)

def choice(ans):
	if ans == 1:
		add_class()
	elif ans == 2:
		add_teacher()
	elif ans == 3:
		add_professor()
	elif ans == 4:
		add_office()
	elif ans == 5:
		add_destination()
	elif ans >=6:
		print("Goodbye")

def add_course():
	name = input("Enter class name: ")
	course_id = input("Enter course id: ")
	desc = input("Enter a description: ")

	payload = {"name" : name, "course_id": course_id, "description":desc}

	r = requests.post('http://188.166.97.39:8080/course', payload)
	
	print(r.status_code)
	
def add_teaches():
	crn = input("Enter crn: ")
	days = input("Enter days: ")
	time = input("Enter a time: ")
	email = input("Enter a email: ")
	dest_ID = input("Enter a destination_ID: ")
	
	payload = {"crn" : crn, "days": days, "time":time, "email":email, "dest_ID": dest_ID}
	
	r = requests.post('http://188.166.97.39:8080/teaches', payload)

########################################################################

def add_professor():
	name = input("Enter professor name: ")
	email = input("Enter email: ")
	
	payload = {"name" : name, "email": email}
	
	r = requests.post('http://188.166.97.39:8080/professor',json=payload)

def add_office():
	email = input("Enter an email: ")
	dest_id = input("Enter a dest id: ")
	day = input("Enter days available: ")
	start = input("Enter start time: ")
	end = input("Enter an end time: ")
	
	payload = {"email" : email, "dest_id" : dest_id, "day" : day, "start" : start, "end" : end}
	
	r = requests.post('http://188.166.97.39:8080/office', payload)

def add_destination():
	category = input("Enter room category: ")
	dest_id = input("Enter room number: ")
	building = input("Enter the building number: ")
	
	payload = {"category" : category, "dest_id": dest_id, "building":building}
	
	r = requests.post('http://188.166.97.39:8080/destination', payload)
	
	
def main():
	ans = 1
	while(ans < 6):
		ans = menu()
		choice(ans)


main()
