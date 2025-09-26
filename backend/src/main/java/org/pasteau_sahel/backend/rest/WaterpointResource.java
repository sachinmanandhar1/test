package org.pasteau_sahel.backend.rest;

import org.pasteau_sahel.backend.entities.Waterpoint;
import org.pasteau_sahel.backend.services.WaterpointService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
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

        // Manually construct GeoJSON for simplicity
        String geoJson = "{\"type\": \"FeatureCollection\", \"features\": [" +
                waterpoints.stream()
                        .map(wp -> "{\"type\": \"Feature\", " +
                                "\"geometry\": {\"type\": \"Point\", \"coordinates\": [" + wp.getLongitude() + ", " + wp.getLatitude() + "]}, " +
                                "\"properties\": {\"name\": \"" + wp.getName() + "\", \"type\": \"" + wp.getType().name() + "\"}" +
                                "}")
                        .collect(Collectors.joining(",")) +
                "]}";

        return Response.ok(geoJson).build();
    }
}