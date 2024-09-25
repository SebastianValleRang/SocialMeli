package co.mercadolibre.SocialMeli.dto.response;

import co.mercadolibre.SocialMeli.entity.Product;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor

public class PostDTO {
    @JsonProperty("post_id")
    private  int postId;
    @JsonProperty("user_id")
    private int userId;
    private LocalDate date;
    private Product product;
    private int category;
    private double price;
}
