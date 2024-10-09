package co.mercadolibre.SocialMeli.unit.service;

import co.mercadolibre.SocialMeli.dto.response.ResponseDTO;
import co.mercadolibre.SocialMeli.entity.User;
import co.mercadolibre.SocialMeli.exception.NotFoundException;
import co.mercadolibre.SocialMeli.repository.IUsersRepository;
import co.mercadolibre.SocialMeli.service.IUserService;
import co.mercadolibre.SocialMeli.service.impl.UserService;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
    void followSellerSellerDoesntExistsTest(){
        //Arrange
        User user = Data.createUser(1,"Leandro");

        //Simulation
        when(iUsersRepository.findAllUsers()).thenReturn(List.of(user));
        when(globalMethods.getUserById(1)).thenReturn(user);
        when(globalMethods.getUserById(2)).thenReturn(null);

        //Act & Assert
        assertThrows(NotFoundException.class, () -> userService.followSeller(1,2));
        verify(iUsersRepository).findAllUsers();
        verify(globalMethods, times(2)).getUserById(anyInt());
        verify(globalMethods, never()).isNotSeller(any());

    }
}

}
