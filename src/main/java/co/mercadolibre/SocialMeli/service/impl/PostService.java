package co.mercadolibre.SocialMeli.service.impl;

import co.mercadolibre.SocialMeli.dto.request.PostRequestDTO;
import co.mercadolibre.SocialMeli.dto.response.ResponseDTO;
import co.mercadolibre.SocialMeli.repository.impl.UsersRepository;
import co.mercadolibre.SocialMeli.service.IPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostService implements IPostService {
@Autowired
    UsersRepository usersRepository;

    @Override
    public ResponseDTO createPost(PostRequestDTO post) {

        return null;
    }

    private 
}
