package co.mercadolibre.SocialMeli.repository;

import co.mercadolibre.SocialMeli.entity.User;

import java.util.List;

public interface IUsersRepository {
    List<User> findAllUsers();
}
