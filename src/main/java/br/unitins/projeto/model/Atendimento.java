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

    @Column(name = "data_cadastro", nullable = false)
    private LocalDateTime dataCadastro;

    @ManyToOne
    @JoinColumn(name = "id_usuario_inclusao", nullable = false)
    private Usuario usuarioInclusao;

    @Column(name = "descricao", nullable = false, length = 900)
    private String descricao;

    @Column(name = "tipo_beneficio", nullable = false)
    private String tipoBeneficio;

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

    public LocalDateTime getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(LocalDateTime dataCadastro) {
        this.dataCadastro = dataCadastro;
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

    public String getTipoBeneficio() {
        return tipoBeneficio;
    }

    public void setTipoBeneficio(String tipoBeneficio) {
        this.tipoBeneficio = tipoBeneficio;
    }

    public SituacaoAtendimento getSituacaoAtendimento() {
        return situacaoAtendimento;
    }

    public void setSituacaoAtendimento(SituacaoAtendimento situacaoAtendimento) {
        this.situacaoAtendimento = situacaoAtendimento;
    }
}
