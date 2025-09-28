# PASTEAU-Sahel Duplicate

This project is a duplicate of the [PASTEAU-Sahel](https://pasteau-sahel.org/) web mapping application, completely re-architected as a modern, full-stack, database-driven Java EE application.

## Features

*   **Interactive Map:** An OpenLayers map displaying water points from a PostgreSQL database.
*   **Rich Data Model:** Water points are modeled with a comprehensive set of attributes, including geological, administrative, and technical details.
*   **Interactive Data Creation:** Users can click anywhere on the map to add a new water point via a guided dialog.
*   **Detailed Popups:** Clicking on an existing water point displays its full attributes in a map popup.
*   **Database Backend:** A PostgreSQL database with PostGIS capabilities (via hibernate-spatial) stores and manages all water point data.
*   **REST API:** A JAX-RS REST API serves the water point data to the frontend map in GeoJSON format.
*   **Web-Based Management:** A PrimeFaces-based UI allows administrators to perform CRUD (Create, Read, Update, Delete) operations on the comprehensive water point data.
*   **Containerized Deployment:** The entire backend, including the application server and the database, is managed via Docker Compose for easy setup and deployment.

## Project Structure

The project is organized into the following directories:

*   `docker-compose.yml`: Manages the application and database services.
*   `backend/`: Contains the source code and configuration for the Java EE backend.
    *   `pom.xml`: Defines the project's Maven dependencies (Java EE, PrimeFaces, Hibernate, Hibernate Spatial, PostgreSQL driver).
    *   `src/main/java`: Contains the Java source code for the comprehensive JPA entity, services, REST endpoints, and web controllers.
    *   `src/main/webapp`: Contains the PrimeFaces XHTML pages for the management UI, including a tabbed interface for handling the large number of fields.
    *   `Dockerfile`: A multi-stage Dockerfile that builds the Maven project and deploys it to a WildFly server.
*   `data/`: Contains static geospatial data.
    *   `sahel_adm.geojson`: A GeoJSON file for administrative boundaries, loaded directly by the frontend.
*   `frontend/`: Contains the frontend map application code.
    *   `index.html`: The main HTML file, including the structure for the map, sidebar, and popups.
    *   `style.css`: CSS for styling the application's UI.
    *   `app.js`: The JavaScript code that powers the OpenLayers map, handles all user interactions (creating points, viewing details), and communicates with the backend REST API.

## Technologies Used

*   **Backend**
    *   **Java EE 8**: The core platform for the application.
    *   **JPA (Hibernate)**: For object-relational mapping.
    *   **Hibernate Spatial**: For handling `geometry` data types.
    *   **PostgreSQL**: The relational database for storing data.
    *   **JAX-RS**: For creating the REST API.
    *   **PrimeFaces**: For the web-based management UI.
    *   **WildFly**: The application server.
    *   **Docker & Docker Compose**: For containerizing and orchestrating the backend services.
*   **Frontend**
    *   **OpenLayers**: A high-performance library for creating interactive maps.
    *   **HTML/CSS/JavaScript**: For the application structure, styling, and logic.

## How to Run

The entire application (frontend and backend) is containerized and managed by Docker Compose.

From the project's root directory, run the following command:
```bash
sudo docker compose up --build
```
This will:
1.  Build the PostgreSQL database image.
2.  Build the Java EE application using the `backend/Dockerfile`.
3.  Start the Nginx, application, and database containers and connect them.

Once the services are running, you can access the application:
*   **Main Application:** `http://localhost:8000/frontend/`
*   **Management UI:** `http://localhost:8000/admin/list.xhtml`
*   **REST API:** `http://localhost:8000/api/waterpoints`