package br.unitins.projeto.dto.orgao;

import br.unitins.projeto.dto.municipio.MunicipioResponseDTO;
import br.unitins.projeto.model.Municipio;
import br.unitins.projeto.model.Orgao;

public record OrgaoResponseDTO(
        Long id,
        String nome,
        String sigla,
        MunicipioResponseDTO municipio,
        Boolean ativo
) {
    public OrgaoResponseDTO(Orgao entity) {
        this(entity.getId(), entity.getNome(), entity.getSigla(), gerarMunicipioDTO(entity.getMunicipio()), entity.getAtivo());
    }

    public static MunicipioResponseDTO gerarMunicipioDTO(Municipio municipio) {
        MunicipioResponseDTO municipioResponseDTO = new MunicipioResponseDTO(municipio);
        return municipioResponseDTO;
    }
}
