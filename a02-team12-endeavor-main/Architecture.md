# Feature
- Account Management

# User Stories
### Iteration 1
- As a cook, I need to be able to create an account. Cost: 3 days
- As a cook, I need to be able to log in using my username and password. Cost: 4 days
- As a cook, I want to be able to set my dietary restrictions. Cost: 4 days

- Total Cost: 2 weeks (11 days)

### Iteration 2
- As a cook, I want to have the flexibility to delete my account. Cost: 6 hours
- As a cook, I want to be able to update my dietary restrictions. Cost: 2 hours
- As a cook, I want to be able to follow along with a recipe while I am cooking. (First part of it, seen issue #16) Cost 1 day

- Total Cost: 2 days

### Iteration 3
- As a cook, I want to delete my recipe. Cost: 2 day
- As a cook, I want to find a recipe by name. Cost: 2 day
- As a cook, I want to add my own recipes. Cost: 4 day

- Total Cost: 8 days

# Dev Tasks
- To add this feature to our project, we first need to create an Object of Cook, that will create one single user. Our design was that, a Cook should have its User ID, User Name and Password. Then we should have some default user accounts to complete a feature.

- User Account Management has been implemented. The user will be able to register for a new account / login for a new account / update the password or user name that is shown inside the account.  The user will ONLY be able to change the user name but will not be able to change the userID since it must be unique and for avoiding any complication issues, it will be non-changeable in the database.

- User Account Management HSQLDB has been implemented. The user can permantely store their registration account information and log in.

- Recipe list has been implemented using RecyclerView , ImageView widgets, and Adapter class to list all recipes in the account home page. The user can view a list of recipes but without having further description. The second part has been pushed to the next iteration.



# Exceptional code
- For our project we are using Linked List Data structure. We found that in-order to handle dynamic data elements, linked list can grow and shrink in size, which can be useful when the size of the data is unknown. Linked list allows to insert and delete elements efficiently and hence it is useful to modify list frequently.

- For testing purpose, we tested some edge cases and added restrictions to the UI to ensure that the system would not allow users to register or log in if they did not enter anything or if the string they entered did not meet the criteria.


**Login credentials : 
Username : tahao
Password:1023**


# Branching Strategy
- Our group is using git flow for the branching strategy. We have one main branch: (main.) All branches made by group menbers are created through the issues assigned to them. As members continue to work, they commit the changes to their respective branches. At the end of the iteration, respective branches are merged into the main branch which is then ready for release.

# Agile planning
#### Iteration 3 completed user stories/features:
*  Adjusting the list of dietary restrictions to include more options in a a menu of checkboxes ( rather than those 3 checkboxes)
* Presistant and permentant database for the account management
* Presistant and permentant database for the recipe management
* User can update their dietary restrictions
* User can delete their account
* Viewing the list of recipes 
* Handling user input and show feedback messages
* Fixing some bugs with the UI
* User story for adding recipe
* User story for searching for recipes

## Testing
### UnitTest
* AccessCookTest
* AccessRecipeTest
* CookTest
* RecipeTest

### IntegrationTest




