# CRUD

![screenshot](screenshot.jpg)

## Description

Design your very own CRUD app using Spring. It must support user authentication (login and logout) and creating/reading/updating/deleting entries. You can store any kind of information you want (within the bounds of appropriateness).

## Requirements

* Choose something you'd like to "track" in a web app.
* User authentication
  * If not logged in, show a login form at the top (it can double as your create account form).
  * If logged in, display the username and a logout button at the top.
  * Passwords MUST be stored securely.
  * Don't allow unauthenticated users to hit routes they shouldn't hit.
* All data must be stored and manipulated using Hibernate.
* Create: If logged in, display a form to create a new entry.
* Read: Whether logged in or not, list whatever entries were created by the users.
	* If logged in, list the items from the user in one list at the top
	* List all items from all users in a second list
* Update: If logged in, show an edit link next to the entries created by that user.
* Delete: If logged in, show a delete button next to the entries created by that user.
* Write tests for your POST routes.


## Hard Mode
* Add tests for all GET routes
* Add JavaScript and CSS (served statically via `resources/static`).
* Change the formatting so the 2 lists are served side by side..
* Allow the user to sort the list by at least 2 different fields 
* Paginate both lists of items.  There should be at least 3 pages of items.  Please make sure not to explode in the event the user is at the first or last page.
* Add filters for relevant fields

## Resources
* [Github Repo](https://github.com/tiy-lv-java-2016-11/spring-crud)
* [Java Time Tutorial](http://www.tutorialspoint.com/java8/java8_datetime_api.htm)
* [MockMVC](http://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/test/web/servlet/MockMvc.html)
* [Password Hasher](https://raw.githubusercontent.com/defuse/password-hashing/master/PasswordStorage.java)
