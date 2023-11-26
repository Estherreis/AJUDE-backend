package br.unitins.projeto.repository;

import java.util.List;

import br.unitins.projeto.model.Beneficiario;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class BeneficiarioRepository implements PanacheRepository<Beneficiario> {

    public List<Beneficiario> findByNome(String nome){
        if (nome == null)
            return null;
        return find("UPPER(nome) LIKE ?1 ", "%"+nome.toUpperCase()+"%").list();
    }

}