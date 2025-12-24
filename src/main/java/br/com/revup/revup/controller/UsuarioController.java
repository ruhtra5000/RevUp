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

import br.com.revup.revup.controller.request.AlteracaoSenhaRequest;
import br.com.revup.revup.controller.request.AutenticacaoRequest;
import br.com.revup.revup.controller.request.CriarUsuarioRequest;
import br.com.revup.revup.controller.request.EditarUsuarioRequest;
import br.com.revup.revup.controller.response.UsuarioResponse;
import br.com.revup.revup.entity.Usuario;
import br.com.revup.revup.service.UsuarioService;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/usuario")
public class UsuarioController {
    
    // Services
    @Autowired
    private UsuarioService usuarioService;

    // Operações Básicas
    @PostMapping
    public ResponseEntity<String> criarUsuario (@RequestBody @Valid CriarUsuarioRequest dto) {
        usuarioService.criarUsuario(dto.paraEntidade());
        
        return new ResponseEntity<String>(
            "Usuário " + dto.nome() + " criado com sucesso!",
            HttpStatus.CREATED
        );
    }
    
    @GetMapping
    public ResponseEntity<Page<UsuarioResponse>> listarUsuarios (Pageable pageable) {
        return new ResponseEntity<Page<UsuarioResponse>>(
            usuarioService.listarUsuarios(pageable),
            HttpStatus.OK
        );
    }

    // CONSERTAR ROTA DE FILTRO
    @GetMapping("/filtro") 
    public ResponseEntity<Page<UsuarioResponse>> listarUsuariosFiltro (@QuerydslPredicate(root = Usuario.class) Predicate predicate, Pageable pageable) {
        return new ResponseEntity<Page<UsuarioResponse>>(
            usuarioService.listarUsuariosFiltro(predicate, pageable),
            HttpStatus.OK
        );
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<String> editarUsuario (@PathVariable long id, @RequestBody @Valid EditarUsuarioRequest dto) {
        usuarioService.editarUsuario(id, dto.paraEntidade());
        
        return new ResponseEntity<String>(
            "Usuário " + dto.nome() + " editado com sucesso!",
            HttpStatus.OK
        );
    }

    @PatchMapping("/senha/{id}")
    public ResponseEntity<String> alterarSenhaUsuario (@PathVariable long id, @RequestBody @Valid AlteracaoSenhaRequest dto) {
        usuarioService.alterarSenha(id, dto);
        
        return new ResponseEntity<String>(
            "Senha alterada com sucesso!",
            HttpStatus.OK
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarUsuario (@PathVariable long id) {
        usuarioService.deletarUsuario(id);
        
        return new ResponseEntity<String>(
            "Usuário com id " + id + " deletado com sucesso!",
            HttpStatus.OK
        );
    }

    // Autenticação
    @PostMapping("/autenticar")
    public ResponseEntity<String> autenticarUsuario (@RequestBody AutenticacaoRequest dto) {
        usuarioService.autenticarUsuario(dto);
        
        return new ResponseEntity<String>(
            "Usuário com e-mail " + dto.email() + " pseudo-autenticado com sucesso!",
            HttpStatus.OK
        );
    }
    
}
