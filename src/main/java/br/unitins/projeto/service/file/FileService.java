package br.unitins.projeto.service.file;

import java.io.File;
import java.io.IOException;

public interface FileService {

     void salvar(Long id, String nomeDocumento, byte[] documento) throws IOException;
    
    File download(String nomeArquivo); 
}