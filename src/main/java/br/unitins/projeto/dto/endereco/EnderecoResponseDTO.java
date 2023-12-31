package br.unitins.projeto.dto.endereco;

import br.unitins.projeto.dto.estado.EstadoResponseDTO;
import br.unitins.projeto.dto.municipio.MunicipioResponseDTO;
import br.unitins.projeto.model.Endereco;

public record EnderecoResponseDTO(

    Long id,
    MunicipioResponseDTO municipio,
    EstadoResponseDTO estado,
    String numero,
    String bairro,
    String logradouro,
    String complemento

) {
        public EnderecoResponseDTO(Endereco entity){
            this(entity.getId(), new MunicipioResponseDTO(entity.getMunicipio()), new EstadoResponseDTO(entity.getEstado()), 
            entity.getNumero(), entity.getBairro(), entity.getLogradouro(), entity.getComplemento());
        }
}
