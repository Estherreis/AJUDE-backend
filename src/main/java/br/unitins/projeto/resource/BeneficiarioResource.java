package br.unitins.projeto.resource;

import java.util.List;

import jakarta.annotation.security.RolesAllowed;
import org.jboss.logging.Logger;

import br.unitins.projeto.application.Result;
import br.unitins.projeto.dto.beneficiario.BeneficiarioDTO;
import br.unitins.projeto.dto.beneficiario.BeneficiarioResponseDTO;
import br.unitins.projeto.service.beneficiario.BeneficiarioService;
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

@Path("/beneficiarios")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class BeneficiarioResource {
    

    @Inject
    BeneficiarioService service;

    private static final Logger LOG = Logger.getLogger(BeneficiarioResource.class);

    @GET
    //@RolesAllowed({"Administrador", "Assistente"})
    public List<BeneficiarioResponseDTO> getAll() {
        LOG.info("Buscando todos os beneficiarios.");
        return service.getAll();
    }

    @GET
    @Path("/{id}")
    //@RolesAllowed({"Administrador", "Assistente"})
    public BeneficiarioResponseDTO findById(@PathParam("id") Long id) {
        LOG.info("Buscando um beneficiarios pelo id.");
        return service.findById(id);
    }
    @GET
    @Path("/count")
    //@RolesAllowed({"Administrador", "Assistente"})
    public Long count() {
        LOG.info("Buscando a quantidade de beneficiarios.");
        return service.count();
    }

    @POST
    //@RolesAllowed({"Administrador", "Assistente"})
    public Response insert(BeneficiarioDTO dto) {
        LOG.infof("Inserindo um beneficiarios: %s", dto.nome());

        BeneficiarioResponseDTO response = service.create(dto);
        LOG.infof("Beneficiario (%d) criado com sucesso.", response.id());
        return Response.status(Status.CREATED).entity(response).build();
    }

    @PUT
    @Path("/{id}")
    //@RolesAllowed({"Administrador", "Assistente"})
    public Response update(@PathParam("id") Long id, BeneficiarioDTO dto) {
        LOG.infof("Alterando um beneficiarios: %s", dto.nome());

        BeneficiarioResponseDTO response = service.update(id, dto);
        LOG.infof("Beneficiario (%d) alterado com sucesso.", response.id());
        return Response.ok(response).build();
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed({"Administrador", "Assistente"})
    public Response delete(@PathParam("id") Long id) {
        LOG.infof("Deletando um beneficiarios: %s", id);
        Result result = null;

        try {
            service.delete(id);
            LOG.infof("Beneficiario (%d) deletado com sucesso.", id);
            return Response.status(Status.NO_CONTENT).build();
        } catch (ConstraintViolationException e) {
            LOG.error("Erro ao deletar um beneficiario.");
            LOG.debug(e.getMessage());
            result = new Result(e.getConstraintViolations());
        } catch (Exception e) {
            LOG.fatal("Erro sem identificacao: " + e.getMessage());
            result = new Result(e.getMessage(), false);
        }

        return Response.status(Status.NOT_FOUND).entity(result).build();
    }

    @GET
    @Path("/search/{nomeOuCpf}")
    public Response search(@PathParam("nomeOuCpf") String nomeOuCpf,
            @QueryParam("page") @DefaultValue("0") int page,
            @QueryParam("pageSize") @DefaultValue("10") int pageSize) {
        LOG.infof("Pesquisando estados pelo nome/cpf: %s", nomeOuCpf );
        Result result = null;

        try {
            List<BeneficiarioResponseDTO> response = service.findByNomeOuCpf(nomeOuCpf, page, pageSize);
            LOG.infof("Pesquisa realizada com sucesso.");
            return Response.ok(response).build();
        } catch (ConstraintViolationException e) {
            LOG.error("Erro ao pesquisar beneficiarios.");
            LOG.debug(e.getMessage());
            result = new Result(e.getConstraintViolations());
        } catch (Exception e) {
            LOG.fatal("Erro sem identificacao: " + e.getMessage());
            result = new Result(e.getMessage(), false);
        }

        return Response.status(Status.NOT_FOUND).entity(result).build();
    }

    @GET
    @Path("/search/{nomeOuCpf}/count")
    public Long count(@PathParam("nomeOuCpf") String nomeOuCpf) {
        return service.countByNomeOuCpf(nomeOuCpf);
    }
}