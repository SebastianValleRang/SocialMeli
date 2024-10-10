package co.mercadolibre.SocialMeli.unit.service;

import co.mercadolibre.SocialMeli.dto.response.CountPromoPostDTO;
import co.mercadolibre.SocialMeli.entity.User;
import co.mercadolibre.SocialMeli.exception.BadRequestException;
import co.mercadolibre.SocialMeli.exception.NotFoundException;
import co.mercadolibre.SocialMeli.repository.IUsersRepository;
import co.mercadolibre.SocialMeli.service.impl.PromoPostService;
import co.mercadolibre.SocialMeli.util.Data;
import co.mercadolibre.SocialMeli.utils.GlobalMethods;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PromoPostServiceTest {
    @Mock
    IUsersRepository iUsersRepository;
    @InjectMocks
    PromoPostService promoPostService;

    @Nested
    class CountPromoPost{
        @DisplayName("T-0009: Contar post en promoción Ok")
        @Test
        void countPromoPostOk(){
            //Arrange
            User seller = Data.getSellerWithPromoPost();
            int userId = seller.getUserId();
            CountPromoPostDTO expected = new CountPromoPostDTO(userId, seller.getUserName(), 1);

            //Act
            when(iUsersRepository.findAllUsers()).thenReturn(List.of(seller));
            CountPromoPostDTO found = promoPostService.countPromoPostUser(userId);

            //Assert
            Assertions.assertEquals(expected, found);

        }

        @DisplayName("T-0009: El usuario no es un vendedor")
        @Test
        void countPromoPostNotSeller(){
            //Arrange
            User seller = Data.createUser(1, "JuanPerez");
            int userId = seller.getUserId();
            String expected = "El usuario 1 no es un vendedor";

            //Act
            when(iUsersRepository.findAllUsers()).thenReturn(List.of(seller));

            //Assert
            BadRequestException badRequestException = assertThrows(BadRequestException.class, () -> promoPostService.countPromoPostUser(userId));
            assertTrue(badRequestException.getMessage().contains(expected));
        }

        @DisplayName("T-0009: El id no existe")
        @Test
        void countPromoPostNotFound(){
            //Arrange
            User seller = Data.createUser(1, "JuanPerez");
            int userId = 2;
            String expected = "Usuario no encontrado";

            //Act
            when(iUsersRepository.findAllUsers()).thenReturn(List.of(seller));

            //Assert
            NotFoundException notFoundException = assertThrows(NotFoundException.class, () -> promoPostService.countPromoPostUser(userId));
            assertTrue(notFoundException.getMessage().contains(expected));
        }
    }
}
