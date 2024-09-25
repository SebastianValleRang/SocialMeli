package co.mercadolibre.SocialMeli.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data

public class User {
    @JsonProperty("user_id")
    private int userId;
    @JsonProperty("user_name")
    private String userName;
    private List<User> followers; //lista de seguidores
    private List<User> followed; // listas de seguidos
    private List<Post> posts;
}
