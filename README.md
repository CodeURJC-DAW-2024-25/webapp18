🏚🏝 dreamLife 
## PHASE 2: Integration of a REST API, Docker deployment, and remote hosting

## 1. API REST documentation
###  1.1 OpenAPI
###  1.2 HTML

## 2. Class and templates diagram
![CLASSD](https://github.com/user-attachments/assets/e8c5573c-1efa-43b8-a722-7a8d2c2843d5)

## 3. Execution instructions

 1. Clone the repository with
  ```
  git clone https://github.com/CodeURJC-DAW-2024-25/webapp18.git
  ```
  2. Switch to "docker" directory 
  ```
  cd webapp18/docker
  ```
  3. Install docker on your system [here](https://docs.docker.com/engine/install/)
  4. Find the installed docker, and run it
  5. While being on the directory webapp12/docker, excute on terminal:
  ```
  docker-compose up
  ```
  6. Once the previous step is finished, open an internet browser, and search for [https://localhost:8443](https://localhost:8443)
  
  ## 4. Docker image documentation
  
  1. Use a browser to create an account on [Dockerhub](https://hub.docker.com/)
  2. Clone the repository with
  ```
  git clone https://github.com/CodeURJC-DAW-2024-25/webapp18.git
  ```
  3. Switch to "docker" directory 
  ```
  cd webapp18/docker
  ```
  4. Install docker on your system [here](https://docs.docker.com/engine/install/)
  5. Find the installed docker, and run it
  6. Connect your account to Docker. You can use:
  ```
  docker login
  ```
  And then type your credentials   
  
  7. While being on the directory webapp12/docker, excute on terminal:
  ```
  ./create_image.sh
  ```
  
  ## 5. Virtual Machine deployment
  
  ### 5.1. Connect to virtual machine
  Make sure to have the SSH key on the directory you execute these commands from:
  ```
  cp prAppWeb18.key ~/ 
  chmod 600 ~/prAppWeb18.key
  ssh -i ~/prAppWeb18.key vmuser@10.100.139.161
  ```

  ### 5.2 Install Docker
  ```
  sudo apt-get update
  sudo apt-get install ca-certificates curl
  sudo install -m 0755 -d /etc/apt/keyrings
  sudo curl -fsSL https://download.docker.com/linux/ubuntu/gpg -o /etc/apt/keyrings/docker.asc
  sudo chmod a+r /etc/apt/keyrings/docker.asc
  
  echo \
  "deb [arch=$(dpkg --print-architecture) signed-by=/etc/apt/keyrings/docker.asc] https://download.docker.com/linux/ubuntu \
  $(. /etc/os-release && echo "$VERSION_CODENAME") stable" | \
  sudo tee /etc/apt/sources.list.d/docker.list > /dev/null
  sudo apt-get update
  ```
  
  ### 5.3 Install Docker-Compose plugin
  ```
  sudo apt-get install docker-ce docker-ce-cli containerd.io docker-buildx-plugin docker-compose-plugin
  ```
  
  ### 5.4 Test Docker is correctly instaled running
  ```
  sudo docker run hello-world
  ```

 ### 5.5 Prepare Docker Compose
  ```
  sudo curl -L "https://github.com/docker/compose/releases/download/1.29.2/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose
  sudo chmod +x /usr/local/bin/docker-compose
  ```

  ### 5.6 Clone the repository
  Execute:
  ```
  ls
  ```
  If you dont see a webapp18 folder, then execute:
  ```
  git clone https://github.com/CodeURJC-DAW-2024-25/webapp18.git
  ```

 ### 5.7 Switch to directory and grant premissions
  ```
  cd webapp18/docker
  chmod +x create_image.sh
  ```

 ### 5.8 Execute the script
  ```
  ./create_image.sh
  ```

 ### 5.9 Open a browser and access
  ```
  https://10.100.139.161:8443/index
  ```


## GROUP MEMBERS PARTICIPATION

| Name                         | Tasks | Most important commits | Most worked on files | 
|------------------------------|--------|-------------------------|----------------------|
| Laila El Khattabi El Hassnaoui | I have worked with reservation controller as well as user controller. I have implemented the security part of this phase and tried the endpoints on postman      |  -[Security implementation](https://github.com/CodeURJC-DAW-2024-25/webapp18/commit/b1fcb053aa615c236a31a157be095f2b04cdfb1a) <br> -[Added DTOS, controllers and mappers to reservation](https://github.com/CodeURJC-DAW-2024-25/webapp18/commit/e1de9d9db211b6316e34098ddf9cd07af324540c) <br> - [User controller rest files](https://github.com/CodeURJC-DAW-2024-25/webapp18/commit/93038df265fc3fec2d06dc8ffa9e57c556fee329) <br> -[docker](https://github.com/CodeURJC-DAW-2024-25/webapp18/commit/f66e6c5b0110d3b4b323f0faac2fa1e10811e221#diff-e82d83fb2061d91bd2a75743bb98569311d56d8defbc94a7cf1659c007837ce1) <br> -[dto logic](https://github.com/CodeURJC-DAW-2024-25/webapp18/commit/7e2e2cc59c201b899628c66fca9893bbd7ee71d0#diff-307d12707b225f9c9f0f92a132f7ee3683e41ec598e78b18c0c21a3d332bf0af)                 | ReservationRestController, UserRestController, mappers and DTOs. Security implemntation and endpoints with postman                     |
| Carlos Hermán Andrés Andrés    |        |                         |                      |
| Elena Ceinos Abeijón           | I have worked on the RoomRestController, also checked ReviewRestControllers and some of the UserRestControllers. I have tried endpoints on Postman and also helped with the security config. | - [Added DTOs and MapStruct dependencies, disabled CSRF](https://github.com/CodeURJC-DAW-2024-25/webapp18/commit/0b158b87a8e5d13fbf2b0494a7930d3d2dd8d226) <br> - [Modified UserRestController, login and ID. Added @Component in the mappers](https://github.com/CodeURJC-DAW-2024-25/webapp18/commit/efe6e12d4ccbec30f3b4cdc07c82be0a89fb36b3) <br> - [Corrected JWT security target errors and fixed some mappers](https://github.com/CodeURJC-DAW-2024-25/webapp18/commit/306d403dc619cc0f8d21715667570bb6e3bc9532) <br> - [Added mapping and target to mappers to remove warnings](https://github.com/CodeURJC-DAW-2024-25/webapp18/commit/6ddcb5f3bfc1e2ceadd94e8f8a76c711542a9027) | RoomRestController, UserRestController, ReviewRestController, mappers, and DTOs. Also, a lot of endpoint trials with postman |




## PREPARATION 1


## Group members
| Names |Email |Github user| 
|--------------|--------------|--------------|
Laila El Khattabi El Hassnaoui | l.elkhattabi.2020@alumnos.urjc.es |lailaelkhattabielhas
Carlos Hermán Andrés Andrés | ch.andres.2020@alumnos.urjc.es|Carlos-Herman
Elena Ceinos Abeijón | e.ceinos@alumnos.urjc.es| elenacabe
Daniel Gómez López | d.gomezl.2021@alumnos.urjc.es | daniel-gomez487

## Functionalities

### Entities

  - Apartments
  - Users
  - Review
  - Reservation


![DiagramaER](https://github.com/user-attachments/assets/9bd76667-efb8-4383-ab47-d68b4f5a9af7)

### User types

  - Registered client
  - Unregistered client
  - Administrator
  - Apartment Manager

### Permissions

| Activities | Registered client | Unregistered client | Administrator | Apartment manager |
|--|--|--| -- |-- |
| Search / filter apartments | ✔      | ✔       | ✔       | ✔ |
| Write reviews       | ✔  | ❌   | ❌  | ✔ |
| Modify the apartments DB       | ❌  | ❌   | ✔  | ❌  |
| Personal profile       | ✔  | ❌   | ✔  | ✔ |
| Make reservations      |  ✔ | ❌   | ✔  | ✔ |
| Modify reservations       | ✔  | ❌  | ✔  | ✔ |
| View reservations history       | ✔  | ❌  | ✔ | ✔ |
| Create / edit / delete apartment | ❌ | ❌ | ✔ | ✔ |

### Images

- User profile
- Apartments interior / scenery

### Graphics

- Reservations
-  Revenue by month
- Date

### Complementary technologies
- Mail confirmation with tickets
- PDF generation
- Integration with Google Maps to show apartments in the map

### Advanced query
- Recommendation algorithm


## PREPARATION 2

### PAGES

- **apartmentInformation** -> This page displays the description of the apartment, as well as pictures and the categories it belongs to. It has some reviews as well.![image](https://github.com/user-attachments/assets/68061cea-1e36-48bb-97bd-e3b7d4f798b9)


- **apartmentValidation** -> In this page, the admin can accept or deny the inclusion of new apartments to the app. It also shows some info about the apartments.
 ![image](https://github.com/user-attachments/assets/f01f115f-4e21-4fdb-a17b-ae86cfeec882)


- **clientReservation** -> Shows all reservations made by a user. Accessible by admin and users alike.
![image](https://github.com/user-attachments/assets/686d184d-dd72-4e18-9fec-65d82f80087d)


- **editApartment** -> Allows a manager to edit the attributes of an apartment.
![image](https://github.com/user-attachments/assets/5f86eddd-8454-4a77-96e7-bc94345c4975)


- **index.html** -> Main page of the web application. Here you can select your preferred apartment, search for others, access your profile, log in, contact us...
![image](https://github.com/user-attachments/assets/26dc8dfb-dc78-4cbb-92d7-9e83bc8800a5)

- **managerList** -> Shows all managers included in the database. Can make certain actions with them, and show their current apartments.
![image](https://github.com/user-attachments/assets/350634bd-af7c-482c-9d83-8eda1a9e498c)


- **profile** -> Shows personal data about the user and a picture.
![image](https://github.com/user-attachments/assets/f28ad1a4-70c8-44a5-adff-5a50f1fa3f1a)


- **viewApartmentsManager** -> Shows all the apartments belonging to a manager, and some info about them.
![image](https://github.com/user-attachments/assets/58f8ab12-738e-4372-a9d1-2e8febcbe4a0)



- **Apartment Review** -> In the apartment review page, you can view some booker's opinions
  ![image](https://github.com/user-attachments/assets/25776fb0-18cc-4f3a-9924-8df43422f7b3)


- **Chart pages** -> In the admin, manager and user charts, you can view detailed information about the bookings and, it also shows the notifications
![image](https://github.com/user-attachments/assets/a16062f4-50db-4aa8-9740-463c029d23b4)
![image](https://github.com/user-attachments/assets/e1bb7558-5c2e-4143-93d3-f21ae66efe38)
![image](https://github.com/user-attachments/assets/43b43412-0171-4352-ac34-e132e23652bc)



- **Contact page** -> In the contact page, you have a form to send a direct message. Also you can enter the footer information

*not a user* ![image](https://github.com/user-attachments/assets/353fcbff-244a-4b30-84f3-84286ee16001)
*logged user* ![image](https://github.com/user-attachments/assets/b3a20263-b5c9-4b22-8578-6bca4da3004d)


- **Edit profile** -> In this page, you can edit your profile's info and view some details.
![image](https://github.com/user-attachments/assets/7a912d3d-0a76-48b1-a258-ad90918bd2a1)

- **Login page** -> In this page, you can login if you have a user or, otherwise, navigate to the register page.
![image](https://github.com/user-attachments/assets/57f1fee3-2258-4c29-aaf8-3e723724ec11)


- **Register page** -> In this page, you enter some info about yourself to create an account
  ![image](https://github.com/user-attachments/assets/732e9e69-47c9-4ad4-8dc5-5f1338638db1)

### DIAGRAM NAVIGATION



![DIAGRAMA_NAVEFGACION](https://github.com/user-attachments/assets/2c870d4b-44a0-46fc-a65a-9c72b871a31f)

## PHASE 1: WEB WITH HTML GENERATED IN SERVER AND AJAX



## PAGES

- **index** -> This is the welcome page. Also, you can view the validated-manager apartments and click to view each ones info (if you're a user, you can book) and reviews. Also, in the nav bar you can click on "login". If you're already logged, you can click on "profile" to see your details. You can also load more apartments
![image](https://github.com/user-attachments/assets/ec4f0873-8a6e-432c-b1f3-c16c53c34955)

![image](https://github.com/user-attachments/assets/d163e953-b385-49db-bf73-aa069890ea37)
- **login** -> In this page, if you already have a user, you can log in. If you don't, you can click on "create an account", it will redirect you to the register page. If your  credentials are invalid, you'll be redirected to loginError
  ![image](https://github.com/user-attachments/assets/aa97a809-2fe6-44bb-b0fd-e564154735f3)

- **loginError** -> This is the login Error page
 ![image](https://github.com/user-attachments/assets/936beb4c-74d3-454a-97ad-3aa24049a32c)

- **nick taken** -> If you try to register with a username that already exists, you'll be redirected to this page

- **register** -> In this page you can register. Make sure you don't repeat mail nor username. You can choose your role but managers must be validated.
  ![image](https://github.com/user-attachments/assets/031d2582-3aee-4c72-8bda-f2b64601959d)

- **profile** -> In this page, you can check your info. Also, you can see a sidebar with your role and some options depending on it. Also, you can click on "edit profile" or "sign out". If you're a manager, you can also see wether you're validated or not. *ONLY USERS*
  ![image](https://github.com/user-attachments/assets/c5648f58-3473-4eac-8536-1cc0b2a94738)
  ![image](https://github.com/user-attachments/assets/01db8acd-0d03-415e-be8d-4163f3c81f8d)

- **edit profile** -> In this page, you can change your details and your photo. *ONLY USERS* 
  ![image](https://github.com/user-attachments/assets/e4d3a57b-ea63-4a45-a44a-c50f3de93491)
- **my Reservations** -> In this page, you can view a list of your reservations. Also, you can proceed to view the details or cancel them
  ![image](https://github.com/user-attachments/assets/72ebc696-61e4-44a9-9b19-5d70a0fd02f0)
- **Reservation details** -> In this page, you can check your reservation details or cancel it
  ![image](https://github.com/user-attachments/assets/f0df55ca-39d1-41c2-b64d-42f22a47f0a8)

- **apartment information** -> if you're not logged, you can just check the info. If you're a client, you can check the dates to make a booking. If you're any of the roles, you can also click on "reviews"
  ![image](https://github.com/user-attachments/assets/746b89a7-87e2-4142-a71f-62bf34430bd2)
![image](https://github.com/user-attachments/assets/9a831948-4dbd-4b65-b98a-6fb72db6aed0)

- **apartment reviews** -> You can check the reviews and if you're a user, you can leave yours. Also you can check the chart to see how many reviews the apartment has. If there are more than 6 comments, you can load more.
  ![image](https://github.com/user-attachments/assets/767c2905-c841-45e2-8888-699597ea4f59)
  ![image](https://github.com/user-attachments/assets/53c3036b-62fd-4d2f-8b08-a9eb51c7a9c5)
  *ONLY USERS-last*
- **add apartment** ->*ONLY MANAGER* As a manager, in this page you can upload a new apartment with all the details.
  ![image](https://github.com/user-attachments/assets/76595ba2-03cb-4aae-a1cc-9fb066d0964a)
- **my apartments** -> *ONLY MANAGER* As a manager, in this page you can view a list of your apartments. You can also proceed to delete or edit each one.
  ![image](https://github.com/user-attachments/assets/0c9f8d35-07a2-45a2-ae1a-600d900e6020)
- **edit apartment** -> *ONLY MANAGER* As a manager, in this page you can edit the details of your selected apartment and check the preview of the neww image.
  ![image](https://github.com/user-attachments/assets/e169aa4e-abcb-4dc5-adb5-c33f9b55683a)
- **Manager validation** -> *ONLY ADMIN*  As an admin, in this page you can view a list of the new managers. You can validate them so that they will be able to access all the manager's features, or reject them.
  ![image](https://github.com/user-attachments/assets/c0033c2b-ed44-4e44-b42a-13a7664e8a9c)

# DIAGRAM NAVIGATION
![NAVIGATION DIAGRAM](https://github.com/user-attachments/assets/d98ca735-1882-4b36-9abc-27a8b9b9b70e)

# EXECUTION INSTRUCTIONS

## VERSION 

The tecnologies that we use in this phase are:

    -MySQL: 8.0.33
  
    -Maven : 4.0.0
  
    -Spring Boot: 3.2.3

    -Java: 21

    -Google ReCAPTCHA: Version 2

## CONFIGURATION 

In order to execute the app, you will need to follow the next steps:

  - 1.Download the zip that appears in this link, https://github.com/CodeURJC-DAW-2024-25/webapp18

  - 2.Navigate to the MySQL website at https://dev.mysql.com/downloads/workbench/ and select the 'Download' option.

  - 3.Choose your operating system and click 'Download' to initiate the download procedure.

  - 4.Open MySQL Workbench using your applications menu or start menu.

  - 5.Select 'Local instance 3306' from the 'MySQL Connections' section to establish a connection with the default local   MySQL server

  - 6.Provide your MySQL server credentials if requested.

  - 7.Upon establishing the connection, you'll have the capability to create or manage databases and tables, as well as execute SQL queries using the MySQL Workbench interface.

  - 8.Before utilizing this database, it's necessary to input new data by populating the DataSampleService with information regarding the elements in the tables

  - 9.Additionally, it's advisable to update the 'application.properties' file to use 'create-drop' instead of 'update'.

# EXECUTION
The code for the application is developed in Visual Studio Code. To execute the application, you need to press the 'Run' button within the IDE, ensuring that it is set to the backend directory. Following that, open your preferred browser, such as Google Chrome, and navigate to https://localhost:8443/ to view the main page

# DATABASE ENTITY DIAGRAM
![DiagramERFASE1](https://github.com/user-attachments/assets/6642d870-c254-45a7-9266-530110b60c44)

# CLASS AND TEMPLATES DIAGRAM
![DAW Diagram](https://github.com/user-attachments/assets/ec9206cc-c70f-4c67-a037-b7da4063956c)


# GROUP MEMBERS PARTICIPATION
| Name | Tasks |Most important commits| Most worked on files | 
|--------------|--------------|--------------|---------------|
Laila El Khattabi El Hassnaoui |I have worked on several controllers as well as on the model and templates, and added the additional captcha technology | -[codedSomeControllers](https://github.com/CodeURJC-DAW-2024-25/webapp18/commit/12143f311a2336f25c1d224234dba73545c74311) <br> -[Captcha](https://github.com/CodeURJC-DAW-2024-25/webapp18/commit/4620fcfc1bfc101829d2ffcb674344233d07a73b#diff-b2469ee29439c23c45f2f41939ce95ae3d142728327838a0f10fb6813e5d4343) <br> -[apartmentInformation](https://github.com/CodeURJC-DAW-2024-25/webapp18/commit/737f1e13ee020060434159691ef2b26b8e8ee9c5) <br> -[profile and editProfile](https://github.com/CodeURJC-DAW-2024-25/webapp18/commit/0d393065412509a53d0c608dea2b4ca72e4ce242)  <br>-[sidebar,footer page](https://github.com/CodeURJC-DAW-2024-25/webapp18/commit/e80f0742c013a2a6680dc19514db039d6b828b09)| Profile, editProfile, footer and sidebar styles, captcha, apartmentInformation  and controllers, service, repository, model|
Carlos Hermán Andrés Andrés | AJAX loading, several controllers, apartment templates, database creation and configuration | - [viewApartmentsManager template working](https://github.com/CodeURJC-DAW-2024-25/webapp18/commit/5b9cdb1d85092b36417e56df6661aead08a77745)<br> - [Client reservation template done](https://github.com/CodeURJC-DAW-2024-25/webapp18/commit/c5969e07e5e2cc8407499ee615fdf824ab25fae3)<br> - [Index working](https://github.com/CodeURJC-DAW-2024-25/webapp18/commit/5fe4738fdea49ab75f4a7bf896253624adfe1c53)<br> - [Security now allows navigation, and the keystores](https://github.com/CodeURJC-DAW-2024-25/webapp18/commit/4829c6fbcab1b363debac50b4cacf57e754404a2)<br> - [Generic footer](https://github.com/CodeURJC-DAW-2024-25/webapp18/commit/9583b3c7086e53a310c0993438430f71045ecfde) | index.html, Apartment.controller, Security Configuration, viewApartmentsManager, initDatabseService |
Elena Ceinos Abeijón | Client reservations, apartment addition and edition. Fixed some styler errors and server calls. Add reservation done with data check | - [addReservations done](https://github.com/CodeURJC-DAW-2024-25/webapp18/commit/dcdfdad4fb9c8c452c2bed91fcc9550d0c9d2fa3) <br> - [add apartments](https://github.com/CodeURJC-DAW-2024-25/webapp18/commit/857bcb34b927ed383e27ff0d58c3334f7b1a4d72) <br> - [Show only validated's manager apts](https://github.com/CodeURJC-DAW-2024-25/webapp18/commit/f6259f9dac82ab8b0bf1711b52de421765eb2b3f) | Apartment controller, reservation controller, reviews controller, init app, some errors |
Daniel Gómez López | I have worken on some controllers, frontend templates as well as security and the graphic | - [Working web commit](https://github.com/CodeURJC-DAW-2024-25/webapp18/commit/f28bbcc8c3e2f9f5fa5254aa6fd56b0d8cd14157)<br> - [fixed register controller](https://github.com/CodeURJC-DAW-2024-25/webapp18/commit/397797de859c661348497fd7019c99ca5c8a3ce7)<br> - [reservations changes (to be fixed yet)](https://github.com/CodeURJC-DAW-2024-25/webapp18/commit/84e254e2e3e6452b678568d685a165b4308de994)<br> - [fixed nav bar](https://github.com/CodeURJC-DAW-2024-25/webapp18/commit/beb3f85ceab6e24f3567d0893872f45ace3e2dc4)<br> - [Half Fixed reviews](https://github.com/CodeURJC-DAW-2024-25/webapp18/commit/beb3f85ceab6e24f3567d0893872f45ace3e2dc4)<br> | Register, reservation.Controller, Reviews, navegation Bar, ClientReservations, Navegation Diagram|



