package co.mercadolibre.SocialMeli.utils;

import co.mercadolibre.SocialMeli.dto.ProductDTO;
import co.mercadolibre.SocialMeli.entity.Product;
import co.mercadolibre.SocialMeli.entity.User;
import co.mercadolibre.SocialMeli.repository.IProductRepository;
import co.mercadolibre.SocialMeli.repository.IUsersRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GlobalMethods {
    @Autowired
    IUsersRepository iUsersRepository;
    @Autowired
    IProductRepository iProductRepository;

    public User getUserById(int userId){
        return iUsersRepository.findAllUsers().stream().filter(entry -> entry.getUserId() == userId).findFirst().orElse(null);
    }

    public boolean isNotSeller(User seller){
        return seller.getPosts().isEmpty();
    }
    public Product verifyProduct(ProductDTO productDTO){
        ObjectMapper mapper = new ObjectMapper();
        Product product = mapper.convertValue(productDTO, Product.class);
        return iProductRepository.findAllProducts().stream()
                .filter(p->p.equals(product))
                .findFirst().orElse(null);
    }


}
