package ul.ulstu.tamada.converter;

import com.nimbusds.jwt.SignedJWT;
import ul.ulstu.tamada.exception.TokenNotValidException;

import java.text.ParseException;

public class JwtToSignedJwtConverter extends BaseConverter<String, SignedJWT> {

    @Override
    public SignedJWT convert(String token) {
        try {
            return SignedJWT.parse(token);
        } catch (ParseException pex) {
            throw new TokenNotValidException();
        }
    }
}
