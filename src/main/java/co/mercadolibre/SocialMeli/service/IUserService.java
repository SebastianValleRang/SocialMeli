package co.mercadolibre.SocialMeli.service;

import co.mercadolibre.SocialMeli.dto.response.ClientFollowedDTO;
import co.mercadolibre.SocialMeli.dto.response.ResponseDTO;
import co.mercadolibre.SocialMeli.dto.response.SellerFollowersDTO;
import co.mercadolibre.SocialMeli.dto.response.UserDTO;
import co.mercadolibre.SocialMeli.entity.User;

import java.util.List;

public interface IUserService {

    List<UserDTO> getAllUsers();

    SellerFollowersDTO getFollowersByID(int userId);

    ResponseDTO followSeller(int userId, int userIdToFollow);

    ClientFollowedDTO listFollowedSellers(int userId);

    ResponseDTO unfollow(int userId, int userIdToUnfollow);

    ClientFollowedDTO listFollowedSellersOrder(int userId, String Order); //Preguntar si es la misma ClientDTO
}
