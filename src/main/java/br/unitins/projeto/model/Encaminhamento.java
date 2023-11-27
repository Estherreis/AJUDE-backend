package br.unitins.projeto.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;

import java.time.LocalDateTime;

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class Encaminhamento extends DefaultEntity {
    @ManyToOne
    @JoinColumn(name = "id_orgao_atual", nullable = false)
    private Orgao orgaoAtual;

    @ManyToOne
    @JoinColumn(name = "id_orgao_destino", nullable = false)
    private Orgao orgaoDestino;

    @ManyToOne
    @JoinColumn(name = "id_atendimento", nullable = false)
    private Atendimento atendimento;

    @Column(name = "data_inclusao", nullable = false)
    private LocalDateTime dataInclusao;

    @ManyToOne
    @JoinColumn(name = "id_usuario_inclusao", nullable = false)
    private Usuario usuarioInclusao;

    @Column(name = "motivo", nullable = false)
    private String motivo;

    public Orgao getOrgaoAtual() {
        return orgaoAtual;
    }

    public void setOrgaoAtual(Orgao orgaoAtual) {
        this.orgaoAtual = orgaoAtual;
    }

    public Orgao getOrgaoDestino() {
        return orgaoDestino;
    }

    public void setOrgaoDestino(Orgao orgaoDestino) {
        this.orgaoDestino = orgaoDestino;
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

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public Atendimento getAtendimento() {
        return atendimento;
    }

    public void setAtendimento(Atendimento atendimento) {
        this.atendimento = atendimento;
    }
}
