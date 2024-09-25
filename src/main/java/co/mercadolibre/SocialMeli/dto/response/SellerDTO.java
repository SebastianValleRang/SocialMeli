package co.mercadolibre.SocialMeli.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class SellerDTO {
    @JsonProperty("user_id")
    private int userId;
    @JsonProperty("user_name")
    private String userName;
    private List<UserDTO> followers;
}
