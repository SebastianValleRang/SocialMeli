package co.mercadolibre.SocialMeli.service.impl;

import co.mercadolibre.SocialMeli.dto.request.PromoPostRequestDTO;
import co.mercadolibre.SocialMeli.dto.response.ResponseDTO;
import co.mercadolibre.SocialMeli.entity.Post;
import co.mercadolibre.SocialMeli.entity.User;
import co.mercadolibre.SocialMeli.exception.NotFoundException;
import co.mercadolibre.SocialMeli.repository.impl.UsersRepository;
import co.mercadolibre.SocialMeli.service.IPromoPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class PromoPostService implements IPromoPostService {
    @Autowired
    UsersRepository usersRepository;

    @Override
    public ResponseDTO publicPromoPost(PromoPostRequestDTO promoPostRequestDTO) {

        User user = usersRepository.finAllUsers().stream()
                .filter(p->p.getUserId()==promoPostRequestDTO.getUserId())
                .findFirst().orElse(null);

        if(user == null){
            throw new NotFoundException("El usuario suministrado no existe");
        }

        // Aca va la verificacion del producto

        user.getPosts().add(
                new Post(
                        1,
                        user.getUserId(),
                        promoPostRequestDTO.getDate(),
                        promoPostRequestDTO.getProduct(),
                        promoPostRequestDTO.getCategory(),
                        promoPostRequestDTO.getPrice(),
                        promoPostRequestDTO.isHasPromo(),
                        promoPostRequestDTO.getDiscount()
                )
        );

        return new ResponseDTO("Promocion creada: "+promoPostRequestDTO.getProduct().getProductName()+" por "+user.getUserName(), HttpStatus.OK);
    }
}
