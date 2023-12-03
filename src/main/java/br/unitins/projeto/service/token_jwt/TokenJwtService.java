package br.unitins.projeto.service.token_jwt;

import br.unitins.projeto.model.Orgao;
import br.unitins.projeto.model.Perfil;
import br.unitins.projeto.model.Usuario;

import java.util.Set;

public interface TokenJwtService {
    public String generateJwt(Usuario usuario);

    public String generateJwt(Orgao orgao, Set<Perfil> perfil, Usuario usuario);
}
