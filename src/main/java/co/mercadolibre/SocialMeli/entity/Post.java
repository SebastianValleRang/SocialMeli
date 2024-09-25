package co.mercadolibre.SocialMeli.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public class Post {
    @JsonProperty("post_id")
    private  int postId;
    @JsonProperty("user_id")
    private int userId;
    private LocalDate date;
    private  Product product;
    private int category;
    private double price;
    @JsonProperty("has_promo")
    private boolean hasPromo;
    private double discount;

}
