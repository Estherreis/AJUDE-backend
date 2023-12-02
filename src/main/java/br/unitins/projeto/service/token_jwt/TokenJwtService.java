package br.unitins.projeto.service.token_jwt;

import br.unitins.projeto.model.Orgao;
import br.unitins.projeto.model.Perfil;
import br.unitins.projeto.model.Usuario;

public interface TokenJwtService {
    public String generateJwt(Usuario usuario);

    public String generateJwt(Orgao orgao, Perfil perfil, Usuario usuario);
}
