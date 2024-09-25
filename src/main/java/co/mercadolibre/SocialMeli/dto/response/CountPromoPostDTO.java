package co.mercadolibre.SocialMeli.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CountPromoPostDTO {
    @JsonProperty("user_id")
    private int userId;
    @JsonProperty("user_name")
    private String userName;
    @JsonProperty("promo_products_count")
    private int promoProductsCount;
}
