package br.unitins.projeto.resource;

import br.unitins.projeto.application.Result;
import br.unitins.projeto.dto.movimentacao.MovimentacaoDTO;
import br.unitins.projeto.dto.movimentacao.MovimentacaoResponseDTO;
import br.unitins.projeto.form.DocumentoForm;
import br.unitins.projeto.service.file.FileService;
import br.unitins.projeto.service.movimentacao.MovimentacaoService;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.ResponseBuilder;
import jakarta.ws.rs.core.Response.Status;
import org.jboss.logging.Logger;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import java.io.IOException;
import java.util.List;

@Path("/movimentacoes")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MovimentacaoResource {

    private static final Logger LOG = Logger.getLogger(MovimentacaoResource.class);

    @Inject
    MovimentacaoService movimentacaoService;

    @Inject
    FileService fileService;

    @GET
    @Path("/atendimento/{idAtendimento}")
    //@RolesAllowed({"Administrador", "Assistente"})
    public List<MovimentacaoResponseDTO> findByAtendimento(@PathParam("idAtendimento") Long idAtendimento,
                                                           @QueryParam("page") @DefaultValue("0") int page,
                                                           @QueryParam("pageSize") @DefaultValue("10") int pageSize) {
        LOG.info("Buscando os encaminhamentos de um atendimento.");
        return movimentacaoService.findByAtendimento(idAtendimento, page, pageSize);
    }

    @POST
    //@RolesAllowed({"Administrador", "Assistente"})
    public Response insert(MovimentacaoDTO dto) {
        LOG.infof("Inserindo um movimentacaos: %s", dto.tituloMovimentacao());

        MovimentacaoResponseDTO response = movimentacaoService.lancarMovimentacao(dto);
        LOG.infof("Movimentacao (%d) criada com sucesso.", response.id());
        return Response.status(Status.CREATED).entity(response).build();
    }

    @GET
    @Path("/{id}")
    //@RolesAllowed({"Administrador", "Assistente"})
    public MovimentacaoResponseDTO findById(@PathParam("id") Long id) {
        LOG.info("Buscando movimentações pelo id do atendimento.");
        return movimentacaoService.findById(id);
    }

    @GET
    @Path("/documento/download/{nomeDocumento}")
    //@RolesAllowed({"Administrador", "Assistente"})
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response download(@PathParam("nomeDocumento") String nomeDocumento) {
        ResponseBuilder response = Response.ok(fileService.download(nomeDocumento));
        response.header("Content-Disposition", "attachment;filename=" + nomeDocumento);
        return response.build();
    }

    @PATCH
    @Path("/documento/upload")
    //@RolesAllowed({"Administrador", "Assistente"})
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response salvarImagem(@MultipartForm DocumentoForm form) {

        try {
            fileService.salvar(form.getId(), form.getNomeDocumento(), form.getDocumento());
            return Response.noContent().build();
        } catch (IOException e) {
            Result result = new Result(e.getMessage());
            return Response.status(Status.CONFLICT).entity(result).build();
        }
    }

    @GET
    @Path("/atendimento/{idAtendimento}/count")
    public Long count(@PathParam("idAtendimento") Long idAtendimento) {
        return movimentacaoService.countByAtendimento(idAtendimento);
    }

}
