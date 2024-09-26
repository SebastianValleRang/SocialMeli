package co.mercadolibre.SocialMeli.service.impl;

import co.mercadolibre.SocialMeli.dto.response.CountFollowersDTO;
import co.mercadolibre.SocialMeli.entity.User;
import co.mercadolibre.SocialMeli.exception.NotFoundException;
import co.mercadolibre.SocialMeli.repository.impl.UsersRepository;
import co.mercadolibre.SocialMeli.service.ISellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SellerService implements ISellerService {
    @Autowired
    UsersRepository usersRepository;

    @Override
    public CountFollowersDTO countFollowers(int userId) {
        User sellerToCheck = usersRepository.finAllUsers().stream()
                .filter(user -> user.getUserId() == userId)
                .findFirst()
                .orElse(null);
        if (sellerToCheck == null){
            throw new NotFoundException("User not found");
        }
        return new CountFollowersDTO(sellerToCheck.getUserId(), sellerToCheck.getUserName(), sellerToCheck.getFollowers().size());
    }


}
