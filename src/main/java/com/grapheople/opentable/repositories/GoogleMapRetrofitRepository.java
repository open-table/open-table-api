package com.grapheople.opentable.repositories;

import com.fasterxml.jackson.databind.JsonNode;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by pax on 2018. 2. 7..
 */
public interface GoogleMapRetrofitRepository {
    @GET("/maps/api/place/nearbysearch/json")
    Call<JsonNode> getPlace(@Query(value = "location") String location,
                            @Query(value = "radius") Integer radius,
                            @Query(value = "types") String types,
                            @Query(value = "key") String googleApiKey);

    @GET("/maps/api/place/nearbysearch/json")
    Call<JsonNode> getPlaceNextPage(@Query(value = "key") String googleApiKey,
                                    @Query(value = "pagetoken") String pageToken);
}
