package co.mercadolibre.SocialMeli.service.impl;

import co.mercadolibre.SocialMeli.dto.request.PostRequestDTO;
import co.mercadolibre.SocialMeli.dto.response.ResponseDTO;
import co.mercadolibre.SocialMeli.entity.Product;
import co.mercadolibre.SocialMeli.entity.User;
import co.mercadolibre.SocialMeli.exception.BadRequestException;
import co.mercadolibre.SocialMeli.exception.NotFoundException;
import co.mercadolibre.SocialMeli.repository.impl.UsersRepository;
import co.mercadolibre.SocialMeli.service.IPostService;
import co.mercadolibre.SocialMeli.utils.GlobalMethods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class PostService implements IPostService {
    @Autowired
    UsersRepository usersRepository;
    @Autowired
    GlobalMethods globalMethods;

    @Override
    public ResponseDTO createPost(PostRequestDTO post) {
        User user = globalMethods.getUserById(post.getUserId());
        Product product = globalMethods.getProductById(post.getProductDTO().getProductId());
        if (user == null) {
            throw new NotFoundException("Usuario no encontrado.");
        }
        if (product == null){
            throw new NotFoundException("Producto no encontrado.");
        }
        try{
            usersRepository.createPost(post, product);
            return new ResponseDTO("Post creado correctamente.", HttpStatus.OK);
        }catch (Exception e){
            throw new BadRequestException("No se pudo crear el post.");
        }
    }


}
