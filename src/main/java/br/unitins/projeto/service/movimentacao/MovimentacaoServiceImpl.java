package br.unitins.projeto.service.movimentacao;

import br.unitins.projeto.dto.movimentacao.MovimentacaoDTO;
import br.unitins.projeto.dto.movimentacao.MovimentacaoResponseDTO;
import br.unitins.projeto.model.Movimentacao;
import br.unitins.projeto.model.Usuario;
import br.unitins.projeto.repository.AtendimentoRepository;
import br.unitins.projeto.repository.MovimentacaoRepository;
import br.unitins.projeto.repository.OrgaoRepository;
import br.unitins.projeto.service.usuario.UsuarioService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.NotFoundException;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class MovimentacaoServiceImpl implements MovimentacaoService {

    @Inject
    MovimentacaoRepository movimentacaoRepository;

    @Inject
    AtendimentoRepository atendimentoRepository;

    @Inject
    OrgaoRepository orgaoRepository;

    @Inject
    UsuarioService usuarioService;

    @Inject
    JsonWebToken jwt;

    @Override
    public MovimentacaoResponseDTO findById(Long id) {
        Movimentacao movimentacao = movimentacaoRepository.findById(id);

        if (movimentacao == null)
            throw new NotFoundException("Movimentação não encontrada.");

        return new MovimentacaoResponseDTO(movimentacao);
    }

    @Override
    public Long countByAtendimento(Long idAtendimento) {
        return this.movimentacaoRepository.findByAtendimento(idAtendimento).count();
    }

    @Override
    public List<MovimentacaoResponseDTO> findByAtendimento(Long idAtendimento, int page, int pageSize) {
        List<Movimentacao> list = movimentacaoRepository.findByAtendimento(idAtendimento).page(page, pageSize)
                .list()
                .stream()
                .sorted(Comparator.comparing(Movimentacao::getDataInclusao).reversed())
                .collect(Collectors.toList());

        return list.stream().map(MovimentacaoResponseDTO::new).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public MovimentacaoResponseDTO lancarMovimentacao(@Valid MovimentacaoDTO movimentacaoDTO) {

        Movimentacao entity = new Movimentacao();
        LocalDateTime now = LocalDateTime.now();

//        String login = jwt.getSubject();
//        Usuario usuario = usuarioService.findByLogin(login);
//        Orgao orgao = orgaoRepository.findById(Long.valueOf(jwt.getClaim("orgao").toString()));

        String login = "unitins";
        Usuario usuario = usuarioService.findByLogin(login);

        entity.setTituloMovimentacao(movimentacaoDTO.tituloMovimentacao());
        entity.setUsuarioAutor(usuario);
        entity.setAtendimento(atendimentoRepository.findById(movimentacaoDTO.idAtendimento()));
        entity.setDescricao(movimentacaoDTO.tituloMovimentacao());
        entity.setDataInclusao(now);

        movimentacaoRepository.persist(entity);

        return new MovimentacaoResponseDTO(entity);
    }

}
