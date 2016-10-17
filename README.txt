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