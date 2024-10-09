package co.mercadolibre.SocialMeli.unit.service;

import co.mercadolibre.SocialMeli.dto.ProductDTO;
import co.mercadolibre.SocialMeli.dto.response.PostResponseDTO;
import co.mercadolibre.SocialMeli.dto.response.RecentPostDTO;
import co.mercadolibre.SocialMeli.entity.Post;
import co.mercadolibre.SocialMeli.entity.Product;
import co.mercadolibre.SocialMeli.entity.User;
import co.mercadolibre.SocialMeli.repository.IUsersRepository;
import co.mercadolibre.SocialMeli.service.impl.PostService;
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

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PostServiceTest {
    @Mock
    private IUsersRepository usersRepository;
    @Mock
    private GlobalMethods globalMethods;
    @Mock
    ObjectMapper mapper;

    @InjectMocks
    private PostService postService;

    @Nested
    class showPostLastTwoWeeks{
        @DisplayName("T0008: git Verificar que los post son unicamente de las últimas 2 semanas ")
        @Test
        void getPostsFromFollowedLastTwoWeeksTest() {
            //Arrange
            User client = Data.getUserThatFollows1Seller();
            int clientId = client.getUserId();

            Product product = new Product(1,"Mesedora","Muebles","Sillas jairo","Blanco", "Realizada con madera de roble");
            ProductDTO productDTO = new ProductDTO(1,"Mesedora","Muebles","Sillas jairo","Blanco", "Realizada con madera de roble");

            Post post = new Post(1, 2, LocalDate.parse("2024-10-03"), product,1,223.3);
            PostResponseDTO postDTO = new PostResponseDTO(1, 2, LocalDate.parse("2024-10-03"), productDTO,1,223.3);

            List<PostResponseDTO> expectedPosts = List.of(postDTO);

            //Act
            when(usersRepository.findAllUsers()).thenReturn(List.of(client));
            when(globalMethods.getUserById(clientId)).thenReturn(client);
            when(mapper.convertValue(post, PostResponseDTO.class)).thenReturn(postDTO);

            RecentPostDTO found = postService.getPostsByFollowedUsersLastTwoWeeks(clientId, null);

            //Assert
            Assertions.assertEquals(clientId, found.getUserId());
            Assertions.assertEquals(expectedPosts, found.getRecentPosts());
        }
    }
}
