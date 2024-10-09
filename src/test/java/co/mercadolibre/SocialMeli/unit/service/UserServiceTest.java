package co.mercadolibre.SocialMeli.unit.service;

import co.mercadolibre.SocialMeli.dto.response.ClientFollowedDTO;
import co.mercadolibre.SocialMeli.entity.User;
import co.mercadolibre.SocialMeli.repository.IUsersRepository;
import co.mercadolibre.SocialMeli.repository.impl.UsersRepository;
import co.mercadolibre.SocialMeli.service.IUserService;
import co.mercadolibre.SocialMeli.service.impl.UserService;
import co.mercadolibre.SocialMeli.util.TestUtilsFactory;
import co.mercadolibre.SocialMeli.utils.GlobalMethods;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    UsersRepository usersRepository;
    @Mock
    GlobalMethods globalMethods;

    @InjectMocks
    UserService userService;

    @Nested
    class T0003{
        @DisplayName("Permite continuar con normalidad.")
        @Test
        void orderingExist(){

            //Arrange
            int userId = 1;
            String order = "name_asc";

            List<User> usersList = TestUtilsFactory.getUsersListTest();
            User userById = usersList.stream().filter(u->u.getUserId() == userId).findFirst().orElse(null);

            ClientFollowedDTO expectedJson = TestUtilsFactory.getlistFollowedSellersTest();

            //Act
            when(usersRepository.findAllUsers()).thenReturn(usersList);
            when(globalMethods.getUserById(userId)).thenReturn(userById);

            ClientFollowedDTO serviceResponse = userService.listFollowedSellers(userId, order);

            //Assert
            Assertions.assertEquals(expectedJson,serviceResponse);

        }
    }

}
