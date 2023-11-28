package br.unitins.projeto.service.movimentacao;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import br.unitins.projeto.dto.movimentacao.MovimentacaoDTO;
import br.unitins.projeto.dto.movimentacao.MovimentacaoResponseDTO;
import br.unitins.projeto.model.Movimentacao;
import br.unitins.projeto.repository.AtendimentoRepository;
import br.unitins.projeto.repository.MovimentacaoRepository;
import br.unitins.projeto.repository.UsuarioRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class MovimentacaoServiceImpl implements MovimentacaoService{

    @Inject
    MovimentacaoRepository movimentacaoRepository;

    @Inject
    AtendimentoRepository atendimentoRepository;

    @Inject
    UsuarioRepository usuarioRepository;

    

    @Override
    @Transactional
    public MovimentacaoResponseDTO lancarMovimentacao(MovimentacaoDTO movimentacaoDTO) {

        Movimentacao entity = new Movimentacao();
        LocalDateTime now = LocalDateTime.now();

        entity.setTituloMovimentacao(movimentacaoDTO.tituloMovimentacao());
        entity.setUsuarioAutor(usuarioRepository.findById(movimentacaoDTO.usuarioAutor()));
        entity.setAtendimento(atendimentoRepository.findById(movimentacaoDTO.idAtendimento()));
        entity.setDescricao(movimentacaoDTO.descricao());
        entity.setDataInclusao(now);

        movimentacaoRepository.persist(entity);

        return new MovimentacaoResponseDTO(entity);
    }

    @Override
    public List<MovimentacaoResponseDTO> listarPorAtendimento(Long idAtendimento) {

        List<Movimentacao> list = movimentacaoRepository.findByAtendimento(idAtendimento);
        return list.stream().map(MovimentacaoResponseDTO::new).collect(Collectors.toList());
    }
}
