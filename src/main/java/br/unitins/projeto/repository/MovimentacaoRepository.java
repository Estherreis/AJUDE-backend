package br.unitins.projeto.repository;

import java.util.List;

import br.unitins.projeto.model.Movimentacao;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class MovimentacaoRepository implements PanacheRepository<Movimentacao> {
    
    public List<Movimentacao> findByAtendimento(Long idAtendimento) {
        return find("atendimento.id = ?1", idAtendimento).list();
    }
}
