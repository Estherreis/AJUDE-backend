package br.unitins.projeto.service.usuario;

import br.unitins.projeto.dto.usuario.OrgaoPerfilDTO;
import br.unitins.projeto.dto.usuario.OrgaoPerfilResponseDTO;
import br.unitins.projeto.dto.usuario.UsuarioDTO;
import br.unitins.projeto.dto.usuario.UsuarioLotacoesResponseDTO;
import br.unitins.projeto.dto.usuario.UsuarioResponseDTO;
import br.unitins.projeto.dto.usuario.UsuarioUpdateDTO;
import br.unitins.projeto.model.Usuario;
import jakarta.validation.Valid;

import java.util.List;

public interface UsuarioService {

    List<UsuarioResponseDTO> getAll();

    UsuarioResponseDTO findById(Long id);

    UsuarioResponseDTO create(@Valid UsuarioDTO productDTO);

    UsuarioResponseDTO update(Long id, @Valid UsuarioUpdateDTO productDTO);

    UsuarioResponseDTO alterarSituacao(Long id, Boolean situacao);

    UsuarioResponseDTO adicionarUsuario(Long id, @Valid OrgaoPerfilDTO orgaoPerfilDTO);

    List<OrgaoPerfilResponseDTO> getLotacoesUsuario(Long id);

    Usuario findByLoginAndSenha(String login, String senha);

    Usuario findByLogin(String login);

    List<UsuarioLotacoesResponseDTO> getAllUsuariosLotacoes(int page, int pageSize);

    UsuarioLotacoesResponseDTO findUsuarioLotacaoById(Long id);

    List<UsuarioLotacoesResponseDTO> findByNomeOuCpf(String nomeOuCpf, int page, int pageSize);

    Long countByNomeOuCpf(String nomeOuCpf);

    Long count();

}
