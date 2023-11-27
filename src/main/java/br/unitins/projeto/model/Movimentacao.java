package br.unitins.projeto.model;


import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Movimentacao extends DefaultEntity{
    
    private String tituloMovimentacao;

    @ManyToOne
    private Usuario usuarioAutor;

    private String descricao;
    
    private String nomeDocumento;

    //data inclusão já está no default entity

    @ManyToOne
    private Atendimento Atendimento;

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

    public Atendimento getAtendimento() {
        return Atendimento;
    }

    public void setAtendimento(Atendimento atendimento) {
        Atendimento = atendimento;
    }

        public String getTituloMovimentacao() {
        return tituloMovimentacao;
    }

    public void setTituloMovimentacao(String tituloMovimentacao) {
        this.tituloMovimentacao = tituloMovimentacao;
    }

}
