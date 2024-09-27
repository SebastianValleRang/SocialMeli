package co.mercadolibre.SocialMeli.controller;

import co.mercadolibre.SocialMeli.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    IUserService iUserService;
    @GetMapping("/{userId}/follow/{userIdToFollow}")
    public ResponseEntity<?> followSeller(@PathVariable int userId, @PathVariable int userIdToFollow){
        return new ResponseEntity<>(iUserService.followSeller(userId,userIdToFollow), HttpStatus.OK);
    }

    @GetMapping("/{userId}/followed/list")
    public  ResponseEntity<?> listFollowedSellers(@PathVariable int userId){
        return new ResponseEntity<>(iUserService.listFollowedSellers(userId), HttpStatus.OK);
    }


}
