package co.mercadolibre.SocialMeli.service.impl;

import co.mercadolibre.SocialMeli.dto.response.CountFollowersDTO;
import co.mercadolibre.SocialMeli.dto.response.SellerFollowersDTO;
import co.mercadolibre.SocialMeli.dto.response.UserDTO;
import co.mercadolibre.SocialMeli.entity.User;
import co.mercadolibre.SocialMeli.exception.BadRequestException;
import co.mercadolibre.SocialMeli.exception.NotFoundException;
import co.mercadolibre.SocialMeli.repository.impl.UsersRepository;
import co.mercadolibre.SocialMeli.service.ISellerService;
import co.mercadolibre.SocialMeli.utils.GlobalMethods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
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
            throw new NotFoundException("El usuario con el id %d no se ha encontrado".formatted(userId));
        }
        if (globalMethods.isNotSeller(sellerToCheck)){
            throw new NotFoundException("El usuario con el id %d no es un vendedor".formatted(userId));
        }
        return new CountFollowersDTO(sellerToCheck.getUserId(), sellerToCheck.getUserName(), sellerToCheck.getFollowers().size());
    }

    @Override
    public SellerFollowersDTO listFollowers(int userId, String order) {
        User sellerToCheck = globalMethods.getUserById(userId);
        if (sellerToCheck == null){
            throw new NotFoundException("El usuario con el id %d no se ha encontrado".formatted(userId));
        }
        if (globalMethods.isNotSeller(sellerToCheck)){
            throw new NotFoundException("El usuario con el id %d no es un vendedor".formatted(userId));
        }

        List<User> listOfFollowers;
        if (order == null){
            listOfFollowers = sellerToCheck.getFollowers();
        } else if (order.equals("name_asc")){
            listOfFollowers = sellerToCheck.getFollowers().stream()
                    .sorted(Comparator.comparing(User::getUserName)).toList();
        } else if (order.equals("name_desc")){
            listOfFollowers = sellerToCheck.getFollowers().stream()
                    .sorted(Comparator.comparing(User::getUserName)).toList().reversed();
        } else {
            throw new BadRequestException("Ordenamiento inválido");
        }
        List<UserDTO> listOfFollowersDTO = listOfFollowers.stream()
                .map(follower -> new UserDTO(follower.getUserId(), follower.getUserName())).toList();
        return new SellerFollowersDTO(sellerToCheck.getUserId(), sellerToCheck.getUserName(), listOfFollowersDTO);
    }

}
