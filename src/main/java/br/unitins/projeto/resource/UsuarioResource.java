package br.unitins.projeto.resource;

import br.unitins.projeto.application.Result;
import br.unitins.projeto.dto.usuario.UsuarioDTO;
import br.unitins.projeto.dto.usuario.UsuarioLotacoesResponseDTO;
import br.unitins.projeto.dto.usuario.UsuarioResponseDTO;
import br.unitins.projeto.service.usuario.UsuarioService;
import jakarta.inject.Inject;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.Consumes;
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

@Path("/usuarios")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UsuarioResource {

    @Inject
    UsuarioService service;

    private static final Logger LOG = Logger.getLogger(UsuarioResource.class);

    @GET
    // @RolesAllowed({"Administrador", "Assistente"})
    public List<UsuarioResponseDTO> getAll() {
        LOG.info("Buscando todos os usuários.");
        return service.getAll();
    }

    @GET()
    @Path("/lotacoes")
    // @RolesAllowed({"Administrador", "Assistente"})
    public List<UsuarioLotacoesResponseDTO> getAll(@QueryParam("page") @DefaultValue("0") int page,
            @QueryParam("pageSize") @DefaultValue("10") int pageSize) {
        LOG.info("Buscando todos os usuários.");
        return service.getAllUsuariosLotacoes(page, pageSize);
    }

    @GET
    @Path("/count")
    public Long count() {
        return service.count();
    }

    @GET
    @Path("/{id}")
    /// @RolesAllowed({"Administrador", "Assistente"})
    public UsuarioResponseDTO findById(@PathParam("id") Long id) {
        LOG.info("Buscando um usuário pelo id.");
        return service.findById(id);
    }

    @GET
    @Path("/lotacoes/{id}")
    /// @RolesAllowed({"Administrador", "Assistente"})
    public UsuarioLotacoesResponseDTO finWithLotacoesById(@PathParam("id") Long id) {
        LOG.info("Buscando um usuário com as lotações pelo id.");
        return service.findUsuarioLotacaoById(id);
    }

    @POST
    // @RolesAllowed({"Administrador"})
    public Response insert(UsuarioDTO dto) {
        LOG.infof("Inserindo um usuário: %s", dto.nome());
        Result result = null;

        try {
            UsuarioResponseDTO response = service.create(dto);
            LOG.infof("Usuário (%d) criado com sucesso.", response.id());
            return Response.status(Response.Status.CREATED).entity(response).build();
        } catch (ConstraintViolationException e) {
            LOG.error("Erro ao incluir um usuário.");
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
    // @RolesAllowed({"Administrador", "Assistente"})
    public Response update(@PathParam("id") Long id, UsuarioDTO dto) {
        LOG.infof("Alterando um usuário: %s", dto.nome());
        Result result = null;

        try {
            UsuarioResponseDTO response = service.update(id, dto);
            LOG.infof("Usuário (%d) alterado com sucesso.", response.id());
            return Response.ok(response).build();
        } catch (ConstraintViolationException e) {
            LOG.error("Erro ao alterar um usuário.");
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
    // @RolesAllowed({"Administrador", "Assistente"})
    public Response alterarSituacao(@PathParam("id") Long id, Boolean situacao) {
        LOG.infof("Alterando situação do usuário");
        Result result = null;

        try {
            UsuarioResponseDTO response = service.alterarSituacao(id, situacao);
            LOG.infof("Usuário (%d) alterado com sucesso.", response.id());
            return Response.ok(response).build();
        } catch (ConstraintViolationException e) {
            LOG.error("Erro ao alterar um usuário.");
            LOG.debug(e.getMessage());
            result = new Result(e.getConstraintViolations());
        } catch (Exception e) {
            LOG.fatal("Erro sem identificacao: " + e.getMessage());
            result = new Result(e.getMessage(), false);
        }

        return Response.status(Response.Status.NOT_FOUND).entity(result).build();
    }

    @GET
    @Path("/search/{nomeOuCpf}")
    public Response search(@PathParam("nomeOuCpf") String nomeOuCpf,
            @QueryParam("page") @DefaultValue("0") int page,
            @QueryParam("pageSize") @DefaultValue("10") int pageSize) {
        LOG.infof("Pesquisando estados pelo nome/sigla: %s", nomeOuCpf);
        Result result = null;

        try {
            List<UsuarioLotacoesResponseDTO> response = service.findByNomeOuCpf(nomeOuCpf, page, pageSize);
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
    @Path("/search/{nomeOuCpf}/count")
    public Long count(@PathParam("nomeOuCpf") String nomeOuCpf) {
        return service.countByNomeOuCpf(nomeOuCpf);
    }

}
