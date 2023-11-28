package br.unitins.projeto.form;

import org.jboss.resteasy.annotations.providers.multipart.PartType;

import jakarta.ws.rs.FormParam;

public class DocumentoForm {
    
    @FormParam("id")
    private Long id;

    @FormParam("nomeDocumento")
    private String nomeDocumento;

    @FormParam("documento")
    @PartType("application/octet-stream")
    private byte[] documento;

    
    public String getNomeDocumento() {
        return nomeDocumento;
    }

    public void setNomeDocumento(String nomeDocumento) {
        this.nomeDocumento = nomeDocumento;
    }

    public byte[] getDocumento() {
        return documento;
    }

    public void setDocumento(byte[] documento) {
        this.documento = documento;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


}
