package com.grapheople.opentable.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.grapheople.opentable.configs.properties.GoogleApiProperties;
import com.grapheople.opentable.repositories.GoogleMapRetrofitRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Created by pax on 2018. 2. 7..
 */
@Service
@Slf4j
public class GoogleMapService {
    private final GoogleApiProperties googleApiProperties;
    private Retrofit retrofit;
    private final GoogleMapRetrofitRepository googleMapRetrofitRepository;

    public GoogleMapService(GoogleApiProperties googleApiProperties) {
        this.googleApiProperties = googleApiProperties;
        retrofit = new Retrofit.Builder()
                .baseUrl("https://maps.googleapis.com")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
        googleMapRetrofitRepository = retrofit.create(GoogleMapRetrofitRepository.class);
    }

    public JsonNode getRestaurant() {
        try {
            Call<JsonNode> response = googleMapRetrofitRepository.getPlace(googleApiProperties.getKakaoPangyoLocation(), googleApiProperties.getDefaultRadius(), "restaurant", googleApiProperties.getKey());
            return response.execute().body();
        } catch (Exception e) {
            log.error(e.toString());
        }
        return null;
    }
}
