package br.unitins.projeto.repository;

import br.unitins.projeto.model.Encaminhamento;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class EncaminhamentoRepository implements PanacheRepository<Encaminhamento> {

    public List<Encaminhamento> findByAtendimento(Long idAtendimento){
        if (idAtendimento == null)
            return null;
        return find("atendimento.id = ?1 ", idAtendimento).list();
    }

}
