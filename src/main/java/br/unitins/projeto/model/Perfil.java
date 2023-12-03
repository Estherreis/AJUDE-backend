package br.unitins.projeto.model;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Perfil {
    ADMINISTRADOR (1, "Administrador"),
    ASSISTENTE (2, "Assistente"),
    LOGADO (3, "Logado");

    private Integer id;
    private String label;

    Perfil(int id, String label) {
        this.id = id;
        this.label = label;
    }

    public Integer getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public static Perfil valueOf(Integer id) throws IllegalArgumentException {
        if (id == null)
            return null;
        for (Perfil perfil : Perfil.values()) {
            if (id.equals(perfil.getId()))
                return perfil;
        }
        throw new IllegalArgumentException("Id inválido:" + id);
    }
}
