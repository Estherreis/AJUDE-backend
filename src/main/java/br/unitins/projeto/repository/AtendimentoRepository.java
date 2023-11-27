package br.unitins.projeto.repository;

import br.unitins.projeto.model.Atendimento;
import br.unitins.projeto.model.Encaminhamento;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class AtendimentoRepository implements PanacheRepository<Atendimento> {

    public List<Atendimento> findByBeneficiario(Long idBeneficiario){
        if (idBeneficiario == null)
            return null;
        return find("beneficiario.id = ?1 ", idBeneficiario).list();
    }

}
