package co.mercadolibre.SocialMeli.dto.request;

import co.mercadolibre.SocialMeli.entity.Product;
import co.mercadolibre.SocialMeli.dto.response.ProductDTO;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public class PromoPostRequestDTO {
    @JsonProperty("user_id")
    private int userId;
    private LocalDate date;
    private ProductDTO product;
}
