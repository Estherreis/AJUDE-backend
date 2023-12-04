package br.unitins.projeto.form;

import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.core.MediaType;
import org.jboss.resteasy.annotations.providers.multipart.PartType;

public class DocumentoForm {

    @FormParam("id")
    @PartType(MediaType.TEXT_PLAIN)
    private Long id;

    @FormParam("nomeDocumento")
    @PartType(MediaType.TEXT_PLAIN)
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
