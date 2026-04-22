package nicolagraziani.U5_W3_D1_Spring_security.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import nicolagraziani.U5_W3_D1_Spring_security.entities.Employee;
import nicolagraziani.U5_W3_D1_Spring_security.exceptions.UnauthorizedException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

@Component
public class TokenTools {
    private final String secret;

    public TokenTools(@Value("${jwt.secret}") String secret) {
        this.secret = secret;
    }

    public String generateToken(Employee employee) {
        return Jwts.builder()
                .issuedAt(new Date(System.currentTimeMillis())) // Data di emissione (IaT - Issued At), va messa in millisecondi
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7)) // Data di scadenza (Expiration Date), anche questa in millisecondi
                .subject(String.valueOf(employee.getEmployeeId())) // Subject ovvero a chi appartiene il token. Inseriamo l'id del proprietario <-- MAI METTERE DATI SENSIBILI AL SUO INTERNO
                .signWith(Keys.hmacShaKeyFor(secret.getBytes())) // Firmiamo il token con un segreto e con un algoritmo apposito
                .compact();
    }

    public void verifyToken(String token) {
        try {
            Jwts.parser().verifyWith(Keys.hmacShaKeyFor(secret.getBytes())).build().parse(token);
        } catch (Exception ex) {
            throw new UnauthorizedException("Problemi col token, rifare il login!");
        }
    }

    public UUID extractIdFromToken(String token) {
        return UUID.fromString(Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject());
    }


}
