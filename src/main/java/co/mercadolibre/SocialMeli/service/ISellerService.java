package co.mercadolibre.SocialMeli.service;

import co.mercadolibre.SocialMeli.dto.response.CountFollowersDTO;
import co.mercadolibre.SocialMeli.dto.response.SellerFollowersDTO;

public interface ISellerService {
    CountFollowersDTO countFollowers(int userId);
    SellerFollowersDTO listFollowers(int userId, String order);
}
