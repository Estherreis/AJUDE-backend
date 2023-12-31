package br.unitins.projeto.service.token_jwt;

import br.unitins.projeto.model.Orgao;
import br.unitins.projeto.model.Perfil;
import br.unitins.projeto.model.Usuario;
import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.Duration;
import java.time.Instant;
import java.util.Set;
import java.util.stream.Collectors;

@ApplicationScoped
public class TokenJwtServiceImpl implements TokenJwtService {

    private static final Duration EXPIRATION_TIME = Duration.ofHours(24);

    @Override
    public String generateJwt(Usuario usuario) {
        Instant now = Instant.now();
        Instant expiryDate = now.plus(EXPIRATION_TIME);

        Set<String> roles = usuario.getPerfil()
                .stream().map(p -> p.getLabel())
                .collect(Collectors.toSet());

        return Jwt.issuer("unitins-jwt")
                .subject(usuario.getLogin())
                .groups(roles)
                .expiresAt(expiryDate)
                .sign();
    }

    @Override
    public String generateJwt(Orgao orgao, Set<Perfil> perfil, Usuario usuario) {
        Instant now = Instant.now();
        Instant expiryDate = now.plus(EXPIRATION_TIME);

        Set<String> roles = perfil.stream().map(p -> p.getLabel())
                .collect(Collectors.toSet());

        return Jwt.issuer("unitins-jwt")
                .subject(usuario.getLogin())
                .groups(roles)
                .claim("orgao", orgao.getId())
                .expiresAt(expiryDate)
                .sign();
    }
}
