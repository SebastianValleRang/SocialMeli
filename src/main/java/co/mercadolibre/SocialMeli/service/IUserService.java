package co.mercadolibre.SocialMeli.service;

import co.mercadolibre.SocialMeli.dto.response.ClientFollowedDTO;
import co.mercadolibre.SocialMeli.dto.response.ResponseDTO;

public interface IUserService {

    ResponseDTO followSeller(int userId, int userIdToFollow);

    ClientFollowedDTO listFollowedSellers(int userId);

    ResponseDTO unfollow(int userId, int userIdToFollow);

    ClientFollowedDTO listFollowedSellersOrder(int userId, String Order); //Preguntar si es la misma ClientDTO
}
