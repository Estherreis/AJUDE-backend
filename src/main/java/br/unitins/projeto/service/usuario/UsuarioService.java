package br.unitins.projeto.service.usuario;

import br.unitins.projeto.dto.municipio.MunicipioDTO;
import br.unitins.projeto.dto.municipio.MunicipioResponseDTO;
import br.unitins.projeto.dto.orgao.OrgaoResponseDTO;
import br.unitins.projeto.dto.usuario.UsuarioDTO;
import br.unitins.projeto.dto.usuario.UsuarioResponseDTO;

import java.util.List;

public interface UsuarioService {

    List<UsuarioResponseDTO> getAll();

    UsuarioResponseDTO findById(Long id);

    UsuarioResponseDTO create(UsuarioDTO productDTO);

    UsuarioResponseDTO update(Long id, UsuarioDTO productDTO);

    UsuarioResponseDTO alterarSituacao(Long id, Boolean situacao);

}
