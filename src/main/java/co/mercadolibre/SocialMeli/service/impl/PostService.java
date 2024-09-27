package co.mercadolibre.SocialMeli.service.impl;

import co.mercadolibre.SocialMeli.dto.request.PostRequestDTO;
import co.mercadolibre.SocialMeli.dto.response.ResponseDTO;
import co.mercadolibre.SocialMeli.entity.Post;
import co.mercadolibre.SocialMeli.entity.Product;
import co.mercadolibre.SocialMeli.entity.User;
import co.mercadolibre.SocialMeli.exception.BadRequestException;
import co.mercadolibre.SocialMeli.exception.NotFoundException;
import co.mercadolibre.SocialMeli.repository.impl.UsersRepository;
import co.mercadolibre.SocialMeli.service.IPostService;
import co.mercadolibre.SocialMeli.utils.GlobalMethods;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
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
    public ResponseDTO createPost(PostRequestDTO postDTO) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        if(postDTO.getDate() == null || postDTO.getUserId() == 0 || postDTO.getCategory() == 0 || postDTO.getPrice() == 0){
            throw new BadRequestException("Formato de la request erroneo.");
        }

        Post post = mapper.convertValue(postDTO, Post.class);
        User user = globalMethods.getUserById(post.getUserId());

        if (user == null) {
            throw new NotFoundException("Usuario no encontrado.");
        }
        if ( !(globalMethods.verifyProduct(post.getProduct()) )){
            throw new NotFoundException("Producto no encontrado.");
        }
        try{
            post.setPostId(globalMethods.getNewPostId(user));
            usersRepository.createPost(post, user);
            return new ResponseDTO("Post creado correctamente.", HttpStatus.OK);
        }catch (Exception e){
            throw new BadRequestException("No se pudo crear el post.");
        }
    }


}
