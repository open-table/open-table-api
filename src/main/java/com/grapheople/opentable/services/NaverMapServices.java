package com.grapheople.opentable.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.Maps;
import com.grapheople.opentable.configs.properties.NaverApiProperties;
import com.grapheople.opentable.models.Restaurant;
import com.grapheople.opentable.repositories.NaverMapRetrofitRepository;
import com.grapheople.opentable.repositories.RestaurantRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.util.List;
import java.util.Map;
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
    @Autowired private RestaurantRepository restaurantRepository;
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

    public void insertRestaurant(String query, Integer page, Integer pageSize, String sort) {
        try {
            Call<JsonNode> request = naverMapRetrofitRepository.getLocalInfo(query, (page-1)*pageSize+1, pageSize, sort, headers);
            Response<JsonNode> result = request.execute();
            if (result.code() == 200 && result.body().get("items").size() > 0) {
                StreamSupport.stream(result.body().get("items").spliterator(), false)
                .forEach(jsonNode -> {
                    try {
                        Restaurant savedRestaurant = restaurantRepository.save(new Restaurant(jsonNode));
                        if (savedRestaurant == null) {
                            log.error("save restaurant failed. title:"+jsonNode.get("title"));
                        }
                        else {
                            log.info("new restaurant is inserted. title:"+savedRestaurant.getTitle());
                        }
                    }catch (Exception e) {
                        log.error("save restaurant failed. title:"+jsonNode.get("title")+" reason:"+e.getMessage());
                    }
                });
            }

        } catch (Exception e) {
            log.error(e.toString());
        }
    }

    public JsonNode findRestaurant(String query, Integer page, Integer pageSize, String sort) {
        try {
            Call<JsonNode> request = naverMapRetrofitRepository.getLocalInfo(query, (page-1)*pageSize+1, pageSize, sort, headers);
            Response<JsonNode> result = request.execute();
            return result.body();
        } catch (Exception e) {
            log.error(e.toString());
        }
        return null;
    }

    public List<Restaurant> getRestaurantList() {
        return restaurantRepository.findAll();
    }
}
