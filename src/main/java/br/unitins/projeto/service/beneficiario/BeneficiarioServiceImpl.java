package br.unitins.projeto.service.beneficiario;

import br.unitins.projeto.dto.beneficiario.BeneficiarioDTO;
import br.unitins.projeto.dto.beneficiario.BeneficiarioResponseDTO;
import br.unitins.projeto.model.Beneficiario;
import br.unitins.projeto.model.Endereco;
import br.unitins.projeto.repository.BeneficiarioRepository;
import br.unitins.projeto.repository.EnderecoRepository;
import br.unitins.projeto.repository.EstadoRepository;
import br.unitins.projeto.repository.MunicipioRepository;
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
public class BeneficiarioServiceImpl implements BeneficiarioService {

    @Inject
    BeneficiarioRepository beneficiarioRepository;

    @Inject
    EstadoRepository estadoRepository;

    @Inject
    MunicipioRepository municipioRepository;

    @Inject
    EnderecoRepository enderecoRepository;

    @Inject
    Validator validator;

    @Override
    public List<BeneficiarioResponseDTO> getAll() {
        List<Beneficiario> list = beneficiarioRepository.listAll();
        return list.stream().map(BeneficiarioResponseDTO::new).collect(Collectors.toList());

    }

    @Override
    public BeneficiarioResponseDTO findById(Long id) {

        Beneficiario beneficiario = beneficiarioRepository.findById(id);

        if (beneficiario == null) {
            throw new NotFoundException("Beneficiario não encontrado.");
        }

        return new BeneficiarioResponseDTO(beneficiario);
    }

    @Override
    @Transactional
    public BeneficiarioResponseDTO create(@Valid BeneficiarioDTO beneficiarioDTO) {
        validar(beneficiarioDTO);

        Beneficiario entity = new Beneficiario();
        Endereco endereco = new Endereco();

        entity.setNome(beneficiarioDTO.nome());
        entity.setCpf(beneficiarioDTO.cpf());
        entity.setNis(beneficiarioDTO.nis());
        entity.setDataNascimento(beneficiarioDTO.dataNascimento());
        entity.setCpfPai(beneficiarioDTO.cpfPais());
        entity.setRg(beneficiarioDTO.rg());

        if (beneficiarioDTO.endereco() != null) {
            endereco.setMunicipio(municipioRepository.findById(beneficiarioDTO.endereco().idMunicipio()));
            endereco.setEstado(estadoRepository.findById(endereco.getMunicipio().getEstado().getId()));
            endereco.setBairro(beneficiarioDTO.endereco().bairro());
            endereco.setComplemento(beneficiarioDTO.endereco().complemento());
            endereco.setNumero(beneficiarioDTO.endereco().numero());
            endereco.setLogradouro(beneficiarioDTO.endereco().logradouro());
            entity.setEndereco(endereco);

            enderecoRepository.persist(endereco);
        }

        beneficiarioRepository.persist(entity);

        return new BeneficiarioResponseDTO(entity);
    }

    @Override
    @Transactional
    public BeneficiarioResponseDTO update(Long id, @Valid BeneficiarioDTO beneficiarioDTO) {
        validar(beneficiarioDTO);

        Beneficiario entity = beneficiarioRepository.findById(id);
        Endereco endereco = new Endereco();

        entity.setNome(beneficiarioDTO.nome());
        entity.setCpf(beneficiarioDTO.cpf());
        entity.setNis(beneficiarioDTO.nis());
        entity.setDataNascimento(beneficiarioDTO.dataNascimento());
        entity.setCpfPai(beneficiarioDTO.cpfPais());

        if (beneficiarioDTO.endereco() != null) {
            endereco.setMunicipio(municipioRepository.findById(beneficiarioDTO.endereco().idMunicipio()));
            endereco.setEstado(estadoRepository.findById(endereco.getMunicipio().getEstado().getId()));
            endereco.setBairro(beneficiarioDTO.endereco().bairro());
            endereco.setComplemento(beneficiarioDTO.endereco().complemento());
            endereco.setNumero(beneficiarioDTO.endereco().numero());
            endereco.setLogradouro(beneficiarioDTO.endereco().logradouro());
            entity.setEndereco(endereco);

            enderecoRepository.persist(endereco);
        }

        beneficiarioRepository.persist(entity);

        return new BeneficiarioResponseDTO(entity);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        beneficiarioRepository.deleteById(id);
    }

    @Override
    public Long count() {

        return beneficiarioRepository.count();
    }

    private void validar(BeneficiarioDTO beneficiarioDTO) throws ConstraintViolationException {
        Set<ConstraintViolation<BeneficiarioDTO>> violations = validator.validate(beneficiarioDTO);

        if (!violations.isEmpty())
            throw new ConstraintViolationException(violations);
    }

    @Override
    public List<BeneficiarioResponseDTO> findByNome(String nome) {

        List<Beneficiario> list = beneficiarioRepository.findByNome(nome);
        return list.stream().map(BeneficiarioResponseDTO::new).collect(Collectors.toList());

    }

    @Override
    public List<BeneficiarioResponseDTO> findByCpf(String cpf) {

        List<Beneficiario> list = beneficiarioRepository.findByCPF(cpf);
        return list.stream().map(BeneficiarioResponseDTO::new).collect(Collectors.toList());
    }

    @Override
    public List<BeneficiarioResponseDTO> findByNomeOuCpf(String nomeOuCpf, int page, int pageSize) {

        List<Beneficiario> list = this.beneficiarioRepository.findByNomeOrCpf(nomeOuCpf)
                .page(Page.of(page, pageSize))
                .list().stream()
                .sorted(Comparator.comparing(Beneficiario::getNome))
                .collect(Collectors.toList());

        return list.stream().map(BeneficiarioResponseDTO::new).collect(Collectors.toList());
    }

    @Override
    public Long countByNomeOuCpf(String nomeOuCpf) {
        return beneficiarioRepository.findByNomeOrCpf(nomeOuCpf).count();
    }

}
