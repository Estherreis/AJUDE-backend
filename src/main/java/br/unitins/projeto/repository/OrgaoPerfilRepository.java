package br.unitins.projeto.repository;

import br.unitins.projeto.model.OrgaoPerfil;
import br.unitins.projeto.model.Perfil;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class OrgaoPerfilRepository implements PanacheRepository<OrgaoPerfil> {

    public List<OrgaoPerfil> findByUsuario(Long idUsuario) {
        if (idUsuario == null)
            return null;
        return find("usuario.id = ?1 ", idUsuario).list();
    }

    public OrgaoPerfil findByPerfilOrgaoUsuario(Perfil perfil, Long idOrgao, Long idUsuario) {
        if (perfil == null || idOrgao == null || idUsuario == null)
            return null;
        return find("perfil = ?1 AND orgao.id = ?2 AND usuario.id = ?3", perfil, idOrgao, idUsuario).firstResult();
    }

}
