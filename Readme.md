
# AddressBook Api

This api facilitates brach managers to keep track of their customer contacts. There will be multiple branch managers using the system. So that, they will maintain multiple addressbooks. A given customer will have multiple telephone numbers (ex: Work, Mobile,Residence etc).

Below are the list of functionalities provided with this software.

1) **Create Address Book** : Branch managers can create multiple address books.However address book name must be unique.
2) **Add contacts to an address book** :  Branch managers can create multiple contacts in an address book.
3) **Get all the contacts in an address book**: Branch managers can get a list of all the contacts on a specific address book.
4) **Delete a specific Contact**: Branch managers can remove a selected contact from the addressbook.
5) **Delete an existing address book**: Branch manager can delete an address book. It will delete the address book with all it's contacts.
6) **Get All the contacts across multiple address books** : Branch managers can get a list of contacts across multiple address books. The contacts will be sorted in ascending order by their names.



## Build and Run the project

### Prerequsites
Docker and maven should be installed.

### Build instructions

1) Checkout source code
2) Execute ```mvn clean package```
 * This command will build the project and produce an docker image artifact.
 * During the build phase it will run all the unit tests and a build will fail,if an unit test fail.
 * Once build is success, we can ensure the existance of the docker image by executing ```docker image ls```
 ![ScreenShot](/screenshots/check-for-docker-image.png)


### Run instructions

Execute ```docker run -d -p 9090:8080 address_book_api:0.0.1```
## Api Documentation

Once the project is running we can access the api documentaion here.
http://localhost:9090/address_book_api/swagger-ui/index.html

 ![ScreenShot](/screenshots/api-documents.png)


## Inspect Database
We can login to the inmemory database and inspect the data.
Use below details in the login page.

 * **URL** : http://localhost:9090/address_book_api/h2-console
 * **jdbc url**: jdbc:h2:mem:address_book_db
 * **user name**: address_book_user
 * **password**: 1qaz2wsx@

 ![ScreenShot](/screenshots/h2_login_console.png)