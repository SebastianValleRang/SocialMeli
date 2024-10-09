package co.mercadolibre.SocialMeli.util;


import co.mercadolibre.SocialMeli.dto.request.PostRequestDTO;
import co.mercadolibre.SocialMeli.dto.response.UserDTO;
import co.mercadolibre.SocialMeli.entity.Post;
import co.mercadolibre.SocialMeli.entity.Product;
import co.mercadolibre.SocialMeli.entity.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Data {

    public static User createUser(int id, String userName){
        return new User(id, userName, new ArrayList<>(), new ArrayList<>(),new ArrayList<>());
    }
    public static User createSeller(int id, String userName){
        List<Post> posts = new ArrayList<>();
        Product product = new Product(2,"Licuadora","Cocina","Imusa","Negro","Gomela");
        posts.add(new Post(1,id, LocalDate.now(), product, 100, 150.000));
        return new User(id, userName, new ArrayList<>(), new ArrayList<>(), posts);
    }
}
