package br.com.revup.revup.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.querydsl.core.types.Predicate;

import br.com.revup.revup.entity.Abastecimento;

public interface AbastecimentoService {
    public void criarAbastecimento (Abastecimento abastecimento);
    public Page<Abastecimento> listarAbastecimentos (Pageable pageable);
    public Page<Abastecimento> listarAbastecimentosFiltro (Predicate predicate, Pageable pageable);
    public void editarAbastecimento (long idAbastecimento, Abastecimento abastecimento);
    public void deletarAbastecimento (long idAbastecimento);
}
