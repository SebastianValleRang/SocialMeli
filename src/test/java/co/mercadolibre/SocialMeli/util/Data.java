package co.mercadolibre.SocialMeli.util;

import co.mercadolibre.SocialMeli.dto.response.ClientFollowedDTO;
import co.mercadolibre.SocialMeli.dto.response.SellerFollowersDTO;

import co.mercadolibre.SocialMeli.dto.response.UserDTO;
import co.mercadolibre.SocialMeli.entity.User;
import co.mercadolibre.SocialMeli.entity.Post;
import co.mercadolibre.SocialMeli.entity.Product;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Data {
    static ObjectMapper objectMapper = new ObjectMapper();

    public static List<User> getUsersListTestT0003() {

        List<User> usersList;

        User userJuanPerez = new User(1, "JuanPerez");
        User userLeandroDiaz = new User(2, "LeandroDiaz");
        User userAngelaGonzales = new User(3, "AngelaGonzales");
        User userAnita99 = new User(4, "Anita99");
        User userFedericoV = new User(5, "FedericoV");

        usersList = List.of(userJuanPerez, userLeandroDiaz, userAngelaGonzales,
                userAnita99, userFedericoV);

        userJuanPerez.getFollowers().add(userLeandroDiaz);
        userJuanPerez.getFollowers().add(userAngelaGonzales);

        userJuanPerez.getFollowed().add(userFedericoV);
        userJuanPerez.getFollowed().add(userAnita99);

        return usersList;

    }

    public static ClientFollowedDTO getlistFollowedSellersTest() {

        User userJuanPerez = new User(1, "JuanPerez");
        User userAnita99 = new User(4, "Anita99");
        User userFedericoV = new User(5, "FedericoV");

        userJuanPerez.getFollowed().add(userFedericoV);
        userJuanPerez.getFollowed().add(userAnita99);

        ClientFollowedDTO clientFollowedDTO = new ClientFollowedDTO(
                userJuanPerez.getUserId(),
                userJuanPerez.getUserName(),
                List.of(objectMapper.convertValue(userFedericoV, UserDTO.class)
                        ,objectMapper.convertValue(userAnita99,UserDTO.class))
        );

        return clientFollowedDTO;

    }

    public static SellerFollowersDTO getlistFollowersSellersTest() {

        User userJuanPerez = new User(1, "JuanPerez");
        User userLeandroDiaz = new User(2, "LeandroDiaz");
        User userAngelaGonzales = new User(3, "AngelaGonzales");

        userJuanPerez.getFollowers().add(userLeandroDiaz);
        userJuanPerez.getFollowers().add(userAngelaGonzales);

        SellerFollowersDTO sellerFollowersDTO = new SellerFollowersDTO(
                userJuanPerez.getUserId(),
                userJuanPerez.getUserName(),
                List.of(objectMapper.convertValue(userLeandroDiaz,UserDTO.class)
                        ,objectMapper.convertValue(userAngelaGonzales,UserDTO.class))
        );

        return sellerFollowersDTO;

    }

    public static List<User> getUsersListTest(){
        //Usuarios
        User userLeandroRamirez = new User(1,"LeandroRamirez");
        User userVaneLozano = new User(2,"VaneLozano");
        User userAngelaDaza = new User(3,"AngelaDaza");
        User userAnaGarcia = new User(4,"AnaGarcia");
        User userSebasVallejo = new User(5,"SebasVallejo");

        //productos
        Product product1 = new Product(1,"Mesedora","Muebles","Sillas jairo","Blanco", "Realizada con madera de roble");
        Product product2 = new Product(2,"Licuadora","Cocina","Imusa","Negro","Gomela");
        Product product3 = new Product(3,"Nevera","Cocina","Samsung","Blanco","Con congelador");

        //posts
        Post post1 = new Post(1, 2, LocalDate.parse("2024-10-03"), product1,1,223.3);
        Post post2 = new Post(2, 2, LocalDate.parse("2024-09-23"), product2, 2, 150.5);
        Post post3 = new Post(3, 2, LocalDate.parse("2023-01-01"), product3, 2, 525.0);

        //Vendedores
        userLeandroRamirez.getPosts().add(post1);
        userVaneLozano.getPosts().add(post2);
        userAngelaDaza.getPosts().add(post3);

        //seguidores del vendedor 1 -- followers
        userLeandroRamirez.getFollowers().add(userSebasVallejo);
        userSebasVallejo.getFollowed().add(userLeandroRamirez);
        userLeandroRamirez.getFollowers().add(userAnaGarcia);
        userLeandroRamirez.getFollowers().add(userVaneLozano);
        userVaneLozano.getFollowed().add(userLeandroRamirez);

        //usuario 4 seguir  vendedores -- followed
        userAnaGarcia.getFollowed().add(userLeandroRamirez);
        userAnaGarcia.getFollowed().add(userAngelaDaza);
        userAnaGarcia.getFollowed().add(userVaneLozano);

        return List.of(userLeandroRamirez,userVaneLozano,userAngelaDaza,userAnaGarcia,userSebasVallejo);
    }

    public static SellerFollowersDTO getlistFollowersTest(){
        List<User> users = getUsersListTest();
        List<UserDTO> sellerFollowers = users.getFirst().getFollowers().stream()
                .map(v -> new UserDTO(v.getUserId(), v.getUserName()))
                .toList();
        return new SellerFollowersDTO(users.getFirst().getUserId(), users.getFirst().getUserName(), sellerFollowers);
    }

    public static SellerFollowersDTO getlistFollowersAscTest(){
        List<UserDTO> sellerFollowers = List.of(new UserDTO(4,"AnaGarcia"),new UserDTO(5, "SebasVallejo"),new UserDTO(2,"VaneLozano"));
        return new SellerFollowersDTO(1, "LeandroRamirez", sellerFollowers);
    }

    public static SellerFollowersDTO getlistFollowersDescTest(){
        List<User> users = getUsersListTest();
        List<UserDTO> sellerFollowers = users.getFirst().getFollowers().stream()
                .map(v -> new UserDTO(v.getUserId(), v.getUserName()))
                .toList();
        return new SellerFollowersDTO(users.getFirst().getUserId(), users.getFirst().getUserName(), sellerFollowers.stream().sorted(Comparator.comparing(UserDTO::getUserName).reversed()).toList());
    }
    public static ClientFollowedDTO getlistFollowedAscTest(){
        List<User> users = getUsersListTest();
        List<UserDTO> userFollowed = users.get(3).getFollowed().stream()
                .map(v -> new UserDTO(v.getUserId(), v.getUserName()))
                .toList();
        return new ClientFollowedDTO(users.get(3).getUserId(), users.get(3).getUserName(), userFollowed.stream().sorted(Comparator.comparing(UserDTO::getUserName)).toList());
    }

    public static ClientFollowedDTO getlistFollowedDescTest(){
        List<User> users = getUsersListTest();
        List<UserDTO> userFollowed = users.get(3).getFollowed().stream()
                .map(v -> new UserDTO(v.getUserId(), v.getUserName()))
                .toList();
        return new ClientFollowedDTO(users.get(3).getUserId(), users.get(3).getUserName(), userFollowed.stream().sorted(Comparator.comparing(UserDTO::getUserName).reversed()).toList());
    }

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
    public static User createSellerWithDate(int id, String userName, LocalDate date){
        List<Post> posts = new ArrayList<>();
        Product product = new Product(2,"Licuadora","Cocina","Imusa","Negro","Gomela");
        posts.add(new Post(1,id, date, product, 100, 150.000));
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
