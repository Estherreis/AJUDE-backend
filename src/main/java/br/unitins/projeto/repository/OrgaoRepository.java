package br.unitins.projeto.repository;

import br.unitins.projeto.model.Orgao;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class OrgaoRepository implements PanacheRepository<Orgao> {

    public PanacheQuery<Orgao> findByNomeOuSigla(String nomeOuSigla) {
        if (nomeOuSigla != null) {
            return find("UPPER(nome) LIKE ?1 OR UPPER(sigla) LIKE ?1  ", "%" + nomeOuSigla.toUpperCase() + "%");
        }

        return null;
    }

}
