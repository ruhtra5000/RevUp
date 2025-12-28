package br.com.revup.revup.service.implementation;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.querydsl.core.types.Predicate;

import br.com.revup.revup.entity.Abastecimento;
import br.com.revup.revup.exceptions.RecursoNaoEncontradoException;
import br.com.revup.revup.repository.AbastecimentoRepository;
import br.com.revup.revup.service.AbastecimentoService;
import br.com.revup.revup.service.VeiculoService;

@Service
public class AbastecimentoServiceImpl implements AbastecimentoService {
    // Repositórios
    @Autowired
    private AbastecimentoRepository abastecimentoRepository;

    // Outros Services
    @Autowired
    private VeiculoService veiculoService;

    // Métodos auxiliares
    private Abastecimento buscarAbastecimentoPorId(long idAbastecimento) {
        Optional<Abastecimento> abastecimento = abastecimentoRepository.findById(idAbastecimento);

        if (abastecimento.isEmpty())
            throw new RecursoNaoEncontradoException("O abastecimento com id " + idAbastecimento + " não existe.");

        return abastecimento.get();
    }

    // Métodos da interface
    @Override
    public void criarAbastecimento(Abastecimento abastecimento) {
        // Se constar km rodados com o tanque, calcular consumo e salvar
        if (abastecimento.getKmComTanque() != null) {
            abastecimento.calcularKmPorLitro();
            abastecimentoRepository.save(abastecimento);
            veiculoService.calcularConsumoMedio(abastecimento.getVeiculo().getId());   
        }
        // Se não constar, somente salvar
        else {
            abastecimentoRepository.save(abastecimento);
        }
    }

    @Override
    public Page<Abastecimento> listarAbastecimentos(Pageable pageable) {
        return abastecimentoRepository.findAll(pageable);
    }

    @Override
    public Page<Abastecimento> listarAbastecimentosFiltro(Predicate predicate, Pageable pageable) {
        return abastecimentoRepository.findAll(predicate, pageable);
    }

    @Override
    public void editarAbastecimento(long idAbastecimento, Abastecimento abastecimento) {
        Abastecimento abastecimentoEdicao = buscarAbastecimentoPorId(idAbastecimento);

        // Atributos modificados
        abastecimentoEdicao.setCombustivel(abastecimento.getCombustivel());                 //Combustivel
        abastecimentoEdicao.setLitros(abastecimento.getLitros());                           //Litros
        abastecimentoEdicao.setData(abastecimento.getData());                               //Data
        
        if (!abastecimentoEdicao.getKmComTanque().equals(abastecimento.getKmComTanque())) { //KmComTanque
            abastecimentoEdicao.setKmComTanque(abastecimento.getKmComTanque());
            veiculoService.calcularConsumoMedio(abastecimentoEdicao.getVeiculo().getId());
        }
    }

    @Override
    public void deletarAbastecimento(long idAbastecimento) {
        Abastecimento abastecimento = buscarAbastecimentoPorId(idAbastecimento);

        long idVeiculo = abastecimento.getVeiculo().getId();

        abastecimentoRepository.delete(abastecimento);

        veiculoService.calcularConsumoMedio(idVeiculo);
    }
    
}
