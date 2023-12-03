package br.unitins.projeto.resource;

import br.unitins.projeto.application.Result;
import br.unitins.projeto.dto.atendimento.AtendimentoDTO;
import br.unitins.projeto.dto.atendimento.AtendimentoResponseDTO;
import br.unitins.projeto.model.SituacaoAtendimento;
import br.unitins.projeto.service.atendimento.AtendimentoService;
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
import org.jboss.logging.Logger;

import java.util.List;

@Path("/atendimentos")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AtendimentoResource {

    @Inject
    AtendimentoService service;

    private static final Logger LOG = Logger.getLogger(AtendimentoResource.class);

    @GET
    @Path("/{id}")
//    @RolesAllowed({"Administrador", "Assistente"})
    public AtendimentoResponseDTO findById(@PathParam("id") Long id) {
        LOG.info("Buscando um atendimento pelo id.");
        return service.findById(id);
    }

    @GET
    @Path("/beneficiario/{idBeneficiario}")
//    @RolesAllowed({"Administrador", "Assistente"})
    public List<AtendimentoResponseDTO> findByBeneficiario(@PathParam("idBeneficiario") Long idBeneficiario,
                                                           @QueryParam("page") @DefaultValue("0") int page,
                                                           @QueryParam("pageSize") @DefaultValue("10") int pageSize) {
        LOG.info("Buscando os atendimentos de um beneficiário.");
        return service.findByBeneficiario(idBeneficiario, page, pageSize);
    }

    @POST
//    @RolesAllowed({"Administrador", "Assistente"})
    public Response insert(AtendimentoDTO dto) {
        LOG.infof("Inserindo um atendimento: %s", dto.descricao());

        AtendimentoResponseDTO response = service.create(dto);
        LOG.infof("Atendimento (%d) criado com sucesso.", response.id());
        return Response.status(Response.Status.CREATED).entity(response).build();
    }

    @PUT
    @Path("/situacao/{id}")
//    @RolesAllowed({"Administrador", "Assistente"})
    public Response finalizarAtendimento(@PathParam("id") Long id) {
        LOG.infof("Finalizando atendimento");
        Result result = null;

        try {
            AtendimentoResponseDTO response = service.finalizarAtendimento(id);
            LOG.infof("Atendimento (%d) finalizado com sucesso.", response.id());
            return Response.ok(response).build();
        } catch (ConstraintViolationException e) {
            LOG.error("Erro ao finalizar um atendimento.");
            LOG.debug(e.getMessage());
            result = new Result(e.getConstraintViolations());
        } catch (Exception e) {
            LOG.fatal("Erro sem identificacao: " + e.getMessage());
            result = new Result(e.getMessage(), false);
        }

        return Response.status(Response.Status.NOT_FOUND).entity(result).build();
    }

    @GET
    @Path("/situacoes-atendimento")
    public Response getSituacaoesAtendimento() {
        return Response.ok(SituacaoAtendimento.values()).build();
    }

    @GET
    @Path("/beneficiario/{idBeneficiario}/count")
    public Long count(@PathParam("idBeneficiario") Long idBeneficiario) {
        return service.countByBeneficiario(idBeneficiario);
    }

}
