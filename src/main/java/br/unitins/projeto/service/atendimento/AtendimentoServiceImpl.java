package br.unitins.projeto.service.atendimento;

import br.unitins.projeto.dto.atendimento.AtendimentoDTO;
import br.unitins.projeto.dto.atendimento.AtendimentoResponseDTO;
import br.unitins.projeto.dto.atendimento.AtendimentoUpdateDTO;
import br.unitins.projeto.model.Atendimento;
import br.unitins.projeto.model.Orgao;
import br.unitins.projeto.model.SituacaoAtendimento;
import br.unitins.projeto.model.Usuario;
import br.unitins.projeto.repository.AtendimentoRepository;
import br.unitins.projeto.repository.BeneficiarioRepository;
import br.unitins.projeto.repository.OrgaoRepository;
import br.unitins.projeto.service.usuario.UsuarioService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import jakarta.ws.rs.NotFoundException;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@ApplicationScoped
public class AtendimentoServiceImpl implements AtendimentoService {

    @Inject
    AtendimentoRepository repository;

    @Inject
    OrgaoRepository orgaoRepository;

    @Inject
    BeneficiarioRepository beneficiarioRepository;

    @Inject
    UsuarioService usuarioService;

    @Inject
    Validator validator;

    @Inject
    JsonWebToken jwt;

    @Override
    public List<AtendimentoResponseDTO> getAll() {
        List<Atendimento> list = repository.listAll();
        return list.stream().map(AtendimentoResponseDTO::new).collect(Collectors.toList());
    }

    @Override
    public AtendimentoResponseDTO findById(Long id) {
        Atendimento atendimento = repository.findById(id);

        if (atendimento == null)
            throw new NotFoundException("Atendimento não encontrado.");

        return new AtendimentoResponseDTO(atendimento);
    }

    @Override
    public List<AtendimentoResponseDTO> findByBeneficiario(Long idBeneficiario, int page, int pageSize) {
//        String idOrgao = jwt.getClaim("orgao").toString();
        Orgao orgao = orgaoRepository.findById(1L);

        List<Atendimento> list = repository.findByBeneficiario(idBeneficiario, Long.valueOf(orgao.getId())).page(page, pageSize)
                .list()
                .stream()
                .sorted(Comparator.comparing(Atendimento::getDataCadastro).reversed())
                .collect(Collectors.toList());

        return list.stream().map(AtendimentoResponseDTO::new).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public AtendimentoResponseDTO create(@Valid AtendimentoDTO atendimentoDTO) throws ConstraintViolationException {
        validar(atendimentoDTO);

//        String idOrgao = jwt.getClaim("orgao").toString();
//        String login = jwt.getSubject();
        String login = "unitins";
        Usuario usuario = usuarioService.findByLogin(login);
        Long idOrgao = orgaoRepository.findById(1L).getId();

        Atendimento entity = new Atendimento();

        entity.setOrgao(orgaoRepository.findById(Long.valueOf(idOrgao)));
        entity.setBeneficiario(beneficiarioRepository.findById(atendimentoDTO.idBeneficiario()));
        entity.setDataCadastro(LocalDateTime.now());
        entity.setUsuarioInclusao(usuario);
        entity.setDescricao(atendimentoDTO.descricao());
        entity.setTipoBeneficio(atendimentoDTO.tipoBeneficio());
        entity.setSituacaoAtendimento(SituacaoAtendimento.EM_ANDAMENTO);

        repository.persist(entity);

        return new AtendimentoResponseDTO(entity);
    }

    @Override
    @Transactional
    public AtendimentoResponseDTO update(Long id, @Valid AtendimentoUpdateDTO atendimentoDTO)
            throws ConstraintViolationException {
        Atendimento entity = repository.findById(id);
        entity.setDescricao(atendimentoDTO.descricao());
        entity.setTipoBeneficio(atendimentoDTO.tipoBeneficio());

        return new AtendimentoResponseDTO(entity);
    }

    @Override
    @Transactional
    public AtendimentoResponseDTO finalizarAtendimento(Long id) throws ConstraintViolationException {
        Atendimento entity = repository.findById(id);
        entity.setSituacaoAtendimento(SituacaoAtendimento.FINALIZADO);

        return new AtendimentoResponseDTO(entity);
    }

    private void validar(AtendimentoDTO atendimentoDTO) throws ConstraintViolationException {
        Set<ConstraintViolation<AtendimentoDTO>> violations = validator.validate(atendimentoDTO);

        if (!violations.isEmpty())
            throw new ConstraintViolationException(violations);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Long countByBeneficiario(Long idBeneficiario) {
        Orgao orgao = orgaoRepository.findById(1L);
        return repository.findByBeneficiario(idBeneficiario, orgao.getId()).count();
    }

}
