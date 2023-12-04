package br.unitins.projeto.repository;

import java.util.List;

import br.unitins.projeto.model.Beneficiario;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class BeneficiarioRepository implements PanacheRepository<Beneficiario> {

    public List<Beneficiario> findByNome(String nome){
        if (nome == null)
            return null;
        return find("UPPER(nome) LIKE ?1 ", "%"+nome.toUpperCase()+"%").list();
    }

    public List<Beneficiario> findByCPF(String cpf){
        if (cpf == null)
            return null;
        return find("cpf LIKE ?1 ", "%"+cpf+"%").list();
    }

    public List<Beneficiario> getAll(Long idOrgao){
        if (idOrgao == null)
            return null;
        return find("SELECT b FROM Beneficiario b WHERE EXISTS " +
                        "(SELECT 1 FROM Atendimento a WHERE a.beneficiario = b AND a.orgao.id = ?1)",
                idOrgao).list();
    }

    public PanacheQuery<Beneficiario> findByNomeOrCpf(String nomeOuCpf) {
        if (nomeOuCpf != null) {
            return find("UPPER(nome) LIKE ?1 OR UPPER(cpf) LIKE ?1  ", "%" + nomeOuCpf.toUpperCase() + "%");
        }
        return null;
    }
}