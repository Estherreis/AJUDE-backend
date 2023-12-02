package br.unitins.projeto.repository;

import br.unitins.projeto.model.OrgaoPerfil;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class OrgaoPerfilRepository implements PanacheRepository<OrgaoPerfil> {

    public List<OrgaoPerfil> findByUsuario(Long idUsuario){
        if (idUsuario == null)
            return null;
        return find("usuario.id = ?1 ", idUsuario).list();
    }

}
