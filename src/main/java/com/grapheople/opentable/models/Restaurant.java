package com.grapheople.opentable.models;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

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

    public Restaurant() {

    }

    public Restaurant(JsonNode jsonNode) {
        setTitle(jsonNode.get("title").asText());
        setLink(jsonNode.get("link").asText());
        setCategory(jsonNode.get("category").asText());
        setDescription(jsonNode.get("description").asText());
        setTelephone(jsonNode.get("telephone").asText());
        setRoadAddress(jsonNode.get("roadAddress").asText());
        setAddress(jsonNode.get("address").asText());
        setMapx(jsonNode.get("mapx").asInt());
        setMapy(jsonNode.get("mapy").asInt());
    }

}
