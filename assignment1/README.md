Assignment 1

Working Record: This represents a working copy of the assignment. Most work from assignments in the future will be done in here with regards to this project. You can track these changes in the commit history.

Assignment 1:

Create an inventory/part list view coupled to a detail view that will represent the individual inventory item's editable properties. Show all properties of the item within each entry on the Inventory List view.

First project representing work on MVC.

Assignment 2: 

All changes from Assignment 2 to this directory take place after the tag "assignment2". 

Given a series of Change Requests and storing the project in git (this project was always in git), each team member will complete a separate change request on their own branch. Team members will switch off ownership of the master repo to merge completed branches into the master branch.

Assignment 3:

In terms of database connectivity, a basic gateway class was created to handle and maintan connections. After that, Data Access Objects (Dao's) were created for each class, though some are a bit redundant and could be refactored. The AbstractDao class gave common functionality to extending classes, mainly the "get id or insert and then get id" functionality for certain type tables or the "get id for value out of type table" functionality.

Once complete, the PartDao and the ItemDao handled the insertion, deletion and editing of existing Part objects or Item objects. The normalized database is Kyle Haley's fault and probably overkill for this assignment.

Model objects make calls to the database via the Dao objects in the 'dao' package. The Dao objects generate the model objects from data in the database.

Assignment 4:

PERFORMANCE NOTE: PartDao is slow to generate a list of Part's from the database, 
which accounts for the slowness of adding ProductTemplatePart and for editing an existing
 ProductTemplatePart.

 Design Considerations: 
 Two database tables were created, one to represent a Product Template and an assocation 
 table which draws the assocation between Parts and a ProductTemplate and the quantity of the 
 Part's in the Product Template.

 There are two list views, one for all Product Template's and then a list view of all the Part's 
 for each indiviual Product Template. There are two detail views, one for editing an 
 individual Product Template, one for editing a Product Template Part.

 For each table, a Dao (Data Access Object) was created for CRUD operations. Two models were 
 created to represent the rows in each corresponding table. For each of those models, an 
 additional model was created to manage the business rules and act as the intermediary between 
 those models and the Dao's associated with them.

 Assignment 5:

 PERFORMANCE NOTE: Refactored PartDao and ItemDao, but still seeing a large performance hit. Did a check and it appears 62 independent connections are being made. I looked into pooling the connections, but decided that I'd rather save that for assignment six. - Kyle Haley

 LOGIN: A login view was created with a corresponding controller to manage permissions. The selected user is passed to the Authenticator constructor and a Session object is returned. Based on the role, the permissions in the Session are configured and then passed to other views, so that the corresponding permissions filter down to the controllers. For login, a while loop is used to control whether or not the other views are loaded properly.
