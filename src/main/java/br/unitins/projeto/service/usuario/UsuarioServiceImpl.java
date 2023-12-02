package br.unitins.projeto.service.usuario;

import br.unitins.projeto.dto.orgao.OrgaoDTO;
import br.unitins.projeto.dto.orgao.OrgaoResponseDTO;
import br.unitins.projeto.dto.usuario.OrgaoPerfilDTO;
import br.unitins.projeto.dto.usuario.OrgaoPerfilResponseDTO;
import br.unitins.projeto.dto.usuario.UsuarioDTO;
import br.unitins.projeto.dto.usuario.UsuarioResponseDTO;
import br.unitins.projeto.model.Orgao;
import br.unitins.projeto.model.OrgaoPerfil;
import br.unitins.projeto.model.Perfil;
import br.unitins.projeto.model.Usuario;
import br.unitins.projeto.repository.OrgaoPerfilRepository;
import br.unitins.projeto.repository.OrgaoRepository;
import br.unitins.projeto.repository.UsuarioRepository;
import br.unitins.projeto.service.hash.HashService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import jakarta.ws.rs.NotFoundException;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@ApplicationScoped
public class UsuarioServiceImpl implements UsuarioService {

    @Inject
    UsuarioRepository repository;

    @Inject
    OrgaoRepository orgaoRepository;

    @Inject
    OrgaoPerfilRepository orgaoPerfilRepository;

    @Inject
    Validator validator;

    @Inject
    HashService hashService;

    @Override
    public List<UsuarioResponseDTO> getAll() {
        List<Usuario> list = repository.listAll();
        return list.stream().map(UsuarioResponseDTO::new).collect(Collectors.toList());
    }

    @Override
    public UsuarioResponseDTO findById(Long id) {
        Usuario usuario = repository.findById(id);

        if (usuario == null)
            throw new NotFoundException("Usuário não encontrado.");

        return new UsuarioResponseDTO(usuario);
    }

    @Override
    @Transactional
    public UsuarioResponseDTO create(UsuarioDTO usuarioDTO) throws ConstraintViolationException {
        validar(usuarioDTO);

        Usuario entity = new Usuario();
        entity.setNome(usuarioDTO.nome());
        entity.setCpf(usuarioDTO.cpf());
        entity.setLogin(usuarioDTO.login());
        entity.setSenha(hashService.getHashSenha(usuarioDTO.senha()));
        entity.setNivelSigilo(usuarioDTO.nivelSigilo());
        entity.setAtivo(true);
        repository.persist(entity);

        OrgaoPerfil orgaoPerfil = new OrgaoPerfil();
        orgaoPerfil.setOrgao(orgaoRepository.findById(usuarioDTO.orgaoPerfil().idOrgao()));
        orgaoPerfil.setPerfil(Perfil.valueOf(usuarioDTO.orgaoPerfil().idPerfil()));
        orgaoPerfil.setUsuario(entity);
        orgaoPerfilRepository.persist(orgaoPerfil);

        return new UsuarioResponseDTO(entity);
    }

    @Override
    @Transactional
    public UsuarioResponseDTO update(Long id, UsuarioDTO usuarioDTO) throws ConstraintViolationException {
        validar(usuarioDTO);

        Usuario entity = repository.findById(id);

        entity.setNome(usuarioDTO.nome());
        entity.setCpf(usuarioDTO.cpf());
        entity.setLogin(usuarioDTO.login());
        entity.setSenha(hashService.getHashSenha(usuarioDTO.senha()));
        entity.setNivelSigilo(usuarioDTO.nivelSigilo());

        return new UsuarioResponseDTO(entity);
    }

    @Override
    @Transactional
    public UsuarioResponseDTO adicionarUsuario(Long id, OrgaoPerfilDTO orgaoPerfilDTO) throws ConstraintViolationException {

        Usuario entity = repository.findById(id);

        OrgaoPerfil orgaoPerfil = new OrgaoPerfil();
        orgaoPerfil.setOrgao(orgaoRepository.findById(orgaoPerfilDTO.idOrgao()));
        orgaoPerfil.setPerfil(Perfil.valueOf(orgaoPerfilDTO.idPerfil()));
        orgaoPerfil.setUsuario(entity);
        orgaoPerfilRepository.persist(orgaoPerfil);

        return new UsuarioResponseDTO(entity);
    }

    private void validar(UsuarioDTO usuarioDTO) throws ConstraintViolationException {
        Set<ConstraintViolation<UsuarioDTO>> violations = validator.validate(usuarioDTO);

        if (!violations.isEmpty())
            throw new ConstraintViolationException(violations);
    }

    public List<OrgaoPerfilResponseDTO> getLotacoesUsuario(Long id) {
        return orgaoPerfilRepository.findByUsuario(id).stream().map(OrgaoPerfilResponseDTO::new).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public UsuarioResponseDTO alterarSituacao(Long id, Boolean situacao) {
        Usuario entity = repository.findById(id);
        entity.setAtivo(situacao);

        return new UsuarioResponseDTO(entity);
    }

    @Override
    public Usuario findByLoginAndSenha(String login, String senha) {
        return repository.findByLoginAndSenha(login, senha);
    }

    @Override
    public Usuario findByLogin(String login) {
        return repository.findByLogin(login);
    }

}
