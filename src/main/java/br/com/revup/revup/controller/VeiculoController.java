package br.com.revup.revup.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.querydsl.core.types.Predicate;

import br.com.revup.revup.controller.request.AlteracaoDonoRequest;
import br.com.revup.revup.controller.request.CriarVeiculoRequest;
import br.com.revup.revup.controller.request.EditarVeiculoRequest;
import br.com.revup.revup.entity.Veiculo;
import br.com.revup.revup.service.VeiculoService;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping("/veiculo")
public class VeiculoController {
    // Services
    @Autowired
    private VeiculoService veiculoService;

    // Operações Básicas
    @PostMapping
    public ResponseEntity<String> criarVeiculo (@RequestBody @Valid CriarVeiculoRequest dto) {
        veiculoService.criarVeiculo(dto.idUsuario(), dto.paraEntidade());
        
        return new ResponseEntity<String>(
            "Veiculo " + dto.marca() + " " + dto.modelo() + ", placa " + dto.placa() + " criado com sucesso.",
            HttpStatus.CREATED
        );
    }

    @GetMapping
    public ResponseEntity<Page<Veiculo>> listarVeiculos (Pageable pageable) {
        return new ResponseEntity<Page<Veiculo>>(
            veiculoService.listarVeiculos(pageable),
            HttpStatus.OK
        );
    }
    
    @GetMapping("/usuario/{id}")
    public ResponseEntity<Page<Veiculo>> listarVeiculosPorUsuario (@PathVariable long id, Pageable pageable) {
        return new ResponseEntity<Page<Veiculo>>(
            veiculoService.listarVeiculosPorUsuario(id, pageable),
            HttpStatus.OK
        );
    }

    @GetMapping("/filtro")
    public ResponseEntity<Page<Veiculo>> listarVeiculosFiltro (@QuerydslPredicate(root = Veiculo.class) Predicate predicate, Pageable pageable) {
        return new ResponseEntity<Page<Veiculo>>(
            veiculoService.listarVeiculosFiltro(predicate, pageable),
            HttpStatus.OK
        );
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<String> editarVeiculo (@PathVariable long id, @RequestBody @Valid EditarVeiculoRequest dto) {
        veiculoService.editarVeiculo(id, dto.paraEntidade());
        
        return new ResponseEntity<String>(
            "Veiculo " + dto.marca() + " " + dto.modelo() + " editado com sucesso.",
            HttpStatus.OK
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarVeiculo (@PathVariable long id) {
        veiculoService.deletarVeiculo(id);

        return new ResponseEntity<String>(
            "Veiculo com id " + id + " deletado com sucesso.",
            HttpStatus.OK
        );
    }

    // Dono
    @PostMapping("/{id}/alterarDono")
    public ResponseEntity<String> alterarDono (@PathVariable long id, @RequestBody @Valid AlteracaoDonoRequest dto) {
        veiculoService.alterarDono(id, dto.idAntigoDono(), dto.idNovoDono());
        
        return new ResponseEntity<String>(
            "Dono do veiculo com id " + id + " alterado com sucesso.",
            HttpStatus.OK
        );
    }
    
}
