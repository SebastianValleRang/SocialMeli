package co.mercadolibre.SocialMeli.unit.service;

import co.mercadolibre.SocialMeli.dto.response.ClientFollowedDTO;
import co.mercadolibre.SocialMeli.dto.response.SellerFollowersDTO;
import co.mercadolibre.SocialMeli.dto.response.ClientFollowedDTO;
import co.mercadolibre.SocialMeli.dto.response.ExceptionDTO;
import co.mercadolibre.SocialMeli.entity.User;
import co.mercadolibre.SocialMeli.exception.BadRequestException;
import co.mercadolibre.SocialMeli.dto.response.ResponseDTO;
import co.mercadolibre.SocialMeli.exception.NotFoundException;
import co.mercadolibre.SocialMeli.dto.response.ResponseDTO;
import co.mercadolibre.SocialMeli.repository.IUsersRepository;
import co.mercadolibre.SocialMeli.service.impl.SellerService;
import co.mercadolibre.SocialMeli.service.impl.UserService;
import co.mercadolibre.SocialMeli.util.Data;
import co.mercadolibre.SocialMeli.utils.GlobalMethods;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.springframework.http.HttpStatus;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

import java.util.ArrayList;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    IUsersRepository iUsersRepository;
    @Mock
    GlobalMethods globalMethods;

    @InjectMocks
    UserService userService;

    @InjectMocks
    SellerService sellerService;

    @Nested
    class userOrderListT0004{
        @DisplayName("T-0004: Listar seguidos por orden ascendente")
        @Test
        public void listFollowedSellersTestOrderAsc(){
            //Arrange
            int userId = 4;
            String order = "name_asc";
            List<User> usersList = Data.getUsersListTest(); //traer datos precargados
            User user = usersList.stream().filter(u->u.getUserId() == userId).findFirst().orElse(null); //traer el vendedor numero 1
            ClientFollowedDTO expectedListFollowed = Data.getlistFollowedAscTest(); //traer lista ordenada

            //Act
            when(iUsersRepository.findAllUsers()).thenReturn(usersList);
            when(globalMethods.getUserById(userId)).thenReturn(user);
            ClientFollowedDTO listFollowedByOrderAsc = userService.listFollowedSellers(userId,order);

            //Assert
            assertEquals(expectedListFollowed,listFollowedByOrderAsc);
        }
        @DisplayName("T-0004: Listar seguidos por orden descendente")
        @Test
        public void listFollowedSellersTestOrderDesc(){
            //Arrange
            int userId = 4;
            String order = "name_desc";
            List<User> usersList = Data.getUsersListTest(); //traer datos precargados
            User user= usersList.stream().filter(u->u.getUserId() == userId).findFirst().orElse(null); //traer el vendedor numero 1
            ClientFollowedDTO expectedListFollowed = Data.getlistFollowedDescTest(); //traer lista ordenada

            //Act
            when(iUsersRepository.findAllUsers()).thenReturn(usersList);
            when(globalMethods.getUserById(userId)).thenReturn(user);
            ClientFollowedDTO listFollowedByOrderDesc = userService.listFollowedSellers(userId,order);

            //Assert
            assertEquals(expectedListFollowed,listFollowedByOrderDesc);
        }
        @DisplayName("T-0004: Orden inválido")
        @Test
        public void listFollowersTestOrderInvalid(){
            //Arrange
            int userId = 1;
            String order = "name";
            List<User> usersList = Data.getUsersListTest(); //traer datos precargados
            User user = usersList.stream().filter(u->u.getUserId() == userId).findFirst().orElse(null); //traer el vendedor numero 1
            ExceptionDTO expectedResponse = new ExceptionDTO("Ordenamiento inválido", HttpStatus.BAD_REQUEST);

            //Act
            when(iUsersRepository.findAllUsers()).thenReturn(usersList);
            when(globalMethods.getUserById(userId)).thenReturn(user);

            //Assert
            BadRequestException badRequestException = assertThrows(BadRequestException.class, ()->userService.listFollowedSellers(userId,order));
            assertTrue(badRequestException.getMessage().contains(expectedResponse.getMessage()));
            verify(iUsersRepository).findAllUsers();
        }

    }

