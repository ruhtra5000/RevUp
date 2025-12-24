package br.com.revup.revup.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.querydsl.core.types.Predicate;

import br.com.revup.revup.controller.request.AlteracaoSenhaRequest;
import br.com.revup.revup.controller.request.AutenticacaoRequest;
import br.com.revup.revup.controller.response.UsuarioResponse;
import br.com.revup.revup.entity.Usuario;
import br.com.revup.revup.entity.Veiculo;

public interface UsuarioService {
    // Operações Básicas
    public void criarUsuario (Usuario usuario);
    public Page<UsuarioResponse> listarUsuarios (Pageable pageable);
    public Page<UsuarioResponse> listarUsuariosFiltro (Predicate predicate, Pageable pageable);
    public void editarUsuario (long idUsuario, Usuario usuario);
    public void alterarSenha (long idUsuario, AlteracaoSenhaRequest dto);
    public void deletarUsuario (long idUsuario);

    // Autenticação
    public void autenticarUsuario(AutenticacaoRequest dto);

    // Privilegios
    // public void alterarRole (long idUsuario);

    // Veiculos
    public void adicionarVeiculo (long idUsuario, Veiculo veiculo);
    public void removerVeiculo (long idUsuario, Veiculo veiculo);
}
