package co.mercadolibre.SocialMeli.util;

import co.mercadolibre.SocialMeli.dto.response.ClientFollowedDTO;
import co.mercadolibre.SocialMeli.dto.response.SellerFollowersDTO;
import co.mercadolibre.SocialMeli.dto.response.UserDTO;
import co.mercadolibre.SocialMeli.entity.User;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public class Data {
    static ObjectMapper objectMapper = new ObjectMapper();

    public static List<User> getUsersListTest() {

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

}
