package br.unitins.projeto.service.orgao;

import br.unitins.projeto.dto.orgao.OrgaoDTO;
import br.unitins.projeto.dto.orgao.OrgaoResponseDTO;
import br.unitins.projeto.model.Orgao;
import br.unitins.projeto.repository.MunicipioRepository;
import br.unitins.projeto.repository.OrgaoRepository;
import io.quarkus.panache.common.Page;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import jakarta.ws.rs.NotFoundException;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@ApplicationScoped
public class OrgaoServiceImpl implements OrgaoService {

    @Inject
    MunicipioRepository municipioRepository;

    @Inject
    OrgaoRepository repository;

    @Inject
    Validator validator;

    @Override
    public List<OrgaoResponseDTO> getAll() {
        List<Orgao> list = repository.listAll();
        return list.stream().map(OrgaoResponseDTO::new).collect(Collectors.toList());
    }

    @Override
    public List<OrgaoResponseDTO> getAll(int page, int pageSize) {
        List<Orgao> list = repository.findAll().page(page, pageSize).list();
        return list.stream().map(e -> new OrgaoResponseDTO(e)).collect(Collectors.toList());
    }

    @Override
    public OrgaoResponseDTO findById(Long id) {
        Orgao orgao = repository.findById(id);

        if (orgao == null)
            throw new NotFoundException("Órgão não encontrado.");

        return new OrgaoResponseDTO(orgao);
    }

    @Override
    @Transactional
    public OrgaoResponseDTO create(@Valid OrgaoDTO orgaoDTO) throws ConstraintViolationException {
        validar(orgaoDTO);

        Orgao entity = new Orgao();
        entity.setNome(orgaoDTO.nome());
        entity.setSigla(orgaoDTO.sigla());
        entity.setMunicipio(municipioRepository.findById(orgaoDTO.idMunicipio()));
        entity.setAtivo(true);
        repository.persist(entity);

        return new OrgaoResponseDTO(entity);
    }

    @Override
    @Transactional
    public OrgaoResponseDTO update(Long id, @Valid OrgaoDTO orgaoDTO) throws ConstraintViolationException {
        validar(orgaoDTO);

        Orgao entity = repository.findById(id);

        entity.setNome(orgaoDTO.nome());
        entity.setSigla(orgaoDTO.sigla());
        entity.setMunicipio(municipioRepository.findById(orgaoDTO.idMunicipio()));

        return new OrgaoResponseDTO(entity);
    }

    private void validar(OrgaoDTO orgaoDTO) throws ConstraintViolationException {
        Set<ConstraintViolation<OrgaoDTO>> violations = validator.validate(orgaoDTO);

        if (!violations.isEmpty())
            throw new ConstraintViolationException(violations);
    }

    @Override
    @Transactional
    public OrgaoResponseDTO alterarSituacao(Long id, Boolean situacao) {
        Orgao entity = repository.findById(id);
        entity.setAtivo(situacao);

        return new OrgaoResponseDTO(entity);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<OrgaoResponseDTO> findByNomeOuSigla(String nomeOuSigla, int page, int pageSize) {
        List<Orgao> list = this.repository.findByNomeOuSigla(nomeOuSigla)
                .page(Page.of(page, pageSize))
                .list().stream()
                .sorted(Comparator.comparing(Orgao::getNome))
                .collect(Collectors.toList());

        return list.stream().map(OrgaoResponseDTO::new).collect(Collectors.toList());
    }

    @Override
    public Long countByNomeOuSigla(String nomeOuSigla) {
        return this.repository.findByNomeOuSigla(nomeOuSigla).count();
    }

    @Override
    public Long count() {
        return repository.count();
    }

}
