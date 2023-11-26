package br.unitins.projeto.repository;

import br.unitins.projeto.model.Orgao;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class OrgaoRepository implements PanacheRepository<Orgao> {
}
