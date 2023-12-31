package br.unitins.projeto.service.usuario;

import br.unitins.projeto.dto.usuario.OrgaoPerfilDTO;
import br.unitins.projeto.dto.usuario.OrgaoPerfilResponseDTO;
import br.unitins.projeto.dto.usuario.UsuarioDTO;
import br.unitins.projeto.dto.usuario.UsuarioLotacoesResponseDTO;
import br.unitins.projeto.dto.usuario.UsuarioResponseDTO;
import br.unitins.projeto.dto.usuario.UsuarioUpdateDTO;
import br.unitins.projeto.model.OrgaoPerfil;
import br.unitins.projeto.model.Perfil;
import br.unitins.projeto.model.Usuario;
import br.unitins.projeto.repository.OrgaoPerfilRepository;
import br.unitins.projeto.repository.OrgaoRepository;
import br.unitins.projeto.repository.UsuarioRepository;
import br.unitins.projeto.service.hash.HashService;
import io.quarkus.panache.common.Page;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import jakarta.ws.rs.NotFoundException;

import java.util.ArrayList;
import java.util.Comparator;
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
    public UsuarioResponseDTO create(@Valid UsuarioDTO usuarioDTO) throws ConstraintViolationException {
        validar(usuarioDTO);

        Usuario entity = new Usuario();
        entity.setNome(usuarioDTO.nome());
        entity.setCpf(usuarioDTO.cpf());
        entity.setLogin(usuarioDTO.login());
        entity.setSenha(hashService.getHashSenha(usuarioDTO.senha()));
        entity.setNivelSigilo(usuarioDTO.nivelSigilo());
        entity.setAtivo(true);
        repository.persist(entity);

        if (usuarioDTO.orgaosPerfis() != null) {
            for (OrgaoPerfilDTO orgaoPerfilDTO : usuarioDTO.orgaosPerfis()) {
                OrgaoPerfil orgaoPerfil = new OrgaoPerfil();
                orgaoPerfil.setOrgao(orgaoRepository.findById(orgaoPerfilDTO.idOrgao()));
                orgaoPerfil.setPerfil(Perfil.valueOf(orgaoPerfilDTO.idPerfil()));
                orgaoPerfil.setUsuario(entity);
                orgaoPerfilRepository.persist(orgaoPerfil);
            }
        }

        return new UsuarioResponseDTO(entity);
    }

    @Override
    @Transactional
    public UsuarioResponseDTO update(Long id, @Valid UsuarioUpdateDTO usuarioDTO) throws ConstraintViolationException {
        validarUpdate(usuarioDTO);

        Usuario entity = repository.findById(id);

        entity.setNome(usuarioDTO.nome());
        entity.setCpf(usuarioDTO.cpf());
        entity.setLogin(usuarioDTO.login());

        if (usuarioDTO.senha() != null && usuarioDTO.senha() != "") {
            entity.setSenha(hashService.getHashSenha(usuarioDTO.senha()));
        }

        entity.setNivelSigilo(usuarioDTO.nivelSigilo());

        if (usuarioDTO.orgaosPerfis() != null) {
            List<OrgaoPerfil> lotacoes = this.orgaoPerfilRepository.findByUsuario(entity.getId());
            List<OrgaoPerfilDTO> orgaosPerfisDTO = usuarioDTO.orgaosPerfis();

            List<OrgaoPerfil> orgaoPerfilsToDelete = lotacoes.stream()
                    .filter(lotacao -> orgaosPerfisDTO.stream()
                            .noneMatch(dto -> dto.idPerfil().equals(lotacao.getPerfil().getId())
                                    && dto.idOrgao().equals(lotacao.getOrgao().getId())))
                    .collect(Collectors.toList());

            for (OrgaoPerfilDTO orgaoPerfilDTO : orgaosPerfisDTO) {
                OrgaoPerfil orgaoPerfilExistente = this.orgaoPerfilRepository.findByPerfilOrgaoUsuario(Perfil.valueOf(orgaoPerfilDTO.idPerfil()), orgaoPerfilDTO.idOrgao(), entity.getId());

                if (orgaoPerfilExistente == null) {
                    OrgaoPerfil orgaoPerfil = new OrgaoPerfil();
                    orgaoPerfil.setOrgao(orgaoRepository.findById(orgaoPerfilDTO.idOrgao()));
                    orgaoPerfil.setPerfil(Perfil.valueOf(orgaoPerfilDTO.idPerfil()));
                    orgaoPerfil.setUsuario(entity);
                    orgaoPerfilRepository.persist(orgaoPerfil);
                }
            }

            if (!orgaoPerfilsToDelete.isEmpty()) {
                for (OrgaoPerfil orgaoPerfil : orgaoPerfilsToDelete) {
                    this.orgaoPerfilRepository.delete(orgaoPerfil);
                }
            }
        }

        return new UsuarioResponseDTO(entity);
    }

    @Override
    @Transactional
    public UsuarioResponseDTO adicionarUsuario(Long id, @Valid OrgaoPerfilDTO orgaoPerfilDTO)
            throws ConstraintViolationException {

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

    private void validarUpdate(UsuarioUpdateDTO usuarioDTO) throws ConstraintViolationException {
        Set<ConstraintViolation<UsuarioUpdateDTO>> violations = validator.validate(usuarioDTO);

        if (!violations.isEmpty())
            throw new ConstraintViolationException(violations);
    }

    public List<OrgaoPerfilResponseDTO> getLotacoesUsuario(Long id) {
        return orgaoPerfilRepository.findByUsuario(id).stream().map(OrgaoPerfilResponseDTO::new)
                .collect(Collectors.toList());
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

    @Override
    public List<UsuarioLotacoesResponseDTO> getAllUsuariosLotacoes(int page, int pageSize) {
        List<UsuarioLotacoesResponseDTO> usuariosLotacao = new ArrayList<>();
        List<Usuario> usuarios = repository.findAll().page(page, pageSize).list().stream()
                .sorted(Comparator.comparing(Usuario::getNome))
                .collect(Collectors.toList());

        if (!usuarios.isEmpty()) {
            for (Usuario usuario : usuarios) {
                List<OrgaoPerfil> lotacoes = this.orgaoPerfilRepository.findByUsuario(usuario.getId());
                usuariosLotacao.add(new UsuarioLotacoesResponseDTO(usuario, lotacoes));
            }
        }

        return usuariosLotacao;
    }

    @Override
    public UsuarioLotacoesResponseDTO findUsuarioLotacaoById(Long id) {
        Usuario usuario = this.repository.findById(id);

        if (usuario == null)
            throw new NotFoundException("Usuário " + id + "não encontrado.");

        List<OrgaoPerfil> lotacoes = this.orgaoPerfilRepository.findByUsuario(id);

        return new UsuarioLotacoesResponseDTO(usuario, lotacoes);
    }

    @Override
    public Long count() {
        return repository.count();
    }

    @Override
    public List<UsuarioLotacoesResponseDTO> findByNomeOuCpf(String nomeOuCpf, int page, int pageSize) {
        List<UsuarioLotacoesResponseDTO> usuariosLotacao = new ArrayList<>();
        List<Usuario> usuarios = this.repository.findByNomeOuCpf(nomeOuCpf)
                .page(Page.of(page, pageSize))
                .list().stream()
                .sorted(Comparator.comparing(Usuario::getNome))
                .collect(Collectors.toList());

        if (!usuarios.isEmpty()) {
            for (Usuario usuario : usuarios) {
                List<OrgaoPerfil> lotacoes = this.orgaoPerfilRepository.findByUsuario(usuario.getId());
                usuariosLotacao.add(new UsuarioLotacoesResponseDTO(usuario, lotacoes));
            }
        }

        return usuariosLotacao;
    }

    @Override
    public Long countByNomeOuCpf(String nomeOuCpf) {
        return this.repository.findByNomeOuCpf(nomeOuCpf).count();
    }

}
