package co.mercadolibre.SocialMeli.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ClientDTO {
    @JsonProperty("user_id")
    private int userId;
    @JsonProperty("user_name")
    private String userName;
    @JsonProperty("followed_sellers")
    private List<UserDTO> followedSellers;
}
