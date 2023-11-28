package br.unitins.projeto.converterjpa;

import br.unitins.projeto.model.SituacaoAtendimento;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class SituacaoAtendimentoConverter implements AttributeConverter<SituacaoAtendimento, Integer> {

    @Override
    public Integer convertToDatabaseColumn(SituacaoAtendimento tipoChavePix) {
        return tipoChavePix == null ? null : tipoChavePix.getId();
    }

    @Override
    public SituacaoAtendimento convertToEntityAttribute(Integer id) {
        return SituacaoAtendimento.valueOf(id);
    }

}
