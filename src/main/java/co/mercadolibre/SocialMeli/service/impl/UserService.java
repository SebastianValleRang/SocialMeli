package co.mercadolibre.SocialMeli.service.impl;

import co.mercadolibre.SocialMeli.dto.response.ClientFollowedDTO;
import co.mercadolibre.SocialMeli.dto.response.ResponseDTO;
import co.mercadolibre.SocialMeli.repository.IUsersRepository;
import co.mercadolibre.SocialMeli.repository.impl.UsersRepository;
import co.mercadolibre.SocialMeli.service.IUserService;

public class UserService implements IUserService {

    IUsersRepository iUsersRepository;

    public UserService(UsersRepository usersRepository){
        this.iUsersRepository = usersRepository;
    }


    @Override
    public ResponseDTO followSeller(int userId, int userIdToFollow) {
        return null;
    }

    @Override
    public ClientFollowedDTO listFollowedSellers(int userId) {
        return null;
    }

    @Override
    public ResponseDTO unfollow(int userId, int userIdToFollow) {
        return null;
    }

    @Override
    public ClientFollowedDTO listFollowedSellersOrder(int userId, String Order) {
        return null;
    }
}
