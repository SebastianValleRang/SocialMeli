package co.mercadolibre.SocialMeli.repository.impl;

import co.mercadolibre.SocialMeli.entity.User;
import co.mercadolibre.SocialMeli.repository.IUsersRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
@Repository
public class UsersRepository implements IUsersRepository {

    private List<User> usersList = new ArrayList<>();

    @Override
    public List<User> finAllUsers() {
        return usersList;
    }
}
