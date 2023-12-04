package br.unitins.projeto.repository;

import br.unitins.projeto.model.Movimentacao;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class MovimentacaoRepository implements PanacheRepository<Movimentacao> {

    public PanacheQuery<Movimentacao> findByAtendimento(Long idAtendimento) {
        if (idAtendimento == null)
            return null;
        return find("atendimento.id = ?1 ", idAtendimento);
    }
}

