# 🏚🏝 dreamLife 

## Preparation 1

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


## Preparation 2

### Screenshots

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

### Diagram Navigation
![DIAGRAMA_NAVEFGACION](https://github.com/user-attachments/assets/2abfd042-32b9-46eb-aeed-7328b53ac9dd)


## PHASE 1

### Screenshots

- **index** -> This is the welcome page. Also, you can view the validated-manager apartments and click to view each ones info (if you're a user, you can book) and reviews. Also, in the nav bar you can click on "login". If you're already logged, you can click on "profile" to see your details. You can also load more apartments
![image](https://github.com/user-attachments/assets/ec4f0873-8a6e-432c-b1f3-c16c53c34955)

![image](https://github.com/user-attachments/assets/d163e953-b385-49db-bf73-aa069890ea37)
- **login** -> In this page, if you already have a user, you can log in. If you don't, you can click on "create an account", it will redirect you to the register page. If your  credentials are invalid, you'll be redirected to loginError
- **loginError** -> This is the login Error page
- ![image](https://github.com/user-attachments/assets/2f39e5fb-6a20-4660-99f1-cdc8bef024fe)

- **register** -> In this page you can register. Make sure you don't repeat mail nor username. You can choose your role but managers must be validated.
  ![image](https://github.com/user-attachments/assets/031d2582-3aee-4c72-8bda-f2b64601959d)

- **profile** -> In this page, you can check your info. Also, you can see a sidebar with your role and some options depending on it. Also, you can click on "edit profile" or "sign out". If you're a manager, you can also see wether you're validated or not. *ONLY USERS*
  ![image](https://github.com/user-attachments/assets/c5648f58-3473-4eac-8536-1cc0b2a94738)
  ![image](https://github.com/user-attachments/assets/01db8acd-0d03-415e-be8d-4163f3c81f8d)

- **edit profile** -> In this page, you can change your details and your photo. *ONLY USERS* 
  ![image](https://github.com/user-attachments/assets/e4d3a57b-ea63-4a45-a44a-c50f3de93491)
- **apartment information** -> if you're not logged, you can just check the info. If you're a client, you can check the dates to make a booking. If you're any of the roles, you can also click on "reviews"
- **apartment reviews** -> You can check the reviews and if you're a user, you can leave yours. Also you can check the chart to see how many reviews the apartment has. If there are more than 6 comments, you can load more.
  ![image](https://github.com/user-attachments/assets/767c2905-c841-45e2-8888-699597ea4f59)
  ![image](https://github.com/user-attachments/assets/53c3036b-62fd-4d2f-8b08-a9eb51c7a9c5)
  *ONLY USERS-last*


