package br.unitins.projeto.resource;

import br.unitins.projeto.application.Result;
import br.unitins.projeto.dto.usuario.UsuarioDTO;
import br.unitins.projeto.dto.usuario.UsuarioResponseDTO;
import br.unitins.projeto.service.usuario.UsuarioService;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
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
    @RolesAllowed({"Administrador", "Assistente"})
    public List<UsuarioResponseDTO> getAll() {
        LOG.info("Buscando todos os usuários.");
        return service.getAll();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({"Administrador", "Assistente"})
    public UsuarioResponseDTO findById(@PathParam("id") Long id) {
        LOG.info("Buscando um usuário pelo id.");
        return service.findById(id);
    }

    @POST
    @PermitAll
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
    @RolesAllowed({"Administrador", "Assistente"})
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
    @RolesAllowed({"Administrador", "Assistente"})
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

}
