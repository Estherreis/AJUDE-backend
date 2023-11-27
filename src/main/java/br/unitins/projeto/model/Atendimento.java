package br.unitins.projeto.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;

import java.time.LocalDateTime;

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class Atendimento extends DefaultEntity {
    @ManyToOne
    @JoinColumn(name = "id_orgao", nullable = false)
    private Orgao orgao;

    @ManyToOne
    @JoinColumn(name = "id_beneficiario", nullable = false)
    private Beneficiario beneficiario;

    @Column(name = "data_inclusao", nullable = false)
    private LocalDateTime dataInclusao;

    @ManyToOne
    @JoinColumn(name = "id_usuario_inclusao", nullable = false)
    private Usuario usuarioInclusao;

    @Column(name = "descricao", nullable = false)
    private String descricao;

    @Column(name = "situacao_atendimento", nullable = false)
    private SituacaoAtendimento situacaoAtendimento;

    public Orgao getOrgao() {
        return orgao;
    }

    public void setOrgao(Orgao orgao) {
        this.orgao = orgao;
    }

    public Beneficiario getBeneficiario() {
        return beneficiario;
    }

    public void setBeneficiario(Beneficiario beneficiario) {
        this.beneficiario = beneficiario;
    }

    public LocalDateTime getDataInclusao() {
        return dataInclusao;
    }

    public void setDataInclusao(LocalDateTime dataInclusao) {
        this.dataInclusao = dataInclusao;
    }

    public Usuario getUsuarioInclusao() {
        return usuarioInclusao;
    }

    public void setUsuarioInclusao(Usuario usuarioInclusao) {
        this.usuarioInclusao = usuarioInclusao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public SituacaoAtendimento getSituacaoAtendimento() {
        return situacaoAtendimento;
    }

    public void setSituacaoAtendimento(SituacaoAtendimento situacaoAtendimento) {
        this.situacaoAtendimento = situacaoAtendimento;
    }
}
