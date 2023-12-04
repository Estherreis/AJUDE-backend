package br.unitins.projeto.dto.encaminhamento;

import br.unitins.projeto.dto.atendimento.AtendimentoResponseDTO;
import br.unitins.projeto.dto.orgao.OrgaoResponseDTO;
import br.unitins.projeto.dto.usuario.UsuarioResponseDTO;
import br.unitins.projeto.model.Atendimento;
import br.unitins.projeto.model.Encaminhamento;
import br.unitins.projeto.model.Orgao;
import br.unitins.projeto.model.Usuario;

import java.time.LocalDateTime;

public record EncaminhamentoResponseDTO(
        Long id,
        OrgaoResponseDTO orgaoAtual,
        OrgaoResponseDTO orgaoDestino,
        AtendimentoResponseDTO atendimento,
        String descricao,
        String motivo,
        LocalDateTime dataInclusao,
        UsuarioResponseDTO usuarioInclusao
) {
    public EncaminhamentoResponseDTO(Encaminhamento entity) {
        this(entity.getId(), gerarOrgaoResponseDTO(entity.getOrgaoAtual()),
                gerarOrgaoResponseDTO(entity.getOrgaoDestino()),
                gerarAtendimentoResponseDTO(entity.getAtendimento()),
                entity.getUsuarioInclusao().getNome() + " - ",
//                        + entity.getUsuarioInclusao().getOrgaoPerfil().get(0).getOrgao().getNome(),
                entity.getMotivo(),
                entity.getDataInclusao(),
                gerarUsuarioResponseDTO(entity.getUsuarioInclusao())
        );
    }

    public static OrgaoResponseDTO gerarOrgaoResponseDTO(Orgao orgao) {
        OrgaoResponseDTO orgaoResponseDTO = new OrgaoResponseDTO(orgao);
        return orgaoResponseDTO;
    }

    public static UsuarioResponseDTO gerarUsuarioResponseDTO(Usuario usuario) {
        UsuarioResponseDTO usuarioResponseDTO = new UsuarioResponseDTO(usuario);
        return usuarioResponseDTO;
    }

    public static AtendimentoResponseDTO gerarAtendimentoResponseDTO(Atendimento atendimento) {
        AtendimentoResponseDTO atendimentoResponseDTO = new AtendimentoResponseDTO(atendimento);
        return atendimentoResponseDTO;
    }
}
