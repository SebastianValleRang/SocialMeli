package co.mercadolibre.SocialMeli.service.impl;

import co.mercadolibre.SocialMeli.dto.response.CountFollowersDTO;
import co.mercadolibre.SocialMeli.dto.response.SellerFollowersDTO;
import co.mercadolibre.SocialMeli.dto.response.UserDTO;
import co.mercadolibre.SocialMeli.entity.User;
import co.mercadolibre.SocialMeli.exception.NotFoundException;
import co.mercadolibre.SocialMeli.repository.impl.UsersRepository;
import co.mercadolibre.SocialMeli.service.ISellerService;
import co.mercadolibre.SocialMeli.utils.GlobalMethods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SellerService implements ISellerService {
    @Autowired
    UsersRepository usersRepository;
    @Autowired
    GlobalMethods globalMethods;

    @Override
    public CountFollowersDTO countFollowers(int userId) {
        User sellerToCheck = globalMethods.getUserById(userId);
        if (sellerToCheck == null){
            throw new NotFoundException("User not found");
        }
        if (globalMethods.isNotSeller(sellerToCheck)){
            throw new NotFoundException("The user is not seller");
        }
        return new CountFollowersDTO(sellerToCheck.getUserId(), sellerToCheck.getUserName(), sellerToCheck.getFollowers().size());
    }

    @Override
    public SellerFollowersDTO listFollowers(int userId) {
        User sellerToCheck = globalMethods.getUserById(userId);
        if (sellerToCheck == null){
            throw new NotFoundException("User not found");
        }
        if (globalMethods.isNotSeller(sellerToCheck)){
            throw new NotFoundException("The user is not seller");
        }
        List<UserDTO> listOfFollowers = sellerToCheck.getFollowers().stream()
                .map(follower -> new UserDTO(follower.getUserId(), follower.getUserName())).toList();
        return new SellerFollowersDTO(sellerToCheck.getUserId(), sellerToCheck.getUserName(), listOfFollowers);
    }
}
