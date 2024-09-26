package co.mercadolibre.SocialMeli.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ProductDTO {
    @JsonProperty("product_id")
    private int productId;
    @JsonProperty("product_name")
    private String productName;
    private String type;
    private String brand;
    private String color;
    private String notes;
}
