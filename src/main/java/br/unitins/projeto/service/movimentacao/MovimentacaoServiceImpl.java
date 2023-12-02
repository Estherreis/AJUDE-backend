package br.unitins.projeto.service.movimentacao;

import br.unitins.projeto.dto.movimentacao.MovimentacaoDTO;
import br.unitins.projeto.dto.movimentacao.MovimentacaoResponseDTO;
import br.unitins.projeto.model.Movimentacao;
import br.unitins.projeto.model.Orgao;
import br.unitins.projeto.model.Usuario;
import br.unitins.projeto.repository.AtendimentoRepository;
import br.unitins.projeto.repository.MovimentacaoRepository;
import br.unitins.projeto.repository.OrgaoRepository;
import br.unitins.projeto.service.usuario.UsuarioService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.time.LocalDateTime;
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
    @Transactional
    public MovimentacaoResponseDTO lancarMovimentacao(MovimentacaoDTO movimentacaoDTO) {

        Movimentacao entity = new Movimentacao();
        LocalDateTime now = LocalDateTime.now();

        String login = jwt.getSubject();
        Usuario usuario = usuarioService.findByLogin(login);
        Orgao orgao = orgaoRepository.findById(Long.valueOf(jwt.getClaim("orgao").toString()));

        entity.setTituloMovimentacao(movimentacaoDTO.tituloMovimentacao());
        entity.setUsuarioAutor(usuario);
        entity.setAtendimento(atendimentoRepository.findById(movimentacaoDTO.idAtendimento()));
        entity.setDescricao(usuario.getNome() + " - " + orgao.getSigla());
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
