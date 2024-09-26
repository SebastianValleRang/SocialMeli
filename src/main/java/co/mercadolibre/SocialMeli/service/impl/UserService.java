package co.mercadolibre.SocialMeli.service.impl;

import co.mercadolibre.SocialMeli.dto.response.ClientFollowedDTO;
import co.mercadolibre.SocialMeli.dto.response.ResponseDTO;
import co.mercadolibre.SocialMeli.dto.response.UserDTO;
import co.mercadolibre.SocialMeli.entity.User;
import co.mercadolibre.SocialMeli.exception.BadRequestException;
import co.mercadolibre.SocialMeli.exception.NotFoundException;
import co.mercadolibre.SocialMeli.repository.IUsersRepository;
import co.mercadolibre.SocialMeli.repository.impl.UsersRepository;
import co.mercadolibre.SocialMeli.service.IUserService;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;

public class UserService implements IUserService {

    IUsersRepository iUsersRepository;

    public UserService(UsersRepository usersRepository){
        this.iUsersRepository = usersRepository;
    }


    @Override
    public ResponseDTO followSeller(int userId, int userIdToFollow) {
        // Lista de usuarios
        List<User> userList = iUsersRepository.finAllUsers();

        // Filtro de vendedor
        Optional<User> sellerFilter = userList.stream()
                .filter(u -> u.getUserId() == userIdToFollow)
                .findFirst();

        // Validaciones vendedor
        if (sellerFilter.isEmpty()) throw new BadRequestException("Vendedor no encontrado");

        if (sellerFilter.get().getFollowers().isEmpty()) throw new BadRequestException("Solicitud erronea. Usuario a seguir no es vendedor");

        // Filtro Cliente
        User clientFilter = userList.stream()
                .filter(u -> u.getUserId() == userId)
                .findFirst().orElse(null);

        // Validaciones Cliente
        if (clientFilter.equals(null)) throw new BadRequestException("Id de cliente no encontrado");

        // Adicion cliente a la lista de seguidores del vendedor
        sellerFilter.get().getFollowers().add(clientFilter);

        return new ResponseDTO("Acción Follow realizada correctamente",HttpStatus.OK);

    }

    @Override
    public ClientFollowedDTO listFollowedSellers(int userId) {
        return null;
    }

    @Override
    public ResponseDTO unfollow(int userId, int userIdToFollow) {
        // Lista de usuarios
        List<User> userList = iUsersRepository.finAllUsers();

        // Filtro de vendedor
        Optional<User> sellerFilter = userList.stream()
                .filter(u -> u.getUserId() == userIdToFollow)
                .findFirst();

        if (sellerFilter.isEmpty()) throw new BadRequestException("Vendedor no encontrado");

        // Lista de seguidores del vendedor
        List<User> listFollowers = sellerFilter.get().getFollowers();

        // Validacion que cliente exista dentro de esa lista
        listFollowers.stream().filter(f -> f.getUserId() == userId).findFirst();

        // Filtro cliente
        User clientFilter = userList.stream()
                .filter(u -> u.getUserId() == userId)
                .findFirst().orElse(null);

        // Eliminar seguidor
        sellerFilter.get().getFollowers().remove(clientFilter);

        return new ResponseDTO("Acción Follow realizada correctamente", HttpStatus.OK);
    }

    @Override
    public ClientFollowedDTO listFollowedSellersOrder(int userId, String Order) {
        return null;
    }
}
