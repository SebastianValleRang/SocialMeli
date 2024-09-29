package co.mercadolibre.SocialMeli.controller;

import co.mercadolibre.SocialMeli.dto.request.PromoPostRequestDTO;
import co.mercadolibre.SocialMeli.service.IPromoPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class PromoController {
    @Autowired
    IPromoPostService iPromoPostService;

    @PostMapping("/products/promo-post")
    public ResponseEntity<?> publicPromoPost(@RequestBody PromoPostRequestDTO promoPostRequestDTO){
        return new ResponseEntity<>(iPromoPostService.publicPromoPost(promoPostRequestDTO), HttpStatus.OK);
    }
    @GetMapping("/products/promo-post/count")
    public ResponseEntity<?> countPromoPostUser(@RequestParam String user_id){
        return new ResponseEntity<>(iPromoPostService.countPromoPostUser(user_id), HttpStatus.OK);
    }
}
