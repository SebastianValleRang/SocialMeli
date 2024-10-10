package co.mercadolibre.SocialMeli.util;

import co.mercadolibre.SocialMeli.dto.response.ClientFollowedDTO;
import co.mercadolibre.SocialMeli.dto.response.CountFollowersDTO;
import co.mercadolibre.SocialMeli.dto.response.SellerFollowersDTO;
import co.mercadolibre.SocialMeli.dto.response.UserDTO;
import co.mercadolibre.SocialMeli.entity.User;

import java.util.ArrayList;
import java.util.List;

public class IntegrationData {

    public static CountFollowersDTO getCountSellerDTO() {
        return new CountFollowersDTO(1, "JuanPerez", 2);
    }

    public static ClientFollowedDTO getClientFollowedDTO() {

        List<User> usersList;

        User userJuanPerez = new User(1, "JuanPerez");

        return new ClientFollowedDTO(
                userJuanPerez.getUserId(),
                userJuanPerez.getUserName(),
                new ArrayList<UserDTO>()
        );

    }

    public static SellerFollowersDTO getListFollowersDTO() {

        List<User> usersList;

        User userJuanPerez = new User(1, "JuanPerez");

        return new SellerFollowersDTO(
                userJuanPerez.getUserId(),
                userJuanPerez.getUserName(),
                List.of(new UserDTO(2, "LeandroDiaz"), new UserDTO(100, "Joy"))
        );

    }

}
