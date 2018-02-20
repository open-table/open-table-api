package com.grapheople.opentable.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.grapheople.opentable.configs.properties.NaverApiProperties;
import com.grapheople.opentable.enums.DistanceType;
import com.grapheople.opentable.enums.StartingPoint;
import com.grapheople.opentable.models.Restaurant;
import com.grapheople.opentable.models.RestaurantDistance;
import com.grapheople.opentable.repositories.NaverMapRetrofitRepository;
import com.grapheople.opentable.repositories.RestaurantDistanceRepository;
import com.grapheople.opentable.repositories.RestaurantRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Created by pax on 2018. 2. 8..
 */
@Service
@Slf4j
public class NaverMapServices {
    private final NaverApiProperties naverApiProperties;
    private final Retrofit retrofit;
    private final NaverMapRetrofitRepository naverMapRetrofitRepository;
    private final RestaurantRepository restaurantRepository;
    private final RestaurantDistanceRepository restaurantDistanceRepository;
    private final Map<String, String> headers = Maps.newHashMap();
    private List<Restaurant> restaurantList;
    private Set<String> categories;

    public NaverMapServices(NaverApiProperties naverApiProperties, RestaurantRepository restaurantRepository, RestaurantDistanceRepository restaurantDistanceRepository) {
        this.restaurantRepository = restaurantRepository;
        this.restaurantDistanceRepository = restaurantDistanceRepository;
        this.naverApiProperties = naverApiProperties;
        retrofit = new Retrofit.Builder()
                .baseUrl("https://openapi.naver.com")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
        naverMapRetrofitRepository = retrofit.create(NaverMapRetrofitRepository.class);
        headers.put("X-Naver-Client-Id", naverApiProperties.getId());
        headers.put("X-Naver-Client-Secret", naverApiProperties.getSecret());
    }

    public void insertRestaurant(String query, Integer page, Integer pageSize, String sort, String startingPointStr) {
        try {
            Call<JsonNode> request = naverMapRetrofitRepository.getLocalInfo(query, (page - 1) * pageSize + 1, pageSize, sort, headers);
            Response<JsonNode> result = request.execute();
            if (result.isSuccessful() && result.body().get("items").size() > 0) {
                StreamSupport.stream(result.body().get("items").spliterator(), false)
                        .forEach(jsonNode -> {
                            if (getDistance(jsonNode.get("mapx").asInt(), jsonNode.get("mapy").asInt(), StartingPoint.convert(startingPointStr)) < DistanceType.FAR.getDistance()) {
                                Restaurant savedRestaurant = saveRestaurant(jsonNode);
                                if (savedRestaurant != null) {
                                    saveRestaurantDistance(startingPointStr, savedRestaurant.getId(), savedRestaurant.getMapx(), savedRestaurant.getMapy());
                                }
                            }
                        });
            } else {
                log.error("naver api failed. response code:" + result.code() + " message:" + result.message());
            }
        } catch (Exception e) {
            log.error(e.toString());
        }
    }

    private Restaurant saveRestaurant(JsonNode jsonNode) {
        try {
            Restaurant savedRestaurant = restaurantRepository.save(new Restaurant(jsonNode));
            log.info("new restaurant is inserted. title:" + savedRestaurant.getTitle());
            return savedRestaurant;
        } catch (Exception e) {
            log.error("save restaurant failed. title:" + jsonNode.get("title") + " reason:" + e.getLocalizedMessage());
            return null;
        }
    }

    private void saveRestaurantDistance(String startingPointStr, Long restaurantId, Integer mapx, Integer mapy) {
        if (!Strings.isNullOrEmpty(startingPointStr) && StartingPoint.isExist(startingPointStr)) {
            StartingPoint startingPoint = StartingPoint.convert(startingPointStr);
            Double distance = Math.sqrt(Math.pow(startingPoint.getMapx()-mapx, 2) + Math.pow(startingPoint.getMapy()-mapy, 2));
            try {
                restaurantDistanceRepository.save(new RestaurantDistance(restaurantId, startingPoint.getName(), distance.floatValue()));
                log.info("new restaurant distance is inserted. restaurantId:" + restaurantId + " startingPoint:" + startingPoint.getName());
            } catch (Exception e) {
                log.error("save restaurant distance failed. restaurant id:" + restaurantId + " reason:" + e.getLocalizedMessage());
            }
        }
    }

    public JsonNode getRestaurantLustFromNaver(String query, Integer page, Integer pageSize, String sort) {
        try {
            Call<JsonNode> request = naverMapRetrofitRepository.getLocalInfo(query, (page - 1) * pageSize + 1, pageSize, sort, headers);
            Response<JsonNode> result = request.execute();
            return result.body();
        } catch (Exception e) {
            log.error(e.toString());
        }
        return null;
    }

    public List<Restaurant> getRestaurantListFromDB() {
        if (restaurantList == null) {
            restaurantList = restaurantRepository.findAll();
        }
        return restaurantList;
    }

    private Integer getDistance(Integer x, Integer y, StartingPoint startingPoint) {
        Double distance = Math.sqrt(Math.pow(startingPoint.getMapx()-x, 2) + Math.pow(startingPoint.getMapy()-y, 2));
        return distance.intValue();
    }

    public List<Restaurant> getRestaurantList(String startingPointStr ,String distanceStr, String category, String title, Integer page, Integer pageSize) {
        List<Restaurant> result = getRestaurantListFromDB();
        if (!Strings.isNullOrEmpty(distanceStr) && DistanceType.isExist(distanceStr) && !DistanceType.ALL.getType().equals(distanceStr) && !Strings.isNullOrEmpty(startingPointStr) && StartingPoint.isExist(startingPointStr)) {
            StartingPoint startingPoint = StartingPoint.convert(startingPointStr);
            DistanceType distanceType = DistanceType.convert(distanceStr);
            result = result.stream()
                    .filter(restaurant -> getDistance(restaurant.getMapx(), restaurant.getMapy(), startingPoint) < distanceType.getDistance())
                    .collect(Collectors.toList());
        }

        if (!Strings.isNullOrEmpty(category)) {
            result= result.stream().filter(restaurant -> restaurant.getCategory().contains(category)).collect(Collectors.toList());
        }
        if (!Strings.isNullOrEmpty(title)) {
            result= result.stream().filter(restaurant -> restaurant.getTitle().contains(title)).collect(Collectors.toList());
        }
        return result.stream().skip(page*pageSize).limit(pageSize).collect(Collectors.toList());
    }

    public Set<String> getCategories() {
        if (categories == null) {
            categories = Sets.newHashSet();
            getRestaurantListFromDB().stream().forEach(restaurant -> {
                Arrays.stream(restaurant.getCategory().split(",")).forEach(category->{
                    categories.add(category.trim());
                });
            });
            return categories;
        }
        return categories;
    }
}
