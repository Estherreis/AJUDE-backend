package br.unitins.projeto.model;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class OrgaoPerfil extends DefaultEntity {

    @JoinColumn(name = "id_perfil", nullable = false)
    Perfil perfil;

    @ManyToOne
    @JoinColumn(name = "id_orgao", nullable = false)
    Orgao orgao;

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

    public Orgao getOrgao() {
        return orgao;
    }

    public void setOrgao(Orgao orgao) {
        this.orgao = orgao;
    }
}
