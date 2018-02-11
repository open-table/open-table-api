package com.grapheople.opentable.models;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

/**
 * Created by pax on 2018. 2. 7..
 */
@Data
@Entity
@Table(name = "restaurants")
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "link")
    private String link;

    @Column(name = "category")
    private String category;

    @Column(name = "description")
    private String description;

    @Column(name = "telephone")
    private String telephone;

    @Column(name = "address")
    private String address;

    @Column(name = "roadAddress")
    private String roadAddress;

    @Column(name = "mapx")
    private Integer mapx;

    @Column(name = "mapy")
    private Integer mapy;

//    @OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
//    @JoinColumn(name = "restaurant_id")
//    private List<RestaurantDistance> restaurantDistance;

    public Restaurant() {

    }

    public Restaurant(JsonNode jsonNode) {
        setTitle(jsonNode.get("title").asText().replaceAll("\\<.*?>",""));
        setLink(jsonNode.get("link").asText());
        setCategory(jsonNode.get("category").asText().replaceAll("음식점>", "").replaceAll(">", ","));
        setDescription(jsonNode.get("description").asText().replaceAll("\\<.*?>",""));
        setTelephone(jsonNode.get("telephone").asText());
        setRoadAddress(jsonNode.get("roadAddress").asText());
        setAddress(jsonNode.get("address").asText());
        setMapx(jsonNode.get("mapx").asInt());
        setMapy(jsonNode.get("mapy").asInt());
    }

}
