package br.unitins.projeto.service.usuario;

import br.unitins.projeto.dto.usuario.OrgaoPerfilDTO;
import br.unitins.projeto.dto.usuario.OrgaoPerfilResponseDTO;
import br.unitins.projeto.dto.usuario.UsuarioDTO;
import br.unitins.projeto.dto.usuario.UsuarioResponseDTO;
import br.unitins.projeto.model.Usuario;

import java.util.List;

public interface UsuarioService {

    List<UsuarioResponseDTO> getAll();

    UsuarioResponseDTO findById(Long id);

    UsuarioResponseDTO create(UsuarioDTO productDTO);

    UsuarioResponseDTO update(Long id, UsuarioDTO productDTO);

    UsuarioResponseDTO alterarSituacao(Long id, Boolean situacao);

    UsuarioResponseDTO adicionarUsuario(Long id, OrgaoPerfilDTO orgaoPerfilDTO);

    List<OrgaoPerfilResponseDTO> getLotacoesUsuario(Long id);

    Usuario findByLoginAndSenha(String login, String senha);

    Usuario findByLogin(String login);

}
