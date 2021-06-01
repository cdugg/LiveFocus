from flask import Flask
from flask import request
from flask import jsonify
import pymongo
import json
import uuid
import random
import os
app = Flask(__name__)

#pygmalion.redbrick.dcu.ie:7726

cluster = pymongo.MongoClient("mongodb+srv://seandhammond99:number77@cluster0-c3eli.mongodb.net/test?retryWrites=true&w=majority")
db = cluster["EEGRecord"]
userscollection = db["user_info"]
filescollection = db["file_info"]



@app.route("/adduser/", methods=["POST"])
def addUser():
	if request.method == "POST":
			content = request.json
			userID = content["userid"]
			password = content["password"]
			userAccountAdd = {
			"userID" : userID,
			"password" : password}
			userscollection.insert(userAccountAdd)
			return "Added user."
	return "Invalid request method"

#Add a file
@app.route("/addfile/", methods=["POST"])
def addFile():
	if request.method == "POST":
			print("test")
			content = request.json
			username = content["username"]
			filename = content["filename"]
			groupname = content["groupname"]
			creationdate = content["creationdate"]
			fileContent = content["fileContent"]

			UID = hex(random.randint(0,99999))

			with open("Files/"+str(UID)+".txt", "w") as fileToWrite:
				fileToWrite.write(fileContent)

			filescollection.insert({
				"filename" : filename,
				"username" : username,
				"groupname" : groupname,
				"creationdate" : creationdate,
				"UID" : UID})
			return "Added file."
	return "Invalid request method"


@app.route("/search/", methods=["POST"])
def search():
	if request.method == "POST":
		content = request.json

		searchstring = content["searchstring"]
		foundfilesuser = filescollection.find({"username" : searchstring})
		foundfilesfile = filescollection.find({"filename" : searchstring})
		foundfilesgroup = filescollection.find({"groupname" : searchstring})


		array = list(foundfilesuser) + list(foundfilesfile) + list(foundfilesgroup)
		return str(array)

@app.route("/downloadfile/", methods=["POST"])
def downloadfile():
	if request.method == "POST":
		content = request.json

		uid = content["UID"]
		with open("Files/"+str(uid)+".txt", "r") as fileToRead:
			data = fileToRead.read()
		data = data.replace("\n", "\\n").replace("\r", "\\r")
		return data

@app.route("/delete/", methods=["POST"])
def delete():
	if request.method == "POST":
			content = request.json

			fileid = content["UID"]
			if os.path.exists("Files/" + fileid + ".txt"):
				os.remove("Files/" + fileid + ".txt")
				filescollection.delete_one({"UID" : fileid})
				return "File deleted successfully"
			else:
				return "File not found"


@app.route("/login/", methods=["POST"])
def login():
	if request.method == "POST":
			content = request.json

			username = content["username"]
			password = content["password"]
			user = userscollection.find_one({"userID" : username})
			if not user is None:
				if user["password"] == password:
					return "Authorised"
				else:
					return "Invalid username or password"
			else:
				return "Invalid username or password"


if (__name__ == "__main__"):
	app.run(host="0.0.0.0", port=7726)
