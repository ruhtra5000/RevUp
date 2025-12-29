package br.com.revup.revup.service.implementation;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.querydsl.core.types.Predicate;

import br.com.revup.revup.entity.Abastecimento;
import br.com.revup.revup.entity.Veiculo;
import br.com.revup.revup.exceptions.RecursoDuplicadoException;
import br.com.revup.revup.exceptions.RecursoNaoEncontradoException;
import br.com.revup.revup.repository.AbastecimentoRepository;
import br.com.revup.revup.repository.VeiculoRepository;
import br.com.revup.revup.service.UsuarioService;
import br.com.revup.revup.service.VeiculoService;

@Service
public class VeiculoServiceImpl implements VeiculoService {
    // Repositórios
    @Autowired
    private VeiculoRepository veiculoRepository;

    @Autowired
    private AbastecimentoRepository abastecimentoRepository;

    // Outros Services
    @Autowired 
    private UsuarioService usuarioService;

    // Métodos Auxiliares
    private Veiculo buscarVeiculoPorId(long idVeiculo) {
        Optional<Veiculo> veiculo = veiculoRepository.findById(idVeiculo);

        if (veiculo.isEmpty()) 
            throw new RecursoNaoEncontradoException("O veículo com id " + idVeiculo + " não existe.");

        return veiculo.get();
    }

    private void verificarPlacaChassiRenavam(Veiculo veiculo) {
        String placa = veiculo.getPlaca();
        String chassi = veiculo.getChassi();
        String renavam = veiculo.getRenavam();

        if (veiculoRepository.findByPlaca(placa).isPresent()) 
            throw new RecursoDuplicadoException("A placa " + placa + " já está associada a outro veículo.");

        if (veiculoRepository.findByChassi(chassi).isPresent())
            throw new RecursoDuplicadoException("O número de chassi " + chassi + " já pertence a outro veículo.");

        if (veiculoRepository.findByRenavam(renavam).isPresent())
            throw new RecursoDuplicadoException("O RENAVAM " + renavam + " já está associado a outro veículo.");
    }

    // Operações Básicas
    @Override
    public void criarVeiculo(long idUsuario, Veiculo veiculo) {
        verificarPlacaChassiRenavam(veiculo);

        veiculoRepository.save(veiculo);

        usuarioService.adicionarVeiculo(idUsuario, veiculo);
    }

    @Override
    public Page<Veiculo> listarVeiculos(Pageable pageable) {
        return veiculoRepository.findAll(pageable);
    }

    @Override
    public Page<Veiculo> listarVeiculosPorUsuario(long idUsuario, Pageable pageable) {
        return veiculoRepository.findAllVeiculosByUsuarioId(idUsuario, pageable);
    }

    @Override
    public Page<Veiculo> listarVeiculosFiltro(Predicate predicate, Pageable pageable) {
        return veiculoRepository.findAll(predicate, pageable);
    }

    @Override
    public void editarVeiculo(long idVeiculo, Veiculo veiculo) {
        Veiculo veiculoEdicao = buscarVeiculoPorId(idVeiculo);

        // Atributos modificados pela edição
        veiculoEdicao.setMarca(veiculo.getMarca());     //Marca
        veiculoEdicao.setModelo(veiculo.getModelo());   //Modelo
        veiculoEdicao.setAno(veiculo.getAno());         //Ano
        veiculoEdicao.setCor(veiculo.getCor());         //Cor

        veiculoRepository.save(veiculoEdicao);
    }

    @Override
    public void deletarVeiculo(long idVeiculo) {
        Veiculo veiculo = buscarVeiculoPorId(idVeiculo);

        long idUsuario = veiculoRepository.findUsuarioIdByVeiculoId(idVeiculo);

        usuarioService.removerVeiculo(idUsuario, veiculo);

        veiculoRepository.delete(veiculo);
    }

    // Dono
    @Override
    public void alterarDono(long idVeiculo, long idAntigoDono, long idNovoDono) {
        Veiculo veiculo = buscarVeiculoPorId(idVeiculo);

        usuarioService.removerVeiculo(idAntigoDono, veiculo);
        usuarioService.adicionarVeiculo(idNovoDono, veiculo);
    }

    // Consumo Médio
    @Override
    public void calcularConsumoMedio(long idVeiculo) {
        Veiculo veiculo = buscarVeiculoPorId(idVeiculo);

        List<Abastecimento> abastecimentos = abastecimentoRepository.findAllByVeiculoId(idVeiculo);

        if (abastecimentos.isEmpty()) {
            veiculo.setConsumoMedio(BigDecimal.ZERO);
        }
        else {
            BigDecimal somaKmPorLitro = BigDecimal.ZERO;
            for (Abastecimento abastecimento : abastecimentos) 
                somaKmPorLitro.add(abastecimento.getKmPorLitro());
            
            BigDecimal tamanho = new BigDecimal(abastecimentos.size());
            
            veiculo.setConsumoMedio(somaKmPorLitro.divide(tamanho, 2, RoundingMode.HALF_UP));
        }

        veiculoRepository.save(veiculo);
    }
    
}
