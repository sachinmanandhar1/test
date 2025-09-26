# PASTEAU-Sahel Duplicate

This project is a duplicate of the [PASTEAU-Sahel](https://pasteau-sahel.org/) web mapping application. It is built with a functional backend using GeoServer and JBoss (WildFly) running in Docker, and a frontend using OpenLayers that acts as a client to the backend.

## Project Structure

The project is organized into the following directories:

- `backend/`: Contains the configuration and `Dockerfile` for the backend.
  - `Dockerfile`: A functional Dockerfile that builds a JBoss (WildFly) image, downloads and deploys the GeoServer WAR file.
  - `geoserver_config/`: GeoServer configuration files that are copied into the Docker image to define workspaces and data layers.
- `data/`: Contains the geospatial data used in the application, which is also copied into the backend container.
  - `sahel_adm.geojson`: A sample GeoJSON file with administrative boundaries.
  - `waterpoints.geojson`: A consolidated GeoJSON file for all water points, with a `type` attribute ("PUITS", "FORAGE", "MARE").
- `frontend/`: Contains the frontend application code.
  - `index.html`: The main HTML file for the application.
  - `style.css`: CSS for styling the application's UI.
  - `app.js`: The JavaScript code that connects to the GeoServer backend via WFS and allows for dynamic filtering of water point types.

## Data Model

The application uses a structured data model:
- **Administrative Boundaries**: A polygon layer for regional boundaries.
- **Water Points**: A single point layer for all water sources. Each feature has a `type` property to distinguish between:
  - `PUITS` (Wells)
  - `FORAGE` (Boreholes)
  - `MARE` (Ponds)

## Technologies Used

- **Backend**
  - **GeoServer**: For serving geospatial data via WFS, with CQL filters for dynamic data requests.
  - **JBoss (WildFly)**: The application server running GeoServer.
  - **Docker**: For containerizing and running the backend services.

- **Frontend**
  - **OpenLayers**: A high-performance library for creating interactive maps.
  - **HTML/CSS/JavaScript**: For the application structure, styling, and logic.

## How to Run

This application requires both a running backend container and a web server for the frontend.

### 1. Run the Backend

The backend is containerized using Docker.

a. **Build the Docker image:**
From the project's root directory, run the following command. This will download WildFly, GeoServer, and set up the environment. This may take a few minutes.

```bash
docker build -t pasteau-sahel-backend ./backend
```

b. **Run the Docker container:**
Once the image is built, start the container.

```bash
docker run -p 8080:8080 pasteau-sahel-backend
```

This will start the WildFly server with GeoServer deployed. You can verify that GeoServer is running by navigating to `http://localhost:8080/geoserver` in your browser.

### 2. Run the Frontend

The frontend files need to be served by a separate, simple web server.

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
Navigate to `http://localhost:8000/frontend/` (or the appropriate URL for your web server). The map should load, making WFS requests to the GeoServer instance. The sidebar will allow you to toggle the visibility of the administrative boundaries and filter the water points by type.