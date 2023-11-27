package br.unitins.projeto.model;

public enum SituacaoAtendimento {
    EM_ANDAMENTO (1, "Em Andamento"),
    FINALIZADO (2, "Finalizado");

    private int id;
    private String label;

    SituacaoAtendimento(int id, String label) {
        this.id = id;
        this.label = label;
    }

    public int getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public static SituacaoAtendimento valueOf(Integer id) throws IllegalArgumentException {
        if (id == null)
            return null;
        for (SituacaoAtendimento perfil : SituacaoAtendimento.values()) {
            if (id.equals(perfil.getId()))
                return perfil;
        }
        throw new IllegalArgumentException("Id inválido:" + id);
    }
}
