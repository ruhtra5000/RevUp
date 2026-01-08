package br.com.revup.revup.service.implementation;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.querydsl.core.types.Predicate;

import br.com.revup.revup.controller.response.ImagemResponse;
import br.com.revup.revup.entity.Abastecimento;
import br.com.revup.revup.entity.Imagem;
import br.com.revup.revup.entity.Veiculo;
import br.com.revup.revup.exceptions.RecursoDuplicadoException;
import br.com.revup.revup.exceptions.RecursoNaoEncontradoException;
import br.com.revup.revup.repository.AbastecimentoRepository;
import br.com.revup.revup.repository.VeiculoRepository;
import br.com.revup.revup.service.ImagemService;
import br.com.revup.revup.service.UsuarioService;
import br.com.revup.revup.service.VeiculoService;
import io.github.cdimascio.dotenv.Dotenv;

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

    @Autowired
    private ImagemService imagemService;

    // Dotenv
    private Dotenv dotenv = Dotenv.load();

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

    private String gerarPathImagemVeiculoDiretorio () {
        String path = dotenv.get("STORAGE_DIR");    // Diretório
        path = path + "/veiculo";                       // Sub-diretório

        return path;
    }

    private String gerarPathImagemVeiculoCompleto (String nomeArquivo) {
        String path = gerarPathImagemVeiculoDiretorio();    // Diretório completo
        path = path + "/" + nomeArquivo;                    // Nome do arquivo

        return path;
    }

    private List<ImagemResponse> paraDTOList (List<Imagem> imagens, List<Resource> recursos) {
        List<ImagemResponse> response = new ArrayList<>();
        
        for (int i = 0; i < imagens.size(); i++) 
            response.add(ImagemResponse.paraDTO(imagens.get(i), recursos.get(i)));
        
        return response;
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
    @Transactional
    public void deletarVeiculo(long idVeiculo) {
        Veiculo veiculo = buscarVeiculoPorId(idVeiculo);

        long idUsuario = veiculoRepository.findUsuarioIdByVeiculoId(idVeiculo);

        usuarioService.removerVeiculo(idUsuario, veiculo);

        veiculoRepository.delete(veiculo);
    }

    // Imagens
    @Override
    @Transactional
    public void adicionarImagem(long idVeiculo, MultipartFile file) {
        Veiculo veiculo = buscarVeiculoPorId(idVeiculo);

        String nomeArquivo = imagemService.salvarImagem(file, gerarPathImagemVeiculoDiretorio());

        Imagem imagem = new Imagem(
            gerarPathImagemVeiculoCompleto(nomeArquivo),
            nomeArquivo,
            file.getContentType()
        );

        veiculo.adicionarImagem(imagem);
        veiculoRepository.save(veiculo);
    }

    @Override
    public List<ImagemResponse> listarImagensPorVeiculo(long idVeiculo) {
        Veiculo veiculo = buscarVeiculoPorId(idVeiculo);
        List<String> paths = new ArrayList<String>();
        
        for (Imagem imagem : veiculo.getImagens()) 
            paths.add(imagem.getPath());
        
        List<Resource> recursos = imagemService.listarImagem(paths);

        return paraDTOList(veiculo.getImagens(), recursos);
    }

    @Override
    @Transactional
    public void removerImagem(long idVeiculo, long idImagem) {
        Veiculo veiculo = buscarVeiculoPorId(idVeiculo);

        for (Imagem imagem : veiculo.getImagens()) {
            if (imagem.getId() == idImagem) {
                imagemService.removerImagem(imagem.getPath());
                veiculo.removerImagem(imagem);
                veiculoRepository.save(veiculo);
                return;
            }
        }

        throw new RecursoNaoEncontradoException(
            "A imagem com id " + idImagem + 
            " não existe ou não está vinculada ao veículo com placa " + 
            veiculo.getPlaca());
    }

    // Dono
    @Override
    @Transactional
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
                somaKmPorLitro = somaKmPorLitro.add(abastecimento.getKmPorLitro());
            
            BigDecimal tamanho = new BigDecimal(abastecimentos.size());
            
            veiculo.setConsumoMedio(somaKmPorLitro.divide(tamanho, 2, RoundingMode.HALF_UP));
        }

        veiculoRepository.save(veiculo);
    }
    
}
