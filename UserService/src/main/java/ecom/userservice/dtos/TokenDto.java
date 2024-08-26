package ecom.userservice.dtos;

import ecom.userservice.models.Token;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TokenDto {
    private String token;
    private Date expiryAt;

    public static TokenDto from(Token token){
        return new TokenDto(token.getValue(), token.getExpiryAt());
    }
}
