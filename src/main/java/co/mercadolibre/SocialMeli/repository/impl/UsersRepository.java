package co.mercadolibre.SocialMeli.repository.impl;

import co.mercadolibre.SocialMeli.entity.User;
import co.mercadolibre.SocialMeli.repository.IUsersRepository;

import java.util.ArrayList;
import java.util.List;

public class UsersRepository implements IUsersRepository {

    private List<User> usersList = new ArrayList<>();

    @Override
    public List<User> finAllUsers() {
        return usersList;
    }
}
