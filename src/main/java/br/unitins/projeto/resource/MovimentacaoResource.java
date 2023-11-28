package br.unitins.projeto.resource;

import java.io.IOException;
import java.util.List;

import jakarta.annotation.security.RolesAllowed;
import org.jboss.logging.Logger;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import br.unitins.projeto.application.Result;
import br.unitins.projeto.dto.movimentacao.MovimentacaoResponseDTO;
import br.unitins.projeto.form.DocumentoForm;
import br.unitins.projeto.dto.movimentacao.MovimentacaoDTO;
import br.unitins.projeto.service.file.FileService;
import br.unitins.projeto.service.movimentacao.MovimentacaoService;
import jakarta.inject.Inject;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.ResponseBuilder;
import jakarta.ws.rs.core.Response.Status;

@Path("/movimentacoes")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MovimentacaoResource {

    private static final Logger LOG = Logger.getLogger(MovimentacaoResource.class);

    @Inject
    MovimentacaoService movimentacaoService;

    @Inject
    FileService fileService;

    @POST
    @RolesAllowed({"Administrador", "Assistente"})
    public Response insert(MovimentacaoDTO dto) {
        LOG.infof("Inserindo um movimentacaos: %s", dto.descricao());
        Result result = null;

        try {
            MovimentacaoResponseDTO response = movimentacaoService.lancarMovimentacao(dto);
            LOG.infof("Movimentacao (%d) criada com sucesso.", response.id());
            return Response.status(Status.CREATED).entity(response).build();
        } catch (ConstraintViolationException e) {
            LOG.error("Erro ao incluir uma movimentacao.");
            LOG.debug(e.getMessage());
            result = new Result(e.getConstraintViolations());
        } catch (Exception e) {
            LOG.fatal("Erro sem identificacao: " + e.getMessage());
            result = new Result(e.getMessage(), false);
        }

        return Response.status(Status.NOT_FOUND).entity(result).build();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({"Administrador", "Assistente"})
    public List<MovimentacaoResponseDTO> findById(@PathParam("id") Long id) {

        LOG.info("Buscando movimentações pelo id do atendimento.");
        return movimentacaoService.listarPorAtendimento(id);
    }

    @GET
    @Path("/document/download/{nomeDocumento}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    @RolesAllowed({"Administrador", "Assistente"})
    public Response download(@PathParam("nomeDocumento") String nomeDocumento) {
        ResponseBuilder response = Response.ok(fileService.download(nomeDocumento));
        response.header("Content-Disposition", "attachment;filename=" + nomeDocumento);
        return response.build();
    }

    @PATCH
    @Path("/documento/upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @RolesAllowed({"Administrador", "Assistente"})
    public Response salvarImagem(@MultipartForm DocumentoForm form) {

        try {
            fileService.salvar(form.getId(), form.getNomeDocumento(), form.getDocumento());
            return Response.noContent().build();
        } catch (IOException e) {
            Result result = new Result(e.getMessage());
            return Response.status(Status.CONFLICT).entity(result).build();
        }
    }
}
