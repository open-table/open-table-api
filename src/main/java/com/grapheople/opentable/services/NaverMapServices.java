package com.grapheople.opentable.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.Maps;
import com.grapheople.opentable.configs.properties.NaverApiProperties;
import com.grapheople.opentable.models.Restaurant;
import com.grapheople.opentable.repositories.NaverMapRetrofitRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Created by pax on 2018. 2. 8..
 */
@Service
@Slf4j
public class NaverMapServices {
    private final NaverApiProperties naverApiProperties;
    private Retrofit retrofit;
    private NaverMapRetrofitRepository naverMapRetrofitRepository;
    private Map<String, String> headers = Maps.newHashMap();

    public NaverMapServices(NaverApiProperties naverApiProperties) {
        this.naverApiProperties = naverApiProperties;
        retrofit = new Retrofit.Builder()
                .baseUrl("https://openapi.naver.com")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
        naverMapRetrofitRepository = retrofit.create(NaverMapRetrofitRepository.class);
        headers.put("X-Naver-Client-Id", naverApiProperties.getId());
        headers.put("X-Naver-Client-Secret", naverApiProperties.getSecret());
    }

    public JsonNode getLocalInfo(String query, Integer page, Integer pageSize, String sort) {
        try {
            Call<JsonNode> request = naverMapRetrofitRepository.getLocalInfo(query, page, pageSize, sort, headers);
            Response<JsonNode> result = request.execute();
            if (result.code() == 200 && result.body().get("items").size() > 0) {
                List<Restaurant> restaurantList = StreamSupport.stream(result.body().get("items").spliterator(), false)
                        .map(jsonNode -> new Restaurant(jsonNode)).collect(Collectors.toList());
            }
            return result.body();
        } catch (Exception e) {
            log.error(e.toString());
        }
        return null;
    }
}
