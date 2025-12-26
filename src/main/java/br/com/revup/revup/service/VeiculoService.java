package br.com.revup.revup.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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
    // Dono
    public void alterarDono(long idVeiculo, long idAntigoDono, long idNovoDono);
    // Consumo médio
    public void calcularConsumoMedio(long idVeiculo);

}
