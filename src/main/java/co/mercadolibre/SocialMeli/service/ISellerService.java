package co.mercadolibre.SocialMeli.service;

import co.mercadolibre.SocialMeli.dto.response.CountFollowersDTO;

public interface ISellerService {
    CountFollowersDTO countFollowers(int userId);
}
