package br.unitins.projeto.model;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;

import java.util.Set;

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class OrgaoPerfil extends DefaultEntity {

    @JoinColumn(name = "id_perfil", nullable = false)
    Set<Perfil> perfil;

    @ManyToOne
    @JoinColumn(name = "id_orgao", nullable = false)
    Orgao orgao;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    Usuario usuario;

    public Set<Perfil> getPerfil() {
        return perfil;
    }

    public void setPerfil(Set<Perfil> perfil) {
        this.perfil = perfil;
    }

    public Orgao getOrgao() {
        return orgao;
    }

    public void setOrgao(Orgao orgao) {
        this.orgao = orgao;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
