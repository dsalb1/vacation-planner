package org.launchcode.vacationplanner.Models.Helpers;

import com.google.maps.DistanceMatrixApi;
import com.google.maps.GeoApiContext;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DistanceMatrix;

import java.io.IOException;

public class getDistanceMatrix {

    private String[] origin = new String[1];
    private String[] destination = new String[1];

    public getDistanceMatrix(String org, String dest){
        this.origin[0] = org;
        this.destination[0] = dest;
    }

    public String getDistanceMiles() throws InterruptedException, ApiException, IOException {
        try {
            GeoApiContext context = new GeoApiContext.Builder()
                    .apiKey(new GoogleMapsApiKey().getKey()) // gets api key from helper class
                    .build();
            DistanceMatrix distanceMatrix = DistanceMatrixApi.getDistanceMatrix(context, this.origin, this.destination).await();
            return (distanceMatrix.rows[0].elements[0].distance).toString();
        } catch (ApiException e) {
            return "";
        }
    }

}




