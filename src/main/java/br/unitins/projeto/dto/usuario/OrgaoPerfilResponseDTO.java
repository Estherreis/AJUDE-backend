package br.unitins.projeto.dto.usuario;

import br.unitins.projeto.dto.orgao.OrgaoResponseDTO;
import br.unitins.projeto.model.Orgao;
import br.unitins.projeto.model.OrgaoPerfil;
import br.unitins.projeto.model.Perfil;

public record OrgaoPerfilResponseDTO(
        Long id,
        Perfil perfil,
        OrgaoResponseDTO orgao) {

    public OrgaoPerfilResponseDTO(OrgaoPerfil entity) {
        this(entity.getId(), entity.getPerfil(), gerarOrgaoResponseDTO(entity.getOrgao()));
    }

    public static OrgaoResponseDTO gerarOrgaoResponseDTO(Orgao orgao) {
        OrgaoResponseDTO orgaoResponseDTO = new OrgaoResponseDTO(orgao);
        return orgaoResponseDTO;
    }
}
