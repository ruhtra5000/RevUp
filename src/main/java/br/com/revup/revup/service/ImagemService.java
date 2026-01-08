package br.com.revup.revup.service;

import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface ImagemService {
    public String salvarImagem(MultipartFile file, String path);
    public List<Resource> listarImagem (List<String> paths);
    public void removerImagem (String path);
}
