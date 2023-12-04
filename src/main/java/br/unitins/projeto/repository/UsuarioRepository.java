package br.unitins.projeto.repository;

import br.unitins.projeto.model.Usuario;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UsuarioRepository implements PanacheRepository<Usuario> {

    public Usuario findByLoginAndSenha(String login, String senha) {
        if (login == null || senha == null)
            return null;

        return find("login = ?1 AND senha = ?2 ", login, senha).firstResult();
    }

    public Usuario findByLogin(String login) {
        if (login == null)
            return null;

        return find("login = ?1", login).firstResult();
    }

    public PanacheQuery<Usuario> findByNomeOuCpf(String nomeOuCpf) {
        if (nomeOuCpf != null) {
            return find("UPPER(nome) LIKE ?1 OR UPPER(cpf) LIKE ?1  ", "%" + nomeOuCpf.toUpperCase() + "%");
        }

        return null;
    }

}
