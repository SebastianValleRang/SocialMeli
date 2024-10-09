package co.mercadolibre.SocialMeli.unit.service;

import co.mercadolibre.SocialMeli.dto.ProductDTO;
import co.mercadolibre.SocialMeli.dto.response.PostResponseDTO;
import co.mercadolibre.SocialMeli.dto.response.RecentPostDTO;
import co.mercadolibre.SocialMeli.dto.response.ResponseDTO;
import co.mercadolibre.SocialMeli.entity.Post;
import co.mercadolibre.SocialMeli.entity.Product;
import co.mercadolibre.SocialMeli.entity.User;
import co.mercadolibre.SocialMeli.exception.BadRequestException;
import co.mercadolibre.SocialMeli.exception.NotFoundException;
import co.mercadolibre.SocialMeli.repository.IUsersRepository;
import co.mercadolibre.SocialMeli.service.impl.PostService;
import co.mercadolibre.SocialMeli.service.impl.UserService;
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

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PostServiceTest {
    @Mock
    IUsersRepository iUsersRepository;
    @Mock
    GlobalMethods globalMethods;
    @Mock
    ObjectMapper mapper;
    @InjectMocks
    PostService postService;

    @Nested
    class PostsFollowed{
        @DisplayName("T-0005: Posts by Followed Users Last Two Weeks Ok")
        @Test
        void getPostsByFollowedUsersLastTwoWeeksOkTest(){
            //Arrange
            User userUno = Data.createUser(1,"Fernanda");
            User sellerUno = Data.createSellerWithDate(2,"Pedro", LocalDate.now());
            User sellerDos = Data.createSellerWithDate(2,"Alvaro", LocalDate.of(2024, 10, 05));
            List<User> followedList = Arrays.asList(sellerUno, sellerDos);
            userUno.setFollowed(followedList);

            List<Post> aux = sellerUno.getPosts();
            List<PostResponseDTO> auxPost = aux.stream()
                    .map(post -> {
                                Product product = post.getProduct();
                                return new PostResponseDTO(
                                        post.getUserId(),
                                        post.getPostId(),
                                        post.getDate(),
                                        new ProductDTO(
                                                product.getProductId(),
                                                product.getProductName(),
                                                product.getType(),
                                                product.getBrand(),
                                                product.getColor(),
                                                product.getNotes()
                                        ),
                                        post.getCategory(),
                                        post.getPrice()
                                );
                            }
                    )
                    .collect(Collectors.toList());

            auxPost.addAll(auxPost);
            RecentPostDTO expectedResponse = new RecentPostDTO(
                    userUno.getUserId(),
                    auxPost
            );

            //Simulation
            when(iUsersRepository.findAllUsers()).thenReturn(List.of(userUno,sellerUno, sellerDos));
            when(globalMethods.getUserById(1)).thenReturn(userUno);
            when(mapper.convertValue(any(Post.class), eq(PostResponseDTO.class))).thenReturn(expectedResponse.getRecentPosts().get(0));

            //Act
            RecentPostDTO realResponse = postService.getPostsByFollowedUsersLastTwoWeeks(1,"date_asc");

            //Assert
            assertEquals(expectedResponse,realResponse);
            verify(iUsersRepository).findAllUsers();
            verify(globalMethods, times(2)).getUserById(anyInt());
        }

        @DisplayName("T-0005: Posts by Followed Users Last Two Weeks Bad Request")
        @Test
        void getPostsByFollowedUsersLastTwoWeeksNoOrderTest(){

            //Arrange
            User userUno = Data.createUser(1,"Fernanda");
            User sellerUno = Data.createSellerWithDate(2,"Pedro", LocalDate.now());
            User sellerDos = Data.createSellerWithDate(2,"Alvaro", LocalDate.of(2024, 10, 05));
            List<User> followedList = Arrays.asList(sellerUno, sellerDos);
            userUno.setFollowed(followedList);
            ResponseDTO expectedResponse = new ResponseDTO("Orden no válido.",HttpStatus.BAD_REQUEST);

            List<Post> aux = sellerUno.getPosts();
            List<PostResponseDTO> auxPost = aux.stream()
                    .map(post -> {
                                Product product = post.getProduct();
                                return new PostResponseDTO(
                                        post.getUserId(),
                                        post.getPostId(),
                                        post.getDate(),
                                        new ProductDTO(
                                                product.getProductId(),
                                                product.getProductName(),
                                                product.getType(),
                                                product.getBrand(),
                                                product.getColor(),
                                                product.getNotes()
                                        ),
                                        post.getCategory(),
                                        post.getPrice()
                                );
                            }
                    )
                    .collect(Collectors.toList());

            //Simulation
            when(iUsersRepository.findAllUsers()).thenReturn(List.of(userUno,sellerUno, sellerDos));
            when(globalMethods.getUserById(1)).thenReturn(userUno);
            when(mapper.convertValue(any(Post.class), eq(PostResponseDTO.class))).thenReturn(auxPost.get(0));
            //when(userUno.getFollowed()).thenReturn(followedList);

            //Act & Assert
            BadRequestException badRequestException = assertThrows(BadRequestException.class, () -> postService.getPostsByFollowedUsersLastTwoWeeks(1,"prueba"));
            assertTrue(badRequestException.getMessage().contains(expectedResponse.getMessage()));
            verify(iUsersRepository).findAllUsers();
            verify(globalMethods, times(2)).getUserById(anyInt());

        }

        @DisplayName("T-0005: Posts by Followed Users Last Two Weeks Not Found")
        @Test
        void getPostsByFollowedUsersLastTwoWeeksNotFoundTest(){
            //Arrange
            ResponseDTO expectedResponse = new ResponseDTO("No hay usuarios registrados",HttpStatus.NOT_FOUND);

            //Simulation
            when(iUsersRepository.findAllUsers()).thenReturn(Collections.EMPTY_LIST);

            //Act & Assert
            NotFoundException notFoundException = assertThrows(NotFoundException.class, () ->  postService.getPostsByFollowedUsersLastTwoWeeks(1,"prueba"));
            assertTrue(notFoundException.getMessage().contains(expectedResponse.getMessage()));
        }

    }

}
