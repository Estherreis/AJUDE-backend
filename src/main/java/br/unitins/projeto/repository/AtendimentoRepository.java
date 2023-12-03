package br.unitins.projeto.repository;

import br.unitins.projeto.model.Atendimento;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AtendimentoRepository implements PanacheRepository<Atendimento> {

    public PanacheQuery<Atendimento> findByBeneficiario(Long idBeneficiario, Long idOrgao) {
        if (idBeneficiario == null)
            return null;
        return find("beneficiario.id = ?1 AND orgao.id = ?2", idBeneficiario, idOrgao);
    }

}
