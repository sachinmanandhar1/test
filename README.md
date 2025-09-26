# PASTEAU-Sahel Duplicate

This project is a duplicate of the [PASTEAU-Sahel](https://pasteau-sahel.org/) web mapping application, re-architected as a modern, database-driven Java EE application.

## Features

*   **Interactive Map:** An OpenLayers map displaying administrative boundaries and various types of water points.
*   **Dynamic Filtering:** A sidebar allows users to toggle the visibility of different water point types (Puits, Forage, Mare).
*   **Interactive Data Creation:** Users can click anywhere on the map to add a new water point. A series of dialogs will guide them through selecting a type and entering details.
*   **Database Backend:** Water point data is stored in a database and managed via JPA (Java Persistence API).
*   **REST API:** A JAX-RS REST API serves the water point data to the frontend map in GeoJSON format.
*   **Web-Based Management:** A PrimeFaces-based UI allows administrators to perform CRUD (Create, Read, Update, Delete) operations on the water point data.
*   **Containerized Deployment:** The entire backend is containerized using Docker for easy setup and deployment.

## Project Structure

The project is organized into the following directories:

*   `backend/`: Contains the source code and configuration for the Java EE backend.
    *   `pom.xml`: Defines the project's Maven dependencies (Java EE, PrimeFaces, Hibernate, etc.).
    *   `src/main/java`: Contains the Java source code for JPA entities, services, REST endpoints, and web controllers.
    *   `src/main/webapp`: Contains the PrimeFaces XHTML pages for the management UI.
    *   `Dockerfile`: A multi-stage Dockerfile that builds the Maven project and deploys the resulting `.war` file to a WildFly server.
*   `data/`: Contains static geospatial data.
    *   `sahel_adm.geojson`: A GeoJSON file for administrative boundaries, loaded directly by the frontend.
*   `frontend/`: Contains the frontend map application code.
    *   `index.html`: The main HTML file, including the structure for the map, sidebar, and interactive dialogs.
    *   `style.css`: CSS for styling the application's UI.
    *   `app.js`: The JavaScript code that powers the OpenLayers map, handles user interactions, and communicates with the backend REST API.

## Technologies Used

*   **Backend**
    *   **Java EE 8**: The core platform for the application.
    *   **JPA (Hibernate)**: For object-relational mapping and database interaction.
    *   **JAX-RS**: For creating the REST API.
    *   **CDI**: For dependency injection.
    *   **PrimeFaces**: For the web-based management UI.
    *   **WildFly**: The application server.
    *   **Docker**: For containerizing and running the backend.
*   **Frontend**
    *   **OpenLayers**: A high-performance library for creating interactive maps.
    *   **HTML/CSS/JavaScript**: For the application structure, styling, and logic.

## How to Run

This application requires both a running backend container and a separate web server for the frontend.

### 1. Run the Backend

The backend is containerized using Docker.

a. **Build the Docker image:**
From the project's root directory, run the following command. This will build the Java EE application and package it into a WildFly server image.

```bash
docker build -t pasteau-sahel-backend ./backend
```

b. **Run the Docker container:**
Once the image is built, start the container.

```bash
docker run -p 8080:8080 pasteau-sahel-backend
```

This will start the WildFly server. You can access the two main parts of the backend:
*   **REST API:** `http://localhost:8080/api/waterpoints`
*   **Management UI:** `http://localhost:8080/admin/list.xhtml`

### 2. Run the Frontend

The frontend files must be served by a simple web server.

a. **Start a local web server:**
In a **new terminal window**, navigate to the project's root directory and run one of the following commands:

If you have Python:
```bash
python -m http.server
```

If you have Node.js and `npx`:
```bash
npx serve .
```

b. **Open the application in your browser:**
Navigate to `http://localhost:8000/frontend/`. The map will load and fetch data from the backend. You can now filter data using the sidebar or click on the map to add new water points.