package org.example.financeflowapi.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.example.financeflowapi.domain.model.repository.service.exception.model.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${auth.jwt.secret}")
    private String jwtSecretKey;

    @Value("${auth.jwt-expiration-milliseg}")
    private Long jwtExpirationMs;

    public String gerarToken(Authentication authentication){

        // data atual + soma de mais 1 dia em milisegundos
        Date dataExpiracao = new Date(new Date().getTime() + jwtExpirationMs);

        //usuario atual da autenticacao
        Usuario usuario = (Usuario) authentication.getPrincipal();

        try {
            Key secretKey = Keys.hmacShaKeyFor(jwtSecretKey.getBytes("UTF-8"));

            return Jwts.builder()
                    .setSubject(usuario.getUsername())
                    .setIssuedAt(new Date())
                    .setExpiration(dataExpiracao)
                    .signWith(secretKey)
                    .compact();
        } catch (Exception e){
            System.out.println(e.getMessage());
            return "";
        }
    }

    //metodo que com base na chave privada, descobre quais as permissoes do usuario
    private Claims getClaims(String token){

        try {
            Key secretKey = Keys.hmacShaKeyFor(jwtSecretKey.getBytes("UTF-8"));

            Claims claims = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();
            return claims;

        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    //metodo que busca o email do usuario dentro do token
    public String getUsernameFromToken(String token){
        Claims claims = getClaims(token);

        if(claims == null){
            return null;
        }

        return claims.getSubject();
    }

    //metodo para validar o token
    public Boolean validateToken(String token){
        Claims claims = getClaims(token);

        if(claims == null){
            return false;
        }

        String email = claims.getSubject();
        Date dataExpiracao = claims.getExpiration();
        Date agora = new Date(System.currentTimeMillis());

        if (email != null && agora.before(dataExpiracao)){
            return true;
        }

        return false;
    }
}
