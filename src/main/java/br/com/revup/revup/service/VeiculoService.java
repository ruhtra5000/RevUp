package br.com.revup.revup.service;

import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.querydsl.core.types.Predicate;

import br.com.revup.revup.entity.Veiculo;

public interface VeiculoService {
    // Operações Básicas
    public void criarVeiculo(long idUsuario, Veiculo veiculo);
    public Page<Veiculo> listarVeiculos (Pageable pageable);
    public Page<Veiculo> listarVeiculosPorUsuario (long idUsuario, Pageable pageable);
    public Page<Veiculo> listarVeiculosFiltro (Predicate predicate, Pageable pageable);
    public void editarVeiculo(long idVeiculo, Veiculo veiculo);
    public void deletarVeiculo(long idVeiculo);
    // Imagens
    public void adicionarImagem (long idVeiculo, MultipartFile file);
    public List<Resource> listarImagensPorVeiculo (long idVeiculo);
    public void removerImagem(long idVeiculo, long idImagem);
    // Dono
    public void alterarDono(long idVeiculo, long idAntigoDono, long idNovoDono);
    // Consumo médio
    public void calcularConsumoMedio(long idVeiculo);

}
