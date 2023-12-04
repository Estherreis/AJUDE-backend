package br.unitins.projeto.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Transient;

import java.util.Set;

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class Usuario extends DefaultEntity {

    @Column(nullable = false, length = 40)
    private String nome;

    @Column(nullable = false, length = 11)
    private String cpf;

    @Column(nullable = false, length = 20)
    private String login;

    @Column(nullable = false)
    private String senha;

    @Column(name = "nivel_sigilo")
    private Integer nivelSigilo;

    @Column(name = "ativo", nullable = false)
    private Boolean ativo;

    @Transient
    Set<Perfil> perfil;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Integer getNivelSigilo() {
        return nivelSigilo;
    }

    public void setNivelSigilo(Integer nivelSigilo) {
        this.nivelSigilo = nivelSigilo;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public Set<Perfil> getPerfil() {
        return perfil;
    }

    public void setPerfil(Set<Perfil> perfil) {
        this.perfil = perfil;
    }
    
}