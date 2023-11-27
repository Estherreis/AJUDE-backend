package br.unitins.projeto.service.encaminhamento;

import br.unitins.projeto.dto.encaminhamento.EncaminhamentoDTO;
import br.unitins.projeto.dto.encaminhamento.EncaminhamentoResponseDTO;
import br.unitins.projeto.model.Atendimento;
import br.unitins.projeto.model.Encaminhamento;
import br.unitins.projeto.model.Orgao;
import br.unitins.projeto.model.SituacaoAtendimento;
import br.unitins.projeto.repository.AtendimentoRepository;
import br.unitins.projeto.repository.EncaminhamentoRepository;
import br.unitins.projeto.repository.OrgaoRepository;
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
public class EncaminhamentoServiceImpl implements EncaminhamentoService {

    @Inject
    EncaminhamentoRepository repository;

    @Inject
    OrgaoRepository orgaoRepository;

    @Inject
    AtendimentoRepository atendimentoRepository;

    @Inject
    Validator validator;

    @Override
    public List<EncaminhamentoResponseDTO> findByAtendimento(Long idAtendimento) {
        List<Encaminhamento> list = repository.findByAtendimento(idAtendimento);
        return list.stream().map(EncaminhamentoResponseDTO::new).collect(Collectors.toList());
    }

    @Override
    public EncaminhamentoResponseDTO findById(Long id) {
        Encaminhamento encaminhamento = repository.findById(id);

        if (encaminhamento == null)
            throw new NotFoundException("Encaminhamento não encontrado.");

        return new EncaminhamentoResponseDTO(encaminhamento);
    }

    @Override
    @Transactional
    public EncaminhamentoResponseDTO create(EncaminhamentoDTO encaminhamentoDTO) throws ConstraintViolationException {
        validar(encaminhamentoDTO);

        Encaminhamento entity = new Encaminhamento();
        Atendimento atendimento = atendimentoRepository.findById(encaminhamentoDTO.idAtendimento());
        Orgao orgaoDestino = orgaoRepository.findById(encaminhamentoDTO.idOrgao());

        entity.setOrgaoAtual(atendimento.getOrgao());
        entity.setOrgaoDestino(orgaoDestino);
        entity.setAtendimento(atendimento);
        entity.setDataInclusao(LocalDateTime.now());
        entity.setMotivo(encaminhamentoDTO.motivo());

        atendimento.setOrgao(orgaoDestino);

        repository.persist(entity);

        return new EncaminhamentoResponseDTO(entity);
    }

    private void validar(EncaminhamentoDTO encaminhamentoDTO) throws ConstraintViolationException {
        Set<ConstraintViolation<EncaminhamentoDTO>> violations = validator.validate(encaminhamentoDTO);

        if (!violations.isEmpty())
            throw new ConstraintViolationException(violations);
    }
}
