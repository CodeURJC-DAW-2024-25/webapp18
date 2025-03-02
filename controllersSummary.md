# 🌐 Spring MVC Controllers Documentation

This document provides a comprehensive architectural overview of the application's controller classes, delineating their primary responsibilities and detailing their respective endpoint configurations.

## 📋 Table of Contents

- [Review Controller](#review-controller)
- [Room Controller](#room-controller) 
- [User Controller](#user-controller)
- [Apartment Controller](#apartment-controller)
- [Default Controller](#default-controller)
- [Reservation Controller](#reservation-controller)

## 📝 Review Controller

The `ReviewController` serves as the primary management interface for apartment review-related operations, facilitating review submission, retrieval, and dynamic content loading.

### Principal Methods

| Method | Endpoint | Operational Description |
|--------|----------|-------------------------|
| **postReview** | `POST /postapartmentReviews/{id}` | Processes and persistently stores apartment reviews with mandatory user authentication. |
| **apartmentReviews** | `GET /apartmentReviews/{id}` | Renders the initial review compilation for a specific apartment, presenting the first six reviews. |
| **loadMoreReviews** | `GET /loadMoreReviews/{id}/{start}/{end}` | Implements AJAX-based pagination mechanism for incremental review retrieval. |

#### Sophisticated Features:
- Comprehensive statistical analysis including aggregate review count and score distribution
- Advanced infinite scrolling implementation via AJAX methodology
- Rigorous validation protocols ensuring review submission only by verified managers

## 🏠 Room Controller

The `RoomController` represents a foundational architectural component, currently functioning as a dependency injection mechanism for the `RoomService`. Although currently minimal in implementation, it establishes a robust framework for future room-related operational expansions.

## 👤 User Controller

The `UserController` orchestrates comprehensive user account lifecycle management, encompassing authentication, registration, profile manipulation, and role-specific navigational interfaces.

### Authentication and Registration Workflow

| Method | Endpoint | Functional Description |
|--------|----------|------------------------|
| **login** | `GET /login` | Renders the authentication interface. |
| **loginError** | `GET /loginrror` | Presents authentication failure notification interface. |
| **register** | `GET /register` | Displays user registration interface. |
| **register** | `POST /register` | Processes registration, implementing password encryption and role assignment. |
| **nickTaken** | `GET /nickTaken` | Indicates username unavailability during registration. |

### Profile Management Capabilities

| Method | Endpoint | Operational Description |
|--------|----------|-------------------------|
| **profile** | `GET /profile` | Generates personalized user profile visualization. |
| **editProfile** | `GET /editprofile/{id}` | Presents profile modification interface. |
| **editProfile** | `POST /replace/{id}` | Executes comprehensive user profile information updates. |
| **editImage** | `POST /editprofileimage/{id}` | Facilitates profile image modification. |
| **downloadImage** | `GET /profile/{id}/images` | Retrieves user-associated profile imagery. |

### Navigation and Dashboard Interfaces

| Method | Endpoint | Functional Description |
|--------|----------|------------------------|
| **index** | `GET /` | Renders homepage with sophisticated apartment recommendation algorithm. |
| **indexSearch** | `GET /indexsearch` | Implements apartment name-based search functionality. |
| **viewApartmentsManager** | `GET /viewApartmentsManager` | Displays initial six apartments managed by current user. |
| **chartsManager** | `GET /chartsManager` | Generates apartment-specific analytics dashboard. |
| **chartsAdmin** | `GET /chartsadmin` | Provides comprehensive administrative analytics interface. |

### Administrative Operational Methods

| Method | Endpoint | Operational Description |
|--------|----------|-------------------------|
| **managerValidation** | `GET /managerValidation` | Catalogs managers pending system validation. |
| **rejectManager** | `POST /rejection/{id}` | Executes manager application rejection protocol. |
| **acceptManager** | `POST /acceptance/{id}` | Implements manager application acceptance mechanism. |
| **managerList** | `GET /managerlist` | Generates comprehensive manager registry. |

## 🏢 Apartment Controller

The `ApartmentController` manages the comprehensive lifecycle of apartment listings, encompassing creation, modification, image management, and associated client information processing.

### Apartment CRUD Operational Endpoints

| Method | Endpoint | Functional Description |
|--------|----------|------------------------|
| **addApartment** | `GET /addApartment` | Renders apartment creation interface. |
| **createApartment** | `POST /createApartment` | Processes apartment entity creation, including room and image associations. |
| **editApartment** | `GET /editApartment/{id}` | Presents apartment modification interface. |
| **updateApartment** | `POST /updateApartment/{id}` | Executes comprehensive apartment information updates. |
| **deleteApartment** | `GET /deleteApartment/{id}` | Implements apartment entity removal mechanism. |

### Image Management Capabilities

| Method | Endpoint | Operational Description |
|--------|----------|-------------------------|
| **addApartmentPhoto** | `GET /addApartmentPhoto/{imgName}` | Provides photo addition interface. |
| **editImage** | `POST /editApartmentimage/{id}` | Facilitates apartment image modification. |
| **selectImage** | `POST /selectApartmentimage/{imgName}` | Implements image selection mechanism. |
| **downloadImage** | `GET /index/{id}/images` | Retrieves apartment-associated imagery. |

### Visualization and Dynamic Loading Mechanisms

| Method | Endpoint | Functional Description |
|--------|----------|------------------------|
| **apartmentInformation** | `GET /apartmentInformation/{id}` | Generates comprehensive apartment information display. |
| **clientlist** | `GET /clientlist/{id}` | Presents clients associated with specific apartment. |
| **loadMoreApartments** | `GET /loadMoreApartments/{start}/{end}` | Implements infinite scrolling through AJAX methodology. |
| **loadMoreApartmentsManagerView** | `GET /loadMoreApartmentsManagerView/{start}/{end}` | Provides manager-specific apartment list pagination. |

## 🔄 Default Controller

The `DefaultController` functions as a global attribute provider through `@ControllerAdvice`, ensuring consistent data exposure across all view interfaces.

### Global Attribute Generation

| Method | Operational Description |
|--------|-------------------------|
| **userType** | Dynamically assigns user role classification to model interfaces. |
| **isAdmin** | Generates administrative user identification boolean. |
| **isManager** | Produces manager role identification boolean. |
| **isClient** | Creates client role identification boolean. |
| **isUser** | Generates standard user role identification boolean. |
| **getPath** | Appends current request path to model interfaces. |

## 📅 Reservation Controller

The `ReservationController` manages the comprehensive reservation workflow, encompassing booking processes, reservation management, and cancellation protocols.

### Reservation Operational Endpoints

| Method | Endpoint | Functional Description |
|--------|----------|------------------------|
| **addReservation** | `POST /addReservation/{id}` | Generates reservation after exhaustive room availability verification. |
| **clientReservation** | `GET /clientReservations` | Presents initial reservation subset for client (maximum three entries). |
| **loadMoreReservations** | `GET /loadMoreReservations/{start}/{end}` | Implements reservation list expansion via AJAX methodology. |
| **reservationInfo** | `GET /reservationInfo/{id}` | Generates comprehensive reservation details interface. |
| **deleteReservation** | `GET /cancelReservation/{id}` | Executes reservation cancellation with referential integrity maintenance. |

**Architectural Note**: Reservation cancellation implements a sophisticated cascade deletion strategy, systematically removing interdependent references across user, apartment, and room entity domains prior to reservation entity removal.
