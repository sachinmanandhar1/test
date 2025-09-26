package org.pasteau_sahel.backend.rest;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.pasteau_sahel.backend.entities.Waterpoint;
import org.pasteau_sahel.backend.services.WaterpointService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("/waterpoints")
public class WaterpointResource {

    @Inject
    private WaterpointService waterpointService;

    @GET
    @Produces("application/json")
    public Response getWaterpointsAsGeoJSON() {
        List<Waterpoint> waterpoints = waterpointService.findAll();

        String features = waterpoints.stream()
                .filter(wp -> wp.getGeom() != null)
                .map(wp -> {
                    String properties = String.format(
                        "\"gid\": %d, " +
                        "\"noBook\": \"%s\", " +
                        "\"catOuvrag\": \"%s\", " +
                        "\"typeOpen\": \"%s\", " +
                        "\"zone\": \"%s\", " +
                        "\"neighborhood\": \"%s\", " +
                        "\"altitude\": %d",
                        wp.getGid(),
                        wp.getNoBook() != null ? wp.getNoBook() : "",
                        wp.getCatOuvrag() != null ? wp.getCatOuvrag() : "",
                        wp.getTypeOpen() != null ? wp.getTypeOpen() : "",
                        wp.getZone() != null ? wp.getZone() : "",
                        wp.getNeighborhood() != null ? wp.getNeighborhood() : "",
                        wp.getAltitude() != null ? wp.getAltitude() : 0
                    );

                    return String.format(
                        "{\"type\": \"Feature\", \"geometry\": {\"type\": \"Point\", \"coordinates\": [%f, %f]}, \"properties\": {%s}}",
                        wp.getGeom().getX(),
                        wp.getGeom().getY(),
                        properties
                    );
                })
                .collect(Collectors.joining(","));

        String geoJson = String.format("{\"type\": \"FeatureCollection\", \"features\": [%s]}", features);
        return Response.ok(geoJson).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createWaterpoint(Waterpoint waterpoint) {
        // Create the Point geometry from latitude and longitude for new waterpoints
        if (waterpoint.getLatitude() != null && waterpoint.getLongitude() != null) {
            GeometryFactory geometryFactory = new GeometryFactory();
            Point point = geometryFactory.createPoint(new Coordinate(waterpoint.getLongitude(), waterpoint.getLatitude()));
            point.setSRID(4326);
            waterpoint.setGeom(point);
        }
        waterpointService.save(waterpoint);
        return Response.status(Response.Status.CREATED).build();
    }
}