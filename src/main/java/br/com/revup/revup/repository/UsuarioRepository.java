package br.com.revup.revup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.StringPath;

import br.com.revup.revup.entity.QUsuario;
import br.com.revup.revup.entity.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>,
                                           QuerydslPredicateExecutor<Usuario>,
                                           QuerydslBinderCustomizer<QUsuario>
{
    @Override
    default void customize(QuerydslBindings bindings, QUsuario root) {
        // Bind para ID
        bindings.bind(root.id)
                .as("id")
                .first((NumberPath<Long> path, Long value) -> path.eq(value));

        // Bind para nome
        bindings.bind(root.nome)
                .as("nome")
                .first((StringPath path, String value) -> path.containsIgnoreCase(value));

        // Bind para e-mail
        bindings.bind(root.email)
                .as("email")
                .first((StringPath path, String value) -> path.containsIgnoreCase(value));

        // Bind para CPF
        bindings.bind(root.cpf)
                .as("cpf")
                .first((StringPath path, String value) -> path.containsIgnoreCase(value));

        bindings.excludeUnlistedProperties(true);
    }
}
