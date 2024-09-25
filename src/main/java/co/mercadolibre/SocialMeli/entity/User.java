package co.mercadolibre.SocialMeli.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data


public class User {
    @JsonProperty("user_id")
    private int id;
    @JsonProperty("user_name")
    private String userName;
}
