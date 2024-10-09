package co.mercadolibre.SocialMeli.unit.service;

import co.mercadolibre.SocialMeli.dto.response.ExceptionDTO;
import co.mercadolibre.SocialMeli.dto.response.SellerFollowersDTO;
import co.mercadolibre.SocialMeli.entity.User;
import co.mercadolibre.SocialMeli.exception.BadRequestException;
import co.mercadolibre.SocialMeli.exception.NotFoundException;
import co.mercadolibre.SocialMeli.repository.IUsersRepository;
import co.mercadolibre.SocialMeli.service.impl.SellerService;
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

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.never;

@ExtendWith(MockitoExtension.class)
public class SellerServiceTest {
    @Mock
    IUsersRepository usersRepository;
    @Mock
    GlobalMethods globalMethods;

    @InjectMocks
    SellerService sellerService;

    @Nested
    class userOrderListT0004{
        @DisplayName("T-0004: Listar seguidores por orden ascendente")
        @Test
        public void listFollowersTestOrderAsc(){
            //Arrange
            int userId = 1;
            String order = "name_asc";
            List<User> usersList = Data.getUsersListTest(); //traer datos precargados
            User userSeller = usersList.stream().filter(u->u.getUserId() == userId).findFirst().orElse(null); //traer el vendedor numero 1
            SellerFollowersDTO expectedListFollowers = Data.getlistFollowersAscTest(); //traer lista ordenada

            //Act
            when(usersRepository.findAllUsers()).thenReturn(usersList);
            when(globalMethods.getUserById(userId)).thenReturn(userSeller);
            SellerFollowersDTO listFollowersByOrderAsc = sellerService.listFollowers(userId,order);

            //Assert
            assertEquals(expectedListFollowers,listFollowersByOrderAsc);
            verify(usersRepository).findAllUsers();
        }
        @DisplayName("T-0004: Listar seguidores por orden descendente")
        @Test
        public void listFollowersTestOrderDesc(){
            //Arrange
            int userId = 1;
            String order = "name_desc";
            List<User> usersList = Data.getUsersListTest(); //traer datos precargados
            User userSeller = usersList.stream().filter(u->u.getUserId() == userId).findFirst().orElse(null); //traer el vendedor numero 1
            SellerFollowersDTO expectedListFollowers = Data.getlistFollowersDescTest(); //traer lista ordenada

            //Act
            when(usersRepository.findAllUsers()).thenReturn(usersList);
            when(globalMethods.getUserById(userId)).thenReturn(userSeller);
            SellerFollowersDTO listFollowersByOrderDesc = sellerService.listFollowers(userId,order);

            //Assert
            assertEquals(expectedListFollowers,listFollowersByOrderDesc);
            verify(usersRepository).findAllUsers();
        }
        @DisplayName("T-0004: Orden invalido")
        @Test
        public void listFollowersTestOrderInvalid(){
            //Arrange
            int userId = 1;
            String order = "name";
            List<User> usersList = Data.getUsersListTest(); //traer datos precargados
            User userSeller = usersList.stream().filter(u->u.getUserId() == userId).findFirst().orElse(null); //traer el vendedor numero 1
            ExceptionDTO expectedResponse = new ExceptionDTO("Ordenamiento inválido", HttpStatus.BAD_REQUEST);

            //Act
            when(usersRepository.findAllUsers()).thenReturn(usersList);
            when(globalMethods.getUserById(userId)).thenReturn(userSeller);

            //Assert
            BadRequestException badRequestException = assertThrows(BadRequestException.class, ()->sellerService.listFollowers(userId,order));
            assertTrue(badRequestException.getMessage().contains(expectedResponse.getMessage()));
            verify(usersRepository).findAllUsers();
        }

    }

    @Nested
    class T0003{
        @DisplayName("Ordenar seguidores - Camino Bueno")
        @Test
        void orderingFollowersGood(){

            //Arrange
            int userId = 1;
            String order = null;
            List<User> usersList = Data.getUsersListTestT0003();
            User userById = usersList.stream().filter(u->u.getUserId() == userId).findFirst().orElse(null);

            SellerFollowersDTO expectedJson = Data.getlistFollowersSellersTest();


            //Act
            when(usersRepository.findAllUsers()).thenReturn(usersList);
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
            when(usersRepository.findAllUsers()).thenReturn(usersList);

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
            when(usersRepository.findAllUsers()).thenReturn(usersList);
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
            when(usersRepository.findAllUsers()).thenReturn(usersList);
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
