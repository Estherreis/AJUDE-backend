package br.unitins.projeto.resource;

import br.unitins.projeto.application.Result;
import br.unitins.projeto.dto.orgao.OrgaoDTO;
import br.unitins.projeto.dto.orgao.OrgaoResponseDTO;
import br.unitins.projeto.service.orgao.OrgaoService;
import jakarta.inject.Inject;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

import org.jboss.logging.Logger;

import java.util.List;

@Path("/orgaos")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class OrgaoResource {

    @Inject
    OrgaoService service;

    private static final Logger LOG = Logger.getLogger(OrgaoResource.class);

    @GET
    @Path("/all")
    public List<OrgaoResponseDTO> getAll() {
        LOG.info("Buscando todos os órgãos.");
        return service.getAll();
    }

    @GET
    public List<OrgaoResponseDTO> getAll(
            @QueryParam("page") @DefaultValue("0") int page,
            @QueryParam("pageSize") @DefaultValue("10") int pageSize) {

        LOG.info("Buscando todos os órgãos com paginação.");
        LOG.debug("ERRO DE DEBUG.");
        return service.getAll(page, pageSize);
    }

    @GET
    @Path("/{id}")
    public OrgaoResponseDTO findById(@PathParam("id") Long id) {
        LOG.info("Buscando um órgão pelo id.");
        return service.findById(id);
    }

    @POST
    public Response insert(OrgaoDTO dto) {
        LOG.infof("Inserindo um órgão: %s", dto.nome());
        Result result = null;

        try {
            OrgaoResponseDTO response = service.create(dto);
            LOG.infof("Órgão (%d) criado com sucesso.", response.id());
            return Response.status(Response.Status.CREATED).entity(response).build();
        } catch (ConstraintViolationException e) {
            LOG.error("Erro ao incluir um órgão.");
            LOG.debug(e.getMessage());
            result = new Result(e.getConstraintViolations());
        } catch (Exception e) {
            LOG.fatal("Erro sem identificacao: " + e.getMessage());
            result = new Result(e.getMessage(), false);
        }

        return Response.status(Response.Status.NOT_FOUND).entity(result).build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, OrgaoDTO dto) {
        LOG.infof("Alterando um órgão: %s", dto.nome());
        Result result = null;

        try {
            OrgaoResponseDTO response = service.update(id, dto);
            LOG.infof("Órgão (%d) alterado com sucesso.", response.id());
            return Response.ok(response).build();
        } catch (ConstraintViolationException e) {
            LOG.error("Erro ao alterar um órgão.");
            LOG.debug(e.getMessage());
            result = new Result(e.getConstraintViolations());
        } catch (Exception e) {
            LOG.fatal("Erro sem identificacao: " + e.getMessage());
            result = new Result(e.getMessage(), false);
        }

        return Response.status(Response.Status.NOT_FOUND).entity(result).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        LOG.infof("Deletando um órgão: %s", id);
        Result result = null;

        try {
            service.delete(id);
            LOG.infof("Órgão (%d) deletado com sucesso.", id);
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (ConstraintViolationException e) {
            LOG.error("Erro ao deletar um órgão.");
            LOG.debug(e.getMessage());
            result = new Result(e.getConstraintViolations());
        } catch (Exception e) {
            LOG.fatal("Erro sem identificacao: " + e.getMessage());
            result = new Result(e.getMessage(), false);
        }

        return Response.status(Response.Status.NOT_FOUND).entity(result).build();
    }

    @PUT
    @Path("/situacao/{id}")
    public Response alterarSituacao(@PathParam("id") Long id, Boolean situacao) {
        LOG.infof("Alterando situação do órgão");
        Result result = null;

        try {
            OrgaoResponseDTO response = service.alterarSituacao(id, situacao);
            LOG.infof("Órgão (%d) alterado com sucesso.", response.id());
            return Response.ok(response).build();
        } catch (ConstraintViolationException e) {
            LOG.error("Erro ao alterar um órgão.");
            LOG.debug(e.getMessage());
            result = new Result(e.getConstraintViolations());
        } catch (Exception e) {
            LOG.fatal("Erro sem identificacao: " + e.getMessage());
            result = new Result(e.getMessage(), false);
        }

        return Response.status(Response.Status.NOT_FOUND).entity(result).build();
    }

    @GET
    @Path("/count")
    public Long count() {
        return service.count();
    }

    @GET
    @Path("/search/{nomeOuSigla}")
    public Response search(@PathParam("nomeOuSigla") String nomeOuSigla,
            @QueryParam("page") @DefaultValue("0") int page,
            @QueryParam("pageSize") @DefaultValue("10") int pageSize) {
        LOG.infof("Pesquisando estados pelo nome/sigla: %s", nomeOuSigla);
        Result result = null;

        try {
            List<OrgaoResponseDTO> response = service.findByNomeOuSigla(nomeOuSigla, page, pageSize);
            LOG.infof("Pesquisa realizada com sucesso.");
            return Response.ok(response).build();
        } catch (ConstraintViolationException e) {
            LOG.error("Erro ao pesquisar estados.");
            LOG.debug(e.getMessage());
            result = new Result(e.getConstraintViolations());
        } catch (Exception e) {
            LOG.fatal("Erro sem identificacao: " + e.getMessage());
            result = new Result(e.getMessage(), false);
        }

        return Response.status(Status.NOT_FOUND).entity(result).build();
    }

    @GET
    @Path("/search/{nomeOuSigla}/count")
    public Long count(@PathParam("nomeOuSigla") String nomeOuSigla) {
        return service.countByNomeOuSigla(nomeOuSigla);
    }

}
