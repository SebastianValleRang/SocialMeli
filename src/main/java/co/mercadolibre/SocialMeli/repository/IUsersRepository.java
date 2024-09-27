package co.mercadolibre.SocialMeli.repository;

import co.mercadolibre.SocialMeli.dto.request.PostRequestDTO;
import co.mercadolibre.SocialMeli.entity.Product;
import co.mercadolibre.SocialMeli.entity.User;

import java.util.List;

public interface IUsersRepository {
    List<User> findAllUsers();
    void createPost(PostRequestDTO post, Product product);
}
