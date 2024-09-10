package med.voll.api.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import med.voll.api.domain.usuarios.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.security.secret}")
    private String apiSecret;

    public String generarToken(Usuario usuario){

        try {
            //los secrets van encriptados
            //y los parametros estan hardcodeados ahora pero deben ser dinamicos
            Algorithm algorithm = Algorithm.HMAC256(apiSecret);
            return JWT.create()
                    //issuer es quien emite el jwt
                    .withIssuer("voll med")
                    //va dirigido a
                    .withSubject(usuario.getLogin())
                    //withClaim(string qie identifica el nombre del claim,
                    // info a almacenar)
                    .withClaim("id",usuario.getId())
                    //expiracion
                    .withExpiresAt(generarFechaExpiracion())
                    .sign(algorithm);
        } catch (JWTCreationException exception){
            throw new RuntimeException();
        }
    }

    //para quien esta siendo generado el token, en el metodo generarToken
    public String getSubject(String token){
        if(token==null){
            throw new RuntimeException();
        }
        DecodedJWT verifier = null;
        try {
            Algorithm algorithm = Algorithm.HMAC256(apiSecret);//validamos firma
            verifier = JWT.require(algorithm)
                    .withIssuer("voll med")//valida el emisor
                    .build()//hace un build de este objeto verifier
                    .verify(token);//valida el token
            verifier.getSubject();
        } catch (JWTVerificationException exception){
            System.out.println(exception.toString());
        }
        if(verifier.getSubject()==null){
            throw new RuntimeException("Verifier invalido");
        }
        return verifier.getSubject();
    }

    private Instant generarFechaExpiracion(){
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-05:00"));
    }


}
