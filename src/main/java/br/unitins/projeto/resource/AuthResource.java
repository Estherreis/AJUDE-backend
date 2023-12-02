package br.unitins.projeto.resource;

import br.unitins.projeto.dto.auth_usuario.AuthUsuarioDTO;
import br.unitins.projeto.dto.usuario.OrgaoPerfilDTO;
import br.unitins.projeto.dto.usuario.OrgaoPerfilResponseDTO;
import br.unitins.projeto.model.Perfil;
import br.unitins.projeto.model.Usuario;
import br.unitins.projeto.repository.OrgaoRepository;
import br.unitins.projeto.service.hash.HashService;
import br.unitins.projeto.service.token_jwt.TokenJwtService;
import br.unitins.projeto.service.usuario.UsuarioService;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.jboss.logging.Logger;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Path("/auth")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AuthResource {

    @Inject
    OrgaoRepository orgaoRepository;

    @Inject
    HashService hashService;

    @Inject
    UsuarioService usuarioService;

    @Inject
    TokenJwtService tokenService;

    @Inject
    JsonWebToken jwt;

    private static final Logger LOG = Logger.getLogger(AuthResource.class);

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @PermitAll
    public Response login(@Valid AuthUsuarioDTO authDTO) {
        LOG.infof("Fazendo login de usuário: %s", authDTO.login());
        String hash = hashService.getHashSenha(authDTO.senha());

        Usuario usuario = usuarioService.findByLoginAndSenha(authDTO.login(), hash);

        if (usuario == null) {
            return Response.status(Status.NO_CONTENT)
                    .entity("Usuario não encontrado").build();
        } else {
            usuario.setPerfil(new HashSet<>(Set.of(Perfil.LOGADO)));
        }

        return Response.ok()
                .header("Authorization", tokenService.generateJwt(usuario))
                .build();
    }

    @GET
    @Path("/lotacoes")
    @RolesAllowed({"Logado"})
    public List<OrgaoPerfilResponseDTO> getLotacoes() {
        String login = jwt.getSubject();
        Usuario usuario = usuarioService.findByLogin(login);
        return usuarioService.getLotacoesUsuario(usuario.getId());
    }

    @POST
    @Path("/lotacoes")
    @RolesAllowed({"Logado"})
    public Response setLotacao(OrgaoPerfilDTO orgaoPerfil) {
        String login = jwt.getSubject();
        Usuario usuario = usuarioService.findByLogin(login);

        Set<Perfil> perfil = Collections.singleton(Perfil.valueOf(orgaoPerfil.idPerfil()));

        return Response.ok()
                .header("Authorization", tokenService.generateJwt(
                        orgaoRepository.findById(orgaoPerfil.idOrgao()),
                        perfil,
                        usuario))
                .build();
    }
}
