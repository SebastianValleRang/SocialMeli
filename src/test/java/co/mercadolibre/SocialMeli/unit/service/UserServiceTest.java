package co.mercadolibre.SocialMeli.unit.service;

import co.mercadolibre.SocialMeli.dto.response.ClientFollowedDTO;
import co.mercadolibre.SocialMeli.dto.response.SellerFollowersDTO;
import co.mercadolibre.SocialMeli.entity.User;
import co.mercadolibre.SocialMeli.exception.NotFoundException;
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

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

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
    class T0003{
        @DisplayName("Ordenar seguidos - Camino Bueno")
        @Test
        void orderingFollowedGood(){

            //Arrange
            int userId = 1;
            String order = null;
            List<User> usersList = Data.getUsersListTest();
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
            List<User> usersList = Data.getUsersListTest();

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
            List<User> usersList = Data.getUsersListTest();
            User userById = usersList.stream().filter(u->u.getUserId() == userId).findFirst().orElse(null);
            SellerFollowersDTO expectedJson = Data.getlistFollowersSellersTest();

            //Act
            when(globalMethods.getUserById(userId)).thenReturn(userById);
            when(globalMethods.isNotSeller(userById)).thenReturn(false);

            SellerFollowersDTO serviceResponse = sellerService.listFollowers(userId, order);

            //Assert
            Assertions.assertEquals(expectedJson,serviceResponse);
            verify(globalMethods).getUserById(anyInt());
            verify(globalMethods).isNotSeller(any(User.class));

        }

        @DisplayName("Ordenar seguidores - Camino Malo, no encuentra el usuario")
        @Test
        void orderingFollowersBadUsers(){

            //Arrange
            int userId = 1;
            String order = null;
            User userById = null;

            //Act & Assert
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
            List<User> usersList = Data.getUsersListTest();
            User userById = usersList.stream().filter(u->u.getUserId() == userId).findFirst().orElse(null);

            //Act & Assert
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
