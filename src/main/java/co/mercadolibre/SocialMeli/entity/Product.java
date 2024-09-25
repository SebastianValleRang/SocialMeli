package co.mercadolibre.SocialMeli.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Product {
    @JsonProperty("product_id")
    private int productId;
    @JsonProperty("product_name")
    private String productName;
    private String type;
    private String brand;
    private String color;
    private String notes;

}
