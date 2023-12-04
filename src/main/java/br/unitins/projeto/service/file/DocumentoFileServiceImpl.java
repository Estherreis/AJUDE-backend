package br.unitins.projeto.service.file;

import br.unitins.projeto.model.Movimentacao;
import br.unitins.projeto.repository.MovimentacaoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class DocumentoFileServiceImpl implements FileService {

    private final String PATH_USER = System.getProperty("user.home")
            + File.separator + "quarkus"
            + File.separator + "docs"
            + File.separator + "movimentacao" + File.separator;


    @Inject
    MovimentacaoRepository movimentacaoRepository;

    @Override
    @Transactional
    public void salvar(Long id, String nomeDocumento, byte[] documento) throws IOException {
        Movimentacao movimentacao = movimentacaoRepository.findById(id);

        try {
            String novoNomeDocumento = salvarDocumento(documento, nomeDocumento);
            movimentacao.setNomeDocumento(novoNomeDocumento);
        } catch (IOException e) {
            throw e;
        }
    }

    private String salvarDocumento(byte[] documento, String nomeDocumento) throws IOException {

        // verificando o tipo da imagem
        String mimeType = Files.probeContentType(new File(nomeDocumento).toPath());
        List<String> listMimeType = Arrays.asList("application/pdf");
        if (!listMimeType.contains(mimeType)) {
            throw new IOException("Tipo de documento não suportada.");
        }

        if (documento.length > (1024 * 1024 * 10))
            throw new IOException("Arquivo muito grande.");

        // criando as pastas quando não existir
        File diretorio = new File(PATH_USER);
        if (!diretorio.exists())
            diretorio.mkdirs();

        String nomeArquivo = nomeDocumento;

        String path = PATH_USER + nomeArquivo;
        File file = new File(path);

        if (file.exists()) {
            nomeArquivo = UUID.randomUUID() + nomeArquivo;
            path = PATH_USER + nomeArquivo;
            file = new File(path);

            if (file.exists())
                throw new IOException("O nome gerado do documento está repetido.");
        }

        file.createNewFile();

        FileOutputStream fos = new FileOutputStream(file);
        fos.write(documento);
        fos.flush();
        fos.close();

        return nomeArquivo;
    }

    @Override
    public File download(String nomeArquivo) {
        File file = new File(PATH_USER + nomeArquivo);
        return file;
    }

}
