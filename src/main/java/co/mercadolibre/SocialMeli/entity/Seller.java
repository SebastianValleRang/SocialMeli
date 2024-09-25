package co.mercadolibre.SocialMeli.entity;

import java.util.List;

public class Seller extends User{
    private List<User> followers;
    private List<Post> posts;
}
