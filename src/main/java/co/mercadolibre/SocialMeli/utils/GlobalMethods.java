package co.mercadolibre.SocialMeli.utils;

import co.mercadolibre.SocialMeli.entity.Product;
import co.mercadolibre.SocialMeli.entity.User;
import co.mercadolibre.SocialMeli.repository.IProductRepository;
import co.mercadolibre.SocialMeli.repository.IUsersRepository;
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
    public Product getProductById(int productId){
        return iProductRepository.findAllProducts().stream()
                .filter(product -> product.getProductId() == productId)
                .findFirst().orElse(null);
    }


}
