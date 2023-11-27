package br.unitins.projeto.service.atendimento;

import br.unitins.projeto.dto.atendimento.AtendimentoDTO;
import br.unitins.projeto.dto.atendimento.AtendimentoResponseDTO;
import br.unitins.projeto.model.Atendimento;
import br.unitins.projeto.model.SituacaoAtendimento;
import br.unitins.projeto.repository.AtendimentoRepository;
import br.unitins.projeto.repository.BeneficiarioRepository;
import br.unitins.projeto.repository.OrgaoRepository;
import br.unitins.projeto.repository.UsuarioRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import jakarta.ws.rs.NotFoundException;

import java.time.LocalDateTime;
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
    UsuarioRepository usuarioRepository;

    @Inject
    Validator validator;

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
    public List<AtendimentoResponseDTO> findByBeneficiario(Long idBeneficiario) {
        List<Atendimento> list = repository.findByBeneficiario(idBeneficiario);

        return list.stream().map(AtendimentoResponseDTO::new).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public AtendimentoResponseDTO create(AtendimentoDTO atendimentoDTO) throws ConstraintViolationException {
        validar(atendimentoDTO);

        Atendimento entity = new Atendimento();

        entity.setOrgao(orgaoRepository.findById(atendimentoDTO.idOrgao()));
        entity.setBeneficiario(beneficiarioRepository.findById(atendimentoDTO.idBeneficiario()));
        entity.setDataInclusao(LocalDateTime.now());
        entity.setUsuarioInclusao(usuarioRepository.findById(atendimentoDTO.idUsuarioInclusao()));
        entity.setDescricao(atendimentoDTO.descricao());
        entity.setSituacaoAtendimento(SituacaoAtendimento.EM_ANDAMENTO);

        repository.persist(entity);

        return new AtendimentoResponseDTO(entity);
    }

    @Override
    @Transactional
    public AtendimentoResponseDTO update(Long id, AtendimentoDTO atendimentoDTO) throws ConstraintViolationException {
        validar(atendimentoDTO);

        Atendimento entity = repository.findById(id);

        entity.setOrgao(orgaoRepository.findById(atendimentoDTO.idOrgao()));
        entity.setBeneficiario(beneficiarioRepository.findById(atendimentoDTO.idBeneficiario()));
        entity.setUsuarioInclusao(usuarioRepository.findById(atendimentoDTO.idUsuarioInclusao()));
        entity.setDescricao(atendimentoDTO.descricao());

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
    public Long count() {
        return repository.count();
    }

}
