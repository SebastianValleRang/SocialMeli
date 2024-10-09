package co.mercadolibre.SocialMeli.unit.service;

import co.mercadolibre.SocialMeli.dto.response.ClientFollowedDTO;
import co.mercadolibre.SocialMeli.dto.response.ExceptionDTO;
import co.mercadolibre.SocialMeli.entity.User;
import co.mercadolibre.SocialMeli.exception.BadRequestException;
import co.mercadolibre.SocialMeli.repository.IUsersRepository;
import co.mercadolibre.SocialMeli.service.impl.UserService;
import co.mercadolibre.SocialMeli.util.Data;
import co.mercadolibre.SocialMeli.utils.GlobalMethods;
import org.junit.jupiter.api.*;
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
public class UserServiceTest {

    @Mock
    IUsersRepository usersRepository;
    @Mock
    GlobalMethods globalMethods;

    @InjectMocks
    UserService userService;

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
            when(usersRepository.findAllUsers()).thenReturn(usersList);
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
            when(usersRepository.findAllUsers()).thenReturn(usersList);
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
            when(usersRepository.findAllUsers()).thenReturn(usersList);
            when(globalMethods.getUserById(userId)).thenReturn(user);

            //Assert
            BadRequestException badRequestException = assertThrows(BadRequestException.class, ()->userService.listFollowedSellers(userId,order));
            assertTrue(badRequestException.getMessage().contains(expectedResponse.getMessage()));
            verify(usersRepository).findAllUsers();
        }

    }
}
