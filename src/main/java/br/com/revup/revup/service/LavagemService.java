package br.com.revup.revup.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.querydsl.core.types.Predicate;

import br.com.revup.revup.entity.Lavagem;

public interface LavagemService {
    public void criarLavagem (Lavagem lavagem);
    public Page<Lavagem> listarLavagens (Pageable pageable);
    public Page<Lavagem> listarLavagensFiltro (Predicate predicate, Pageable pageable);
    public void editarLavagem (long idLavagem, Lavagem lavagem);
    public void deletarLavagem (long idLavagem);
}
