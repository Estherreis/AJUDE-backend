package br.unitins.projeto.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class Orgao extends DefaultEntity {

    @Column(name = "nome", nullable = false, unique = true)
    private String nome;

    @Column(name = "sigla", nullable = false)
    private String sigla;

    @ManyToOne
    @JoinColumn(name = "id_municipio", nullable = false)
    private Municipio municipio;

    @Column(name = "ativo", nullable = false)
    private Boolean ativo;
}
