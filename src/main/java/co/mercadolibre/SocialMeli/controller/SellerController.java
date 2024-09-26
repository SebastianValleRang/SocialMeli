package co.mercadolibre.SocialMeli.controller;

import co.mercadolibre.SocialMeli.service.ISellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

@RestController
public class SellerController {
    @Autowired
    ISellerService sellerService;

    @RequestMapping("/users/{userId}/followers/count")
    public ResponseEntity<?> countFollowers(@PathVariable int userId){
        return new ResponseEntity<>(sellerService.countFollowers(userId), HttpStatus.OK);
    }
}
