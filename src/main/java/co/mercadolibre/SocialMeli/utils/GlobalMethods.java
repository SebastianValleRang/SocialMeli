package co.mercadolibre.SocialMeli.utils;

import co.mercadolibre.SocialMeli.entity.User;
import co.mercadolibre.SocialMeli.exception.NotFoundException;
import co.mercadolibre.SocialMeli.repository.IUsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GlobalMethods {
    @Autowired
    IUsersRepository iUsersRepository;

    public User getUserById(int userId){
        return iUsersRepository.finAllUsers().stream().filter(entry -> entry.getUserId() == userId).findFirst().orElse(null);
    }

    public boolean isNotSeller(User seller){
        return seller.getPosts().isEmpty();
    }


}
