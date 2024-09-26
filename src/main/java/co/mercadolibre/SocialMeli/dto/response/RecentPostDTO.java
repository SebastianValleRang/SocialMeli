package co.mercadolibre.SocialMeli.dto.response;

import co.mercadolibre.SocialMeli.entity.Post;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class RecentPostDTO {
    @JsonProperty("user_id")
    private int userId;
    @JsonProperty("recent_posts")
    private List<Post> recentPosts;


}
