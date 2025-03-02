# Spring MVC Controllers Documentation

This document provides a comprehensive overview of all controller classes in the application, explaining their purpose and detailing their endpoints.

## Table of Contents

- [Review Controller](#review-controller)
- [Room Controller](#room-controller) 
- [User Controller](#user-controller)
- [Apartment Controller](#apartment-controller)
- [Default Controller](#default-controller)
- [Reservation Controller](#reservation-controller)

## Review Controller

`ReviewController` manages all operations related to apartment reviews, including posting, viewing, and dynamic loading of reviews.

### Methods

| Method | URL | Description |
|--------|-----|-------------|
| **postReview** | `POST /postapartmentReviews/{id}` | Adds a review for an apartment. Requires user authentication. |
| **apartmentReviews** | `GET /apartmentReviews/{id}` | Displays the reviews page for an apartment with the first 6 reviews. |
| **loadMoreReviews** | `GET /loadMoreReviews/{id}/{start}/{end}` | AJAX endpoint to load additional reviews based on pagination parameters. |

#### Key Features:
- Statistical data including total reviews and score distributions
- Infinite scrolling support through AJAX loading
- Validation to ensure managers are verified before reviews can be posted

## Room Controller

`RoomController` is a minimal controller that serves as an injection point for the RoomService. It currently has no implemented methods but provides a foundation for future room-related operations.

## User Controller

`UserController` handles user account management, profile operations, and role-specific views.

### Authentication & Registration

| Method | URL | Description |
|--------|-----|-------------|
| **login** | `GET /login` | Displays the login page. |
| **loginError** | `GET /loginrror` | Shows login error page. |
| **register** | `GET /register` | Displays user registration form. |
| **register** | `POST /register` | Processes registration, encrypts password, assigns roles. |
| **nickTaken** | `GET /nickTaken` | Shown when a username already exists. |

### Profile Management

| Method | URL | Description |
|--------|-----|-------------|
| **profile** | `GET /profile` | Displays the current user's profile. |
| **editProfile** | `GET /editprofile/{id}` | Shows profile edit form. |
| **editProfile** | `POST /replace/{id}` | Updates user profile information. |
| **editImage** | `POST /editprofileimage/{id}` | Updates profile image. |
| **downloadImage** | `GET /profile/{id}/images` | Retrieves a user's profile image. |

### Navigation & Dashboards

| Method | URL | Description |
|--------|-----|-------------|
| **index** | `GET /` | Home page with recommended apartments using a recommendation algorithm. |
| **indexSearch** | `GET /indexsearch` | Searches apartments by name. |
| **viewApartmentsManager** | `GET /viewApartmentsManager` | Shows first 6 apartments of a manager. |
| **chartsManager** | `GET /chartsManager` | Analytics for a manager's apartments. |
| **chartsAdmin** | `GET /chartsadmin` | Admin dashboard with analytics. |

### Admin Functions

| Method | URL | Description |
|--------|-----|-------------|
| **managerValidation** | `GET /managerValidation` | Lists managers pending validation. |
| **rejectManager** | `POST /rejection/{id}` | Rejects a manager application. |
| **acceptManager** | `POST /acceptance/{id}` | Accepts a manager application. |
| **managerList** | `GET /managerlist` | Lists all managers. |

## Apartment Controller

`ApartmentController` manages apartment listings, including creation, editing, image management, and related client information.

### Apartment CRUD Operations

| Method | URL | Description |
|--------|-----|-------------|
| **addApartment** | `GET /addApartment` | Form to add a new apartment. |
| **createApartment** | `POST /createApartment` | Creates apartment with rooms and image. |
| **editApartment** | `GET /editApartment/{id}` | Form to edit an apartment. |
| **updateApartment** | `POST /updateApartment/{id}` | Updates apartment details. |
| **deleteApartment** | `GET /deleteApartment/{id}` | Removes an apartment. |

### Image Management

| Method | URL | Description |
|--------|-----|-------------|
| **addApartmentPhoto** | `GET /addApartmentPhoto/{imgName}` | Interface for adding photos. |
| **editImage** | `POST /editApartmentimage/{id}` | Updates apartment image. |
| **selectImage** | `POST /selectApartmentimage/{imgName}` | Selects an image. |
| **downloadImage** | `GET /index/{id}/images` | Retrieves an apartment's image. |

### Viewing & AJAX Loading

| Method | URL | Description |
|--------|-----|-------------|
| **apartmentInformation** | `GET /apartmentInformation/{id}` | Detailed apartment information. |
| **clientlist** | `GET /clientlist/{id}` | Shows clients who booked this apartment. |
| **loadMoreApartments** | `GET /loadMoreApartments/{start}/{end}` | AJAX for infinite scrolling. |
| **loadMoreApartmentsManagerView** | `GET /loadMoreApartmentsManagerView/{start}/{end}` | AJAX for manager's apartment list. |

## Default Controller

`DefaultController` provides application-wide model attributes through `@ControllerAdvice` to expose common data across all views.

### Global Attributes

| Method | Description |
|--------|-------------|
| **userType** | Adds user role type (Admin/Client/Manager) to all models. |
| **isAdmin** | Boolean indicating if user is an admin. |
| **isManager** | Boolean indicating if user is a manager. |
| **isClient** | Boolean indicating if user is a client. |
| **isUser** | Boolean indicating if user has the USER role. |
| **getPath** | Adds current request path to all models. |

## Reservation Controller

`ReservationController` handles booking flow, reservation management, and cancellation processes.

### Reservation Operations

| Method | URL | Description |
|--------|-----|-------------|
| **addReservation** | `POST /addReservation/{id}` | Creates reservation after checking room availability. |
| **clientReservation** | `GET /clientReservations` | Lists a client's reservations (initially up to 3). |
| **loadMoreReservations** | `GET /loadMoreReservations/{start}/{end}` | AJAX loading of additional reservations. |
| **reservationInfo** | `GET /reservationInfo/{id}` | Detailed reservation information. |
| **deleteReservation** | `GET /cancelReservation/{id}` | Cancels reservation and cleans up relationships. |

**Note**: Reservation cancellation involves removing references from user, apartment, and room entities before deleting the reservation itself.
