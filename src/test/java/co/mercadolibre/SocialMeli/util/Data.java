package co.mercadolibre.SocialMeli.util;

import co.mercadolibre.SocialMeli.entity.Post;
import co.mercadolibre.SocialMeli.entity.Product;
import co.mercadolibre.SocialMeli.entity.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Data {

    public static User getUserThatFollows1Seller(){
        User client = new User(1, "Cliente");

        Product product1 = new Product(1,"Mesedora","Muebles","Sillas jairo","Blanco", "Realizada con madera de roble");
        Product product2 = new Product(2,"Licuadora","Cocina","Imusa","Negro","Gomela");
        Product product3 = new Product(3,"Nevera","Cocina","Samsung","Blanco","Con congelador");

        User seller = new User(2, "Vendedor1");
        Post post1 = new Post(1, 2, LocalDate.parse("2024-10-09"), product1,1,223.3);
        Post post2 = new Post(2, 2, LocalDate.parse("2024-09-23"), product2, 2, 150.5);
        Post post3 = new Post(3, 2, LocalDate.parse("2023-01-01"), product3, 2, 525.0);

        seller.getPosts().add(post1);
        seller.getPosts().add(post2);
        seller.getPosts().add(post3);

        seller.getFollowers().add(client);
        client.getFollowed().add(seller);

        return client;
    }

    public static User createUser(int id, String userName){
        return new User(id, userName, new ArrayList<>(), new ArrayList<>(),new ArrayList<>());
    }
    public static User createSeller(int id, String userName){
        List<Post> posts = new ArrayList<>();
        Product product = new Product(2,"Licuadora","Cocina","Imusa","Negro","Gomela");
        posts.add(new Post(1,id, LocalDate.now(), product, 100, 150.000));
        return new User(id, userName, new ArrayList<>(), new ArrayList<>(), posts);
    }
    public static User createFollowedSeller(int id, String userName, User follower){
        List<User> followers = new ArrayList<>();
        followers.add(follower);
        List<Post> posts = new ArrayList<>();
        Product product = new Product(2,"Licuadora","Cocina","Imusa","Negro","Gomela");
        posts.add(new Post(1,id, LocalDate.now(), product, 100, 150.000));
        return new User(id, userName, followers, new ArrayList<>(), posts);
    }
}
