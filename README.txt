MongoDB CLI editor

Create a console application that allows to enter schema-less documents into MongoDB, search them by field content, delete by id.

Application should accept these command line arguments:
-create
 create a new document in interactive mode:
 ask for field name then for value; repeat until empty string is entered then save document

-find
 ask for field name and value then perform search in database; display all found documents

-delete
 ask for id of document to delete from database

===========================================================================================================
start mongo with a command similar to:
mongod --dbpath D:\datalex\IdeaProjects\nosql\db

mongo will start with default settings (host, port, etc.)

start an app and choose prefered options and see how it works.
===========================================================================================================
Infrastructure. Unit testing. Module 16

Use your application for task MongoDB and cover functionality by unit tests.
Refactor code if needed.
===========================================================================================================

Unit tests was made for jmp.nosql package.
===========================================================================================================

REST service for Mongo editor

Create REST service using existing codebase from the previous MongoDB CLI editor task.
Refactor your code if needed.
It should provide an interface for the same create, find and delete operations.
===========================================================================================================

To test service functionality you can use postman app.
Here is the link https://www.getpostman.com/apps on which you can download postman for chrome, windows, etc.

Please import .src\main\resources\REST service for Mongo editor.postman_collection.json file into it.
You can find 3 items to
-create
 create a new document in interactive mode:
 ask for field name then for value; repeat until empty string is entered then save document
-find
 ask for field name and value then perform search in database; display all found documents
-delete
 ask for id of document to delete from database
--
GET item has http://localhost:8888/documents;name={NAME};value={VALUE} format and will return response
with all documents that contains such name/value pair
--
POST item using http://localhost:8888/documents/ link and you should specify json objects with param in the body like
{
	"test":"test",
	"test1":"test1",
	"test2":"test2",
	"test3":"test3",
	"test4":"test4"
}
in success case you will get status 201 created and link to the brand new document in the headers:
Location →http://localhost:8888/documents/5817d18796c55511585ca40a
--
DELETE to delete the document you can use just created document link like above or insert some known id:
http://localhost:8888/documents/{KNOWN_ID}




