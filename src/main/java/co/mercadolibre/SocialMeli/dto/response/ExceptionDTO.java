package co.mercadolibre.SocialMeli.dto.response;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public class ExceptionDTO {
    private String message;
    private HttpStatus status;
}
