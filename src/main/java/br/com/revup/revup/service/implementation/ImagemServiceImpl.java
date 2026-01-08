package br.com.revup.revup.service.implementation;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.com.revup.revup.exceptions.ArquivoInvalidoException;
import br.com.revup.revup.exceptions.RecursoNaoEncontradoException;
import br.com.revup.revup.exceptions.TipoArquivoNaoSuportadoException;
import br.com.revup.revup.service.ImagemService;

// Service para lidar com o armazenamento
// de imagens (comunicação direta com o disco)

@Service
public class ImagemServiceImpl implements ImagemService {

    // Métodos auxiliares
    private void verificarImagem(MultipartFile file) {
        // Verificação de conteúdo
        if(file.isEmpty())
            throw new ArquivoInvalidoException("Não há arquivo anexado.");

        // Verificação de tipo (suportados: jpeg, png e webp)
        String type = file.getContentType();
        if(!type.equals("image/jpeg") && !type.equals("image/png") && !type.equals("image/webp"))
            throw new TipoArquivoNaoSuportadoException("Tipo de arquivo não suportado. Tipos suportados: .jpeg, .png, .webp");

        // Verificação de tamanho (max: 5 MB)
        if(file.getSize() > 5L * 1024 * 1024)
            throw new ArquivoInvalidoException("A imagem excede o tamanho máximo de 5 MB por arquivo.");
    }

    // Métodos da interface
    @Override
    public String salvarImagem (MultipartFile file, String path) { // path: pasta
        try { 
            verificarImagem(file);

            // Criar diretorio
            Path diretorio = Paths.get(path).toAbsolutePath().normalize();
            Files.createDirectories(diretorio);

            // Settar nome do arquivo
            String nomeArquivo = UUID.randomUUID() + "-" + file.getOriginalFilename();

            Path destino = diretorio.resolve(nomeArquivo);

            Files.copy(file.getInputStream(), destino, StandardCopyOption.REPLACE_EXISTING);

            return nomeArquivo;
        } 
        catch (IOException err) {
            throw new RuntimeException("Erro ao salvar arquivo.");
        }
    }

    @Override
    public List<Resource> listarImagem (List<String> paths) { // path: pasta + arquivo
        try {
            ArrayList<Resource> imgs = new ArrayList<>();

            // Buscando arquivo para cada path
            for (String path : paths) {
                Path arquivo = Paths.get(path).toAbsolutePath().normalize();
    
                Resource resource = new UrlResource(arquivo.toUri());

                if (!resource.exists()) 
                    throw new RecursoNaoEncontradoException("Imagem não encontrada.");

                imgs.add(resource);
            }

            return imgs;
        } 
        catch (MalformedURLException e) {
            throw new RuntimeException("Erro ao carregar arquivo.");
        }
    }

    @Override
    public void removerImagem (String path) { // path: pasta + arquivo
        try {
            Path arquivo = Paths.get(path).toAbsolutePath().normalize();

            Files.deleteIfExists(arquivo);
        } 
        catch (IOException e) {
            throw new RuntimeException("Erro ao deletar arquivo: " + e);
        }
    }
    
}
