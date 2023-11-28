package br.unitins.projeto.resource;

import br.unitins.projeto.application.Result;
import br.unitins.projeto.dto.encaminhamento.EncaminhamentoDTO;
import br.unitins.projeto.dto.encaminhamento.EncaminhamentoResponseDTO;
import br.unitins.projeto.service.encaminhamento.EncaminhamentoService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.logging.Logger;

import java.util.List;

@Path("/encaminhamentos")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class EncaminhamentoResource {

    @Inject
    EncaminhamentoService service;

    private static final Logger LOG = Logger.getLogger(EncaminhamentoResource.class);

    @GET
    @Path("/{id}")
    @RolesAllowed({"Administrador", "Assistente"})
    public EncaminhamentoResponseDTO findById(@PathParam("id") Long id) {
        LOG.info("Buscando um atendimento pelo id.");
        return service.findById(id);
    }

    @GET
    @Path("/{idAtendimento}")
    @RolesAllowed({"Administrador", "Assistente"})
    public List<EncaminhamentoResponseDTO> findByAtendimento(@PathParam("idAtendimento") Long idAtendimento) {
        LOG.info("Buscando os encaminhamentos de um atendimento.");
        return service.findByAtendimento(idAtendimento);
    }

    @POST
    @RolesAllowed({"Administrador", "Assistente"})
    public Response insert(EncaminhamentoDTO dto) {
        LOG.infof("Inserindo um encaminhamento: %s", dto.motivo());
        Result result = null;

        try {
            EncaminhamentoResponseDTO response = service.create(dto);
            LOG.infof("Encaminhamento (%d) criado com sucesso.", response.id());
            return Response.status(Response.Status.CREATED).entity(response).build();
        } catch (ConstraintViolationException e) {
            LOG.error("Erro ao incluir um encaminhamento.");
            LOG.debug(e.getMessage());
            result = new Result(e.getConstraintViolations());
        } catch (Exception e) {
            LOG.fatal("Erro sem identificacao: " + e.getMessage());
            result = new Result(e.getMessage(), false);
        }

        return Response.status(Response.Status.NOT_FOUND).entity(result).build();
    }

}
