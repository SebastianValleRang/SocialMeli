package co.mercadolibre.SocialMeli.unit.service;
import co.mercadolibre.SocialMeli.dto.request.PromoPostRequestDTO;
import co.mercadolibre.SocialMeli.dto.response.ResponseDTO;
import co.mercadolibre.SocialMeli.entity.Post;
import co.mercadolibre.SocialMeli.entity.Product;
import co.mercadolibre.SocialMeli.entity.User;
import co.mercadolibre.SocialMeli.exception.NotFoundException;
import co.mercadolibre.SocialMeli.repository.IUsersRepository;
import co.mercadolibre.SocialMeli.service.impl.PromoPostService;
import co.mercadolibre.SocialMeli.util.Data;
import co.mercadolibre.SocialMeli.utils.GlobalMethods;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PromoPostServiceTest {
    @Mock
    IUsersRepository usersRepository;

    @Mock
    GlobalMethods globalMethods;

    @Mock
    ObjectMapper mapper;

    @InjectMocks
    PromoPostService promoPostService;
@Nested
class PostPromotion{
    @DisplayName("Test Extra : Post promotion ok")
    @Test
    void PostPromotionOkTest() {
        //Arrange
        PromoPostRequestDTO promoPostRequestDTO = Data.createPromoPost();
        Post post = Data.createPost();
        User user = Data.createSeller(1, "AndresMarquez");
        ResponseDTO expectedResponse = new ResponseDTO("Promocion creada: " + promoPostRequestDTO.getProduct().getProductName() + " por " + user.getUserName(), HttpStatus.OK);

        //Simulation
        when(usersRepository.findAllUsers()).thenReturn(List.of(user));
        when(mapper.convertValue(promoPostRequestDTO, Post.class)).thenReturn(post);
        when(usersRepository.findAllUsers()).thenReturn(List.of(user));
        when(globalMethods.verifyProduct(post.getProduct())).thenReturn(true);
        // Usar doAnswer para simular el comportamiento del método createPost
        doAnswer(invocation -> {
            Post argPost = invocation.getArgument(0);
            User argUser = invocation.getArgument(1);
            argUser.getPosts().add(argPost);
            return argUser.getPosts();
        }).when(usersRepository).createPost(any(Post.class), any(User.class));

        //Act
        ResponseDTO realResponse = promoPostService.postPromotion(promoPostRequestDTO);

        //Assert
        assertEquals(expectedResponse, realResponse);
        verify(usersRepository, times(2)).findAllUsers();
        verify(mapper).convertValue(promoPostRequestDTO,Post.class);
        verify(globalMethods).verifyProduct(any(Product.class));
        verify(usersRepository).createPost(any(Post.class), any(User.class));
    }
    @DisplayName("Test Extra : Post promotion user not found")
    @Test
    void PostPromotionUserNotFoundTest() {
        //Arrange
        PromoPostRequestDTO promoPostRequestDTO = Data.createPromoPost();
        User user = Data.createSeller(6, "AndresMarquez");
        ResponseDTO expectedResponse = new ResponseDTO("El usuario suministrado no existe", HttpStatus.NOT_FOUND);

        //Simulation
        when(usersRepository.findAllUsers()).thenReturn(List.of(user));

        //Act & Assert
        NotFoundException notFoundException = assertThrows(NotFoundException.class, ()->{
            promoPostService.postPromotion(promoPostRequestDTO);
        });
        assertEquals(expectedResponse.getMessage(), notFoundException.getMessage());
        verify(usersRepository, times(2)).findAllUsers();

    }
}



}
