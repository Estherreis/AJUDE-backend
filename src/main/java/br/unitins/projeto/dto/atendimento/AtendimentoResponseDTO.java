package br.unitins.projeto.dto.atendimento;

import br.unitins.projeto.dto.beneficiario.BeneficiarioResponseDTO;
import br.unitins.projeto.dto.orgao.OrgaoResponseDTO;
import br.unitins.projeto.dto.usuario.UsuarioResponseDTO;
import br.unitins.projeto.model.Atendimento;
import br.unitins.projeto.model.Beneficiario;
import br.unitins.projeto.model.Orgao;
import br.unitins.projeto.model.SituacaoAtendimento;
import br.unitins.projeto.model.Usuario;

import java.time.LocalDateTime;

public record AtendimentoResponseDTO(
        Long id,
        OrgaoResponseDTO orgao,
        BeneficiarioResponseDTO beneficiario,
        LocalDateTime dataCadastro,
        UsuarioResponseDTO usuarioInclusao,
        String descricao,
        String tipoBeneficio,
        SituacaoAtendimento situacao
) {
    public AtendimentoResponseDTO(Atendimento entity) {
        this(entity.getId(), gerarOrgaoResponseDTO(entity.getOrgao()),
                gerarBeneficiarioResponseDTO(entity.getBeneficiario()),
                entity.getDataCadastro(),
                gerarUsuarioResponseDTO(entity.getUsuarioInclusao()),
                entity.getDescricao(),
                entity.getTipoBeneficio(),
                entity.getSituacaoAtendimento()
        );
    }

    public static OrgaoResponseDTO gerarOrgaoResponseDTO(Orgao orgao) {
        OrgaoResponseDTO orgaoResponseDTO = new OrgaoResponseDTO(orgao);
        return orgaoResponseDTO;
    }

    public static BeneficiarioResponseDTO gerarBeneficiarioResponseDTO(Beneficiario beneficiario) {
        BeneficiarioResponseDTO beneficiarioResponseDTO = new BeneficiarioResponseDTO(beneficiario);
        return beneficiarioResponseDTO;
    }

    public static UsuarioResponseDTO gerarUsuarioResponseDTO(Usuario usuario) {
        UsuarioResponseDTO usuarioResponseDTO = new UsuarioResponseDTO(usuario);
        return usuarioResponseDTO;
    }

}
