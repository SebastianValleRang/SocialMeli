package co.mercadolibre.SocialMeli.unit.service;

import co.mercadolibre.SocialMeli.repository.IUsersRepository;
import co.mercadolibre.SocialMeli.service.impl.PromoPostService;
import co.mercadolibre.SocialMeli.utils.GlobalMethods;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class PromoPostServiceTest {
    @Mock
    IUsersRepository iUsersRepository;
    @Mock
    GlobalMethods globalMethods;
    @Mock
    ObjectMapper mapper;
    @InjectMocks
    PromoPostService promoPostService;

    @Nested
    class CountPromoPost{
        @DisplayName("T-0009: Contar post en promoción Ok")
        @Test
        void countPromoPostOk(){
            //Arrange

            //Act
            //Assert
        }

        @DisplayName("T-0009: El usuario no es un vendedor")
        @Test
        void countPromoPostNotSeller(){
            //Arrange
            //Act
            //Assert
        }
    }
}
