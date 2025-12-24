package br.com.revup.revup.service.implementation;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.querydsl.core.types.Predicate;

import br.com.revup.revup.controller.request.AlteracaoSenhaRequest;
import br.com.revup.revup.controller.request.AutenticacaoRequest;
import br.com.revup.revup.controller.response.UsuarioResponse;
import br.com.revup.revup.entity.Usuario;
import br.com.revup.revup.entity.Veiculo;
import br.com.revup.revup.exceptions.CredenciaisInvalidasException;
import br.com.revup.revup.exceptions.RecursoDuplicadoException;
import br.com.revup.revup.exceptions.RecursoNaoEncontradoException;
import br.com.revup.revup.repository.UsuarioRepository;
import br.com.revup.revup.service.UsuarioService;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    // Repositórios
    @Autowired
    private UsuarioRepository usuarioRepository;

    // Outros
    @Autowired
    private PasswordEncoder passwordEncoder;

    // Métodos auxiliares
    private Usuario buscarUsuarioPorId(long idUsuario) {
        Optional<Usuario> usuarioBusca = usuarioRepository.findById(idUsuario);

        if (usuarioBusca.isEmpty())
            throw new RecursoNaoEncontradoException("O usuário com id " + idUsuario + " não existe.");

        return usuarioBusca.get();
    }

    private void verificarEmailDuplicado(String email) {
        Optional<Usuario> usuarioBusca = usuarioRepository.findByEmail(email);

        if (usuarioBusca.isPresent())
            throw new RecursoDuplicadoException("Um usuário com o e-mail " + email + " já se encontra cadastrado.");
    }

    private Page<UsuarioResponse> paraDTOPage (Page<Usuario> usuarios) {
        List<UsuarioResponse> dtos = usuarios.getContent()
            .stream()
            .map(UsuarioResponse::paraDTO)
            .toList();

        return new PageImpl<>(
            dtos, 
            usuarios.getPageable(), 
            usuarios.getTotalElements()
        );
    }

    private String codificarSenha (String senha) {
        return passwordEncoder.encode(senha);
    }

    // Operações Básicas
    @Override
    public void criarUsuario(Usuario usuario) {
        verificarEmailDuplicado(usuario.getEmail());
        
        usuario.setSenha(codificarSenha(usuario.getSenha()));
        
        usuarioRepository.save(usuario);
    }

    @Override
    public Page<UsuarioResponse> listarUsuarios(Pageable pageable) {
        return this.paraDTOPage(
            usuarioRepository.findAll(pageable)
        );
    }

    @Override
    public Page<UsuarioResponse> listarUsuariosFiltro(Predicate predicate, Pageable pageable) {
        return this.paraDTOPage(
            usuarioRepository.findAll(predicate, pageable)
        );
    }

    @Override
    public void editarUsuario(long idUsuario, Usuario usuario) {
        Usuario usuarioEdicao = buscarUsuarioPorId(idUsuario);

        //Atributos modificados
        usuarioEdicao.setNome(usuario.getNome());       //Nome
        
        if (!usuarioEdicao.getEmail().equals(usuario.getEmail())){
            verificarEmailDuplicado(usuario.getEmail());
            usuarioEdicao.setEmail(usuario.getEmail()); //E-mail
        }

        usuarioRepository.save(usuarioEdicao);
    }

    @Override
    public void alterarSenha(long idUsuario, AlteracaoSenhaRequest dto) {
        Usuario usuario = buscarUsuarioPorId(idUsuario);

        usuario.setSenha(codificarSenha(dto.senha()));

        usuarioRepository.save(usuario);
    } 

    @Override
    public void deletarUsuario(long idUsuario) {
        Usuario usuario = buscarUsuarioPorId(idUsuario);
        
        usuarioRepository.delete(usuario);
    }

    // Autenticação
    @Override
    public void autenticarUsuario(AutenticacaoRequest dto) {
        Optional<Usuario> usuario = usuarioRepository.findByEmail(dto.email());

        if (usuario.isEmpty())
            throw new CredenciaisInvalidasException("E-mail não cadastrado.");

        if (!passwordEncoder.matches(dto.senha(), usuario.get().getSenha()))
            throw new CredenciaisInvalidasException("Senha incorreta.");

        // Operações futuras de autenticação
    }

    // Veículos
    @Override
    public void adicionarVeiculo(long idUsuario, Veiculo veiculo) {
        Usuario usuario = buscarUsuarioPorId(idUsuario);
        
        usuario.adicionarVeiculo(veiculo);

        usuarioRepository.save(usuario);
    }

    @Override
    public void removerVeiculo(long idUsuario, Veiculo veiculo) {
        Usuario usuario = buscarUsuarioPorId(idUsuario);

        usuario.removerVeiculo(veiculo);

        usuarioRepository.save(usuario);
    }
}
