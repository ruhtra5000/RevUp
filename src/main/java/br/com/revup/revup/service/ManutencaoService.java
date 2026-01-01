package br.com.revup.revup.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.querydsl.core.types.Predicate;

import br.com.revup.revup.entity.Manutencao;

public interface ManutencaoService {
    public void criarManutencao(Manutencao manutencao);
    public Page<Manutencao> listarManutencoes (Pageable pageable);
    public Page<Manutencao> listarManutencoesFiltro (Predicate predicate, Pageable pageable);
    public void editarManutencao(long idManutencao, Manutencao manutencao);
    public void deletarManutencao(long idManutencao);
}
