package br.unitins.projeto.service.municipio;

import br.unitins.projeto.dto.municipio.MunicipioDTO;
import br.unitins.projeto.dto.municipio.MunicipioResponseDTO;

import java.util.List;

public interface MunicipioService {

    List<MunicipioResponseDTO> getAll();

    List<MunicipioResponseDTO> getAll(int page, int pageSize);

    MunicipioResponseDTO findById(Long id);

    MunicipioResponseDTO create(MunicipioDTO productDTO);

    MunicipioResponseDTO update(Long id, MunicipioDTO productDTO);

    void delete(Long id);

    List<MunicipioResponseDTO> findByDescricao(String descricao);

    Long count();

}
