package co.mercadolibre.SocialMeli.unit.service;

import co.mercadolibre.SocialMeli.dto.response.ExceptionDTO;
import co.mercadolibre.SocialMeli.dto.response.SellerFollowersDTO;
import co.mercadolibre.SocialMeli.entity.User;
import co.mercadolibre.SocialMeli.exception.BadRequestException;
import co.mercadolibre.SocialMeli.repository.IUsersRepository;
import co.mercadolibre.SocialMeli.service.impl.SellerService;
import co.mercadolibre.SocialMeli.util.Data;
import co.mercadolibre.SocialMeli.utils.GlobalMethods;
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

}
