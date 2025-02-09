# 🏚🏝 dreamLife 

## Phase 1

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


## Phase 2

### Screenshots

- **apartmentInformation** -> This page displays the description of the apartment, as well as pictures and the categories it belongs to. It has some reviews as well.
![image](https://github.com/user-attachments/assets/66c7fee9-168b-4146-a9b0-0bc46062c011)

- **apartmentValidation** -> In this page, the admin can accept or deny the inclusion of new apartments to the app. It also shows some info about the apartments.
  ![image](https://github.com/user-attachments/assets/774caa74-0485-47c0-b066-4301c38fb245)

- **clientReservation** -> Shows all reservations made by a user. Accessible by admin and users alike.
![image](https://github.com/user-attachments/assets/88a4a9b1-6fc0-46ce-9efd-c09bc044e612)

- **editApartment** -> Allows a manager to edit the attributes of an apartment.
![image](https://github.com/user-attachments/assets/3dcc5e9c-eba3-4e7f-a14f-3ec02921dded)

- **index.html** -> Main page of the web application. Here you can select your preferred hotel, search for others, access your profile, log in, contact us...
![image](https://github.com/user-attachments/assets/9f3e2c9f-9e84-42aa-866c-dbb8c187c8ac)

-**managerList** -> Shows all managers included in the database. Can make certain actions with them, and show their current apartments.
![image](https://github.com/user-attachments/assets/1f398434-9260-4115-8fe8-2b7fc0315f92)

- **profile** -> Shows personal data about the user and a picture.
![image](https://github.com/user-attachments/assets/415cf117-1887-4a4e-aae2-56130257f2b1)

- **viewApartmentsManager** -> Shows all the apartments belonging to a manager, and some info about them.
![image](https://github.com/user-attachments/assets/42439a76-c780-430f-9ddd-b8a7a368284d)


- **Apartment Review** -> In the apartment review page, you can view some booker's opinions
  ![image](https://github.com/user-attachments/assets/91bd9a89-9e17-4cd5-9177-bd8d828b9f2a)

- **Chart pages** -> In the admin, manager and user charts, you can view detailed information about the bookings and, it also shows the notifications
![image](https://github.com/user-attachments/assets/a639a5cb-7894-4ce5-ad41-70b6b474daba)
![image](https://github.com/user-attachments/assets/7f157335-bf25-4b5f-ac81-60c820bbddbd)
![image](https://github.com/user-attachments/assets/decce0e9-4948-4074-b06a-dc4f47dc8e5b)


- **Contact page** -> In the contact page, you have a form to send a direct message. Also you can enter the footer information

![image](https://github.com/user-attachments/assets/353fcbff-244a-4b30-84f3-84286ee16001)
![image](https://github.com/user-attachments/assets/88efe667-0256-4790-a700-1eefeed1e779)

- **Edit profile** -> In this page, you can edit your profile's info and view some details.
![image](https://github.com/user-attachments/assets/7a912d3d-0a76-48b1-a258-ad90918bd2a1)

  - **Login page** -> In this page, you can login if you have a user or, otherwise, navigate to the register page.
![image](https://github.com/user-attachments/assets/57f1fee3-2258-4c29-aaf8-3e723724ec11)


-  **Register page** -> In this page, you enter some info about yourself to create an account
  ![image](https://github.com/user-attachments/assets/732e9e69-47c9-4ad4-8dc5-5f1338638db1)




### Navegation diagram
![image](https://github.com/user-attachments/assets/ac5577f3-5074-43ff-a12d-198d7e2eda97)

Navigation diagram of the SideBar with buttons for:

- **Personal Data**
- **My Reservations**
- **My Apartments**
- **Apartment Management**
  - View Managers
  - Validate New Apartments
- **Statistics**
  - User Charts
  - Apartment Charts
- **Main Page**
  - Return to Main Page


- From the main page, you can browse the "about us", "profile", "contact", "login", "apartmentInformation" and "apartmentReviews". It's the center point and can access pretty much any other business related page.
![image](https://github.com/user-attachments/assets/b86b2450-1f01-4775-9375-c6b5d460b9db)


