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

import br.com.revup.revup.controller.request.CriarAbastecimentoRequest;
import br.com.revup.revup.controller.request.EditarAbastecimentoRequest;
import br.com.revup.revup.entity.Abastecimento;
import br.com.revup.revup.service.AbastecimentoService;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/abastecimento")
public class AbastecimentoController {
    // Service
    @Autowired
    private AbastecimentoService abastecimentoService;

    @PostMapping
    public ResponseEntity<String> criarAbastecimento (@RequestBody @Valid CriarAbastecimentoRequest dto) {
        abastecimentoService.criarAbastecimento(dto.paraEntidade());
        
        return new ResponseEntity<String>(
            "Abastecimento criado com sucesso.",
            HttpStatus.CREATED
        );
    }
    
    @GetMapping
    public ResponseEntity<Page<Abastecimento>> listarAbastecimentos (Pageable pageable) {
        return new ResponseEntity<Page<Abastecimento>>(
            abastecimentoService.listarAbastecimentos(pageable),
            HttpStatus.OK
        );
    }
    
    @GetMapping("/filtro")
    public ResponseEntity<Page<Abastecimento>> listarAbastecimentosFiltro (@QuerydslPredicate(root = Abastecimento.class) Predicate predicate, Pageable pageable) {
        return new ResponseEntity<Page<Abastecimento>>(
            abastecimentoService.listarAbastecimentosFiltro(predicate, pageable),
            HttpStatus.OK
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> editarAbastecimento (@PathVariable long id, @RequestBody @Valid EditarAbastecimentoRequest dto) {
        abastecimentoService.editarAbastecimento(id, dto.paraEntidade());
        
        return new ResponseEntity<String>(
            "Abastecimento editado com sucesso.",
            HttpStatus.OK
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarAbastecimento (@PathVariable long id) {
        abastecimentoService.deletarAbastecimento(id);
    
        return new ResponseEntity<String>(
            "Abastecimento deletado com sucesso.",
            HttpStatus.OK
        );
    }
}
