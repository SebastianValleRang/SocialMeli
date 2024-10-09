package co.mercadolibre.SocialMeli.util;


import co.mercadolibre.SocialMeli.dto.request.PostRequestDTO;
import co.mercadolibre.SocialMeli.entity.Post;
import co.mercadolibre.SocialMeli.entity.Product;
import co.mercadolibre.SocialMeli.entity.User;

import java.time.LocalDate;

public class Data {
//public static final Objeto NOMBRE = new Objeto();

    public static User getUserThatFollows1Seller(){
        User client = new User(1, "Cliente");

        Product product1 = new Product(1,"Mesedora","Muebles","Sillas jairo","Blanco", "Realizada con madera de roble");
        Product product2 = new Product(2,"Licuadora","Cocina","Imusa","Negro","Gomela");
        Product product3 = new Product(3,"Nevera","Cocina","Samsung","Blanco","Con congelador");

        User seller = new User(2, "Vendedor1");
        Post post1 = new Post(1, 2, LocalDate.parse("2024-10-03"), product1,1,223.3);
        Post post2 = new Post(2, 2, LocalDate.parse("2024-09-23"), product2, 2, 150.5);
        Post post3 = new Post(3, 2, LocalDate.parse("2023-01-01"), product3, 2, 525.0);

        seller.getPosts().add(post1);
        seller.getPosts().add(post2);
        seller.getPosts().add(post3);

        seller.getFollowers().add(client);
        client.getFollowed().add(seller);

        return client;
    }

}