@Nested
class FollowUser{
    @DisplayName("T-0001: Follow seller Ok")
    @Test
    void followSellerOkTest(){
        //Arrange
        User user = Data.createUser(1,"Leandro");
        User seller = Data.createSeller(2,"Pablo");
        ResponseDTO expectedResponse = new ResponseDTO("El usuario 1 ha seguido de manera exitosa al vendedor 2.", HttpStatus.OK);

        //Simulation
        when(iUsersRepository.findAllUsers()).thenReturn(List.of(user,seller));
        when(globalMethods.getUserById(1)).thenReturn(user);
        when(globalMethods.getUserById(2)).thenReturn(seller);
        when(globalMethods.isNotSeller(seller)).thenReturn(false);

        //Act
        ResponseDTO realResponse = userService.followSeller(1,2);

        //Assert
        assertEquals(expectedResponse,realResponse);
        verify(iUsersRepository).findAllUsers();
        verify(globalMethods, times(2)).getUserById(anyInt());
        verify(globalMethods).isNotSeller(seller);

    }
    @DisplayName("T-0001: Follow seller Not found")
    @Test
    void followSellerDoesntExistsTest(){
        //Arrange
        User user = Data.createUser(1,"Leandro");
        ResponseDTO expectedResponse = new ResponseDTO("No existe un vendedor con el id 2.",HttpStatus.NOT_FOUND);

        //Simulation
        when(iUsersRepository.findAllUsers()).thenReturn(List.of(user));
        when(globalMethods.getUserById(1)).thenReturn(user);
        when(globalMethods.getUserById(2)).thenReturn(null);

        //Act & Assert
        NotFoundException notFoundException =assertThrows(NotFoundException.class, () -> userService.followSeller(1,2));
        assertTrue(notFoundException.getMessage().contains(expectedResponse.getMessage()));
        verify(iUsersRepository).findAllUsers();
        verify(globalMethods, times(2)).getUserById(anyInt());
        verify(globalMethods, never()).isNotSeller(any());

    }
}

