package br.unitins.projeto.service.encaminhamento;

import br.unitins.projeto.dto.encaminhamento.EncaminhamentoDTO;
import br.unitins.projeto.dto.encaminhamento.EncaminhamentoResponseDTO;
import br.unitins.projeto.model.Atendimento;
import br.unitins.projeto.model.Encaminhamento;
import br.unitins.projeto.model.Orgao;
import br.unitins.projeto.model.Usuario;
import br.unitins.projeto.repository.AtendimentoRepository;
import br.unitins.projeto.repository.EncaminhamentoRepository;
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
public class EncaminhamentoServiceImpl implements EncaminhamentoService {

    @Inject
    EncaminhamentoRepository repository;

    @Inject
    OrgaoRepository orgaoRepository;

    @Inject
    AtendimentoRepository atendimentoRepository;

    @Inject
    UsuarioService usuarioService;

    @Inject
    JsonWebToken jwt;

    @Inject
    Validator validator;

    @Override
    public List<EncaminhamentoResponseDTO> findByAtendimento(Long idAtendimento, int page, int pageSize) {
        List<Encaminhamento> list = repository.findByAtendimento(idAtendimento).page(page, pageSize)
                .list()
                .stream()
                .sorted(Comparator.comparing(Encaminhamento::getDataInclusao).reversed())
                .collect(Collectors.toList());

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
    public EncaminhamentoResponseDTO create(@Valid EncaminhamentoDTO encaminhamentoDTO) throws ConstraintViolationException {
        validar(encaminhamentoDTO);

        Encaminhamento entity = new Encaminhamento();
        Atendimento atendimento = atendimentoRepository.findById(encaminhamentoDTO.idAtendimento());
        Orgao orgaoDestino = orgaoRepository.findById(encaminhamentoDTO.idOrgao());

//        String login = jwt.getSubject();
//        Usuario usuario = usuarioService.findByLogin(login);
//        Orgao orgaoUsuarioLogado = orgaoRepository.findById(Long.valueOf(jwt.getClaim("orgao").toString()));

        String login = "unitins";
        Usuario usuario = usuarioService.findByLogin(login);
        Orgao orgaoUsuarioLogado = orgaoRepository.findById(1L);

        entity.setOrgaoAtual(atendimento.getOrgao());
        entity.setOrgaoDestino(orgaoDestino);
        entity.setAtendimento(atendimento);
        entity.setUsuarioInclusao(usuario);
        entity.setDataInclusao(LocalDateTime.now());
        entity.setMotivo(encaminhamentoDTO.motivo());
        entity.setDescricao(usuario.getNome() + " - " + orgaoUsuarioLogado.getSigla());

        atendimento.setOrgao(orgaoDestino);

        repository.persist(entity);

        return new EncaminhamentoResponseDTO(entity);
    }

    private void validar(EncaminhamentoDTO encaminhamentoDTO) throws ConstraintViolationException {
        Set<ConstraintViolation<EncaminhamentoDTO>> violations = validator.validate(encaminhamentoDTO);

        if (!violations.isEmpty())
            throw new ConstraintViolationException(violations);
    }

    @Override
    public Long countByAtendimento(Long idAtendimento) {
        return this.repository.findByAtendimento(idAtendimento).count();
    }

}
