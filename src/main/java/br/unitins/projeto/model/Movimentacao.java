package br.unitins.projeto.model;


import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class Movimentacao extends DefaultEntity{
    
    private String tituloMovimentacao;

    @ManyToOne
    private Usuario usuarioAutor;

    private String descricao;
    
    private String nomeDocumento;

    //data inclusão já está no default entity

    @ManyToOne
    @JoinColumn(name = "id_atendimento", nullable = false)
    private Atendimento atendimento;

    public Atendimento getAtendimento() {
        return atendimento;
    }

    public void setAtendimento(Atendimento atendimento) {
        this.atendimento = atendimento;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getNomeDocumento() {
        return nomeDocumento;
    }

    public void setNomeDocumento(String nomeDocumento) {
        this.nomeDocumento = nomeDocumento;
    }

    
    public Usuario getUsuarioAutor() {
        return usuarioAutor;
    }

    public void setUsuarioAutor(Usuario usuarioAutor) {
        this.usuarioAutor = usuarioAutor;
    }


        public String getTituloMovimentacao() {
        return tituloMovimentacao;
    }

    public void setTituloMovimentacao(String tituloMovimentacao) {
        this.tituloMovimentacao = tituloMovimentacao;
    }

}
