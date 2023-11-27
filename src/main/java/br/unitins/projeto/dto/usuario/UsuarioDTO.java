package br.unitins.projeto.dto.usuario;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UsuarioDTO(

        @NotBlank(message = "O nome deve ser informado.")
        @Size(max = 80, message = "O nome deve possuir no máximo 80 caracteres.")
        String nome,

        @NotBlank(message = "O cpf deve ser informado.")
        @Size(max = 11, min = 11, message = "O cpf deve possuir 11 caracteres.")
        String cpf,

        @NotBlank(message = "O login deve ser informado.")
        @Size(max = 20, message = "O login deve possuir no máximo 20 caracteres.")
        String login,

        @NotBlank(message = "A senha deve ser informada.")
        String senha,

        @NotNull(message = "O nível de sigilo deve ser informado.")
        Integer nivelSigilo,

        @NotNull(message = "O perfil e órgão devem ser informados.")
        OrgaoPerfilDTO orgaoPerfil

) {
}
