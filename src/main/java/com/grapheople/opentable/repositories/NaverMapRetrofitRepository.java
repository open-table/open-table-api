package com.grapheople.opentable.repositories;

import com.fasterxml.jackson.databind.JsonNode;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Query;

import java.util.Map;

/**
 * Created by pax on 2018. 2. 7..
 */
public interface NaverMapRetrofitRepository {
    @GET("/v1/search/local.json")
    Call<JsonNode> getLocalInfo(@Query(value = "query") String query,
                                @Query(value = "start") Integer start,
                                @Query(value = "display") Integer display,
                                @Query(value = "sort") String sort,
                                @HeaderMap Map<String, String> headers);
}
