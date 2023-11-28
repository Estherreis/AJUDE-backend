package br.unitins.projeto.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrimaryKeyJoinColumn;

import java.util.List;

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

    @Column(name = "nivel_sigilo", nullable = false)
    private Integer nivelSigilo;

    @Column(name = "ativo", nullable = false)
    private Boolean ativo;

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
}