@Nested
    class UnfollowUser{
    @DisplayName("T-0002: Unfollow seller Ok")
    @Test
    void unfollowSellerOkTest(){
        //Arrange
        User follower = Data.createUser(1,"Leandro");
        User seller = Data.createFollowedSeller(2,"Pablo", follower);
        ResponseDTO expectedResponse = new ResponseDTO("El usuario 1 ha dejado de seguir al vendedor 2.", HttpStatus.OK);

        //Simulation
        when(iUsersRepository.findAllUsers()).thenReturn(List.of(follower,seller));
        when(globalMethods.getUserById(1)).thenReturn(follower);
        when(globalMethods.getUserById(2)).thenReturn(seller);

        //Act
        ResponseDTO realResponse = userService.unfollow(1,2);

        //Assert
        assertEquals(expectedResponse,realResponse);
        verify(iUsersRepository).findAllUsers();
        verify(globalMethods, times(2)).getUserById(anyInt());

    }

    @DisplayName("T-0002: Unfollow seller Not found")
    @Test
    void unfollowSellerDoesntExistsTest(){
        //Arrange
        User user = Data.createUser(1,"Leandro");
        ResponseDTO expectedResponse = new ResponseDTO("No existe un vendedor con el id 2.",HttpStatus.NOT_FOUND);

        //Simulation
        when(iUsersRepository.findAllUsers()).thenReturn(List.of(user));
        when(globalMethods.getUserById(1)).thenReturn(user);
        when(globalMethods.getUserById(2)).thenReturn(null);

        //Act & Assert
        NotFoundException notFoundException =assertThrows(NotFoundException.class, () -> userService.unfollow(1,2));
        assertTrue(notFoundException.getMessage().contains(expectedResponse.getMessage()));
        verify(iUsersRepository).findAllUsers();
        verify(globalMethods, times(2)).getUserById(anyInt());
        verify(globalMethods, never()).isNotSeller(any());

    }
}

    @Nested
    class T0003{
        @DisplayName("Ordenar seguidos - Camino Bueno")
        @Test
        void orderingFollowedGood(){

            //Arrange
            int userId = 1;
            String order = null;
            List<User> usersList = Data.getUsersListTestT0003();
            User userById = usersList.stream().filter(u->u.getUserId() == userId).findFirst().orElse(null);
            ClientFollowedDTO expectedJson = Data.getlistFollowedSellersTest();

            //Act
            when(iUsersRepository.findAllUsers()).thenReturn(usersList);
            when(globalMethods.getUserById(userId)).thenReturn(userById);

            ClientFollowedDTO serviceResponse = userService.listFollowedSellers(userId, order);

            //Assert
            Assertions.assertEquals(expectedJson,serviceResponse);
            verify(globalMethods).getUserById(anyInt());
            verify(iUsersRepository).findAllUsers();

        }

        @DisplayName("Ordenar seguidos - Camino Malo, no hay usuarios")
        @Test
        void orderingFollowedBadUsers(){

            //Arrange
            int userId = 1;
            String order = null;
            List<User> usersList = new ArrayList<>();

            //Act & Assert
            when(iUsersRepository.findAllUsers()).thenReturn(usersList);

            NotFoundException notFoundException = Assertions.assertThrows(NotFoundException.class, () -> {
                userService.listFollowedSellers(userId, order);
            });

            Assertions.assertEquals("No hay usuarios registrados", notFoundException.getMessage());
            verify(globalMethods, never()).getUserById(anyInt());
            verify(iUsersRepository).findAllUsers();

        }

        @DisplayName("Ordenar seguidos - Camino Malo, no encuentra el usuario")
        @Test
        void orderingFollowedBadFindUser(){

            //Arrange
            int userId = 1;
            String order = null;
            List<User> usersList = Data.getUsersListTestT0003();

            //Act & Assert
            when(iUsersRepository.findAllUsers()).thenReturn(usersList);
            when(globalMethods.getUserById(userId)).thenReturn(null);

            NotFoundException notFoundException = Assertions.assertThrows(NotFoundException.class, () -> {
                userService.listFollowedSellers(userId, order);
            });

            Assertions.assertEquals("Usuario con el id %d no se ha encontrado.".formatted(userId) , notFoundException.getMessage());
            verify(iUsersRepository).findAllUsers();
            verify(globalMethods).getUserById(anyInt());

        }

        @DisplayName("Ordenar seguidores - Camino Bueno")
        @Test
        void orderingFollowersGood(){

            //Arrange
            int userId = 1;
            String order = null;
            List<User> usersList = Data.getUsersListTestT0003();
            User userById = usersList.stream().filter(u->u.getUserId() == userId).findFirst().orElse(null);

            SellerFollowersDTO expectedJson = Data.getlistFollowersSellersTest();
            System.out.println(userId);
            System.out.println(userById);
            System.out.println(expectedJson);

            //Act
            when(iUsersRepository.findAllUsers()).thenReturn(usersList);
            when(globalMethods.getUserById(userId)).thenReturn(userById);
            when(globalMethods.isNotSeller(userById)).thenReturn(false);

            SellerFollowersDTO serviceResponse = sellerService.listFollowers(userId, order);

            //Assert
            Assertions.assertEquals(expectedJson,serviceResponse);
            verify(globalMethods).getUserById(anyInt());
            verify(globalMethods).isNotSeller(any(User.class));

        }

        @DisplayName("Ordenar seguidores - Camino Malo, no hay ususarios")
        @Test
        void orderingFollowersBadUsers(){

            //Arrange
            int userId = 1;
            String order = null;
            User userById = null;
            List<User> usersList = new ArrayList<>();

            //Act & Assert
            when(iUsersRepository.findAllUsers()).thenReturn(usersList);

            NotFoundException notFoundException = Assertions.assertThrows(NotFoundException.class, () -> {
                sellerService.listFollowers(userId, order);
            });

            Assertions.assertEquals("No hay usuarios registrados" , notFoundException.getMessage());
            verify(globalMethods, never()).getUserById(anyInt());
            verify(globalMethods, never()).isNotSeller(any(User.class));

        }

        @DisplayName("Ordenar seguidores - Camino Malo, no encuentra al vendedor")
        @Test
        void orderingFollowersBadSeller(){

            //Arrange
            int userId = 1;
            String order = null;
            User userById = null;
            List<User> usersList = Data.getUsersListTestT0003();

            //Act & Assert
            when(iUsersRepository.findAllUsers()).thenReturn(usersList);
            when(globalMethods.getUserById(userId)).thenReturn(userById);

            NotFoundException notFoundException = Assertions.assertThrows(NotFoundException.class, () -> {
                sellerService.listFollowers(userId, order);
            });

            Assertions.assertEquals("El usuario con el id %d no se ha encontrado".formatted(userId) , notFoundException.getMessage());
            verify(globalMethods).getUserById(anyInt());
            verify(globalMethods, never()).isNotSeller(any(User.class));

        }

        @DisplayName("Ordenar seguidores - Camino Malo, el usuario no es vendedor")
        @Test
        void orderingFollowersBadFindUser(){

            //Arrange
            int userId = 1;
            String order = null;
            List<User> usersList = Data.getUsersListTestT0003();
            User userById = usersList.stream().filter(u->u.getUserId() == userId).findFirst().orElse(null);

            //Act & Assert
            when(iUsersRepository.findAllUsers()).thenReturn(usersList);
            when(globalMethods.getUserById(userId)).thenReturn(userById);
            when(globalMethods.isNotSeller(userById)).thenReturn(true);

            NotFoundException notFoundException = Assertions.assertThrows(NotFoundException.class, () -> {
                sellerService.listFollowers(userId, order);
            });

            Assertions.assertEquals("El usuario con el id %d no es un vendedor".formatted(userId) , notFoundException.getMessage());
            verify(globalMethods).getUserById(anyInt());
            verify(globalMethods).isNotSeller(any(User.class));

        }

    }


}
