package co.mercadolibre.SocialMeli.service.impl;

import co.mercadolibre.SocialMeli.dto.response.ClientFollowedDTO;
import co.mercadolibre.SocialMeli.dto.response.ResponseDTO;
import co.mercadolibre.SocialMeli.entity.User;
import co.mercadolibre.SocialMeli.exception.NotFoundException;
import co.mercadolibre.SocialMeli.service.IUserService;
import co.mercadolibre.SocialMeli.utils.GlobalMethods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IUserService {

    @Autowired
    GlobalMethods globalMethods;



    @Override
    public ResponseDTO followSeller(int userId, int userIdToFollow) {
        User user = globalMethods.getUserById(userId);
        User seller = globalMethods.getUserById(userIdToFollow);
        if (user == null){
            throw new NotFoundException("No existe un usuario con el id %d.".formatted(userId));
        }
        if (seller == null){
            throw new NotFoundException("No existe un vendedor con el id %d.".formatted(userIdToFollow));
        }
        if(globalMethods.isNotSeller(seller)){
            throw new NotFoundException("El usuario %d no es un vendedor.".formatted(userIdToFollow));
        }
        //Añadir usuario a la lista de seguidores
        List<User> followers = seller.getFollowers();
        followers.add(user);
        seller.setFollowers(followers);
        //Añadir vendedor a la lista de seguidos
        List<User> followed = user.getFollowed();
        followed.add(seller);
        user.setFollowed(followed);

        return new ResponseDTO("El usuario %d ha seguido de manera exitosa al vendedor %d.".formatted(userId,userIdToFollow), HttpStatus.OK);

    }

    @Override
    public ClientFollowedDTO listFollowedSellers(int userId) {
        return null;
    }

    @Override
    public ResponseDTO unfollow(int userId, int userIdToFollow) { return null;}

    @Override
    public ClientFollowedDTO listFollowedSellersOrder(int userId, String Order) {
        return null;
    }
}
