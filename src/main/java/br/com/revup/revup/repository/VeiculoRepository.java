package br.com.revup.revup.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.StringPath;

import br.com.revup.revup.entity.QVeiculo;
import br.com.revup.revup.entity.Veiculo;

@Repository
public interface VeiculoRepository extends JpaRepository<Veiculo, Long>,
                                           QuerydslPredicateExecutor<Veiculo>,
                                           QuerydslBinderCustomizer<QVeiculo>
{

    @NativeQuery("SELECT v.* " + 
                 "FROM veiculo v " + 
                 "JOIN usuario_veiculos uv ON uv.veiculo_id = v.id " +
                 "WHERE uv.usuario_id = :idUsuario"
    )
    Page<Veiculo> findVeiculosByUsuarioId(@Param("idUsuario") long idUsuario, Pageable pageable);

    @Override
    default void customize(QuerydslBindings bindings, QVeiculo root) {
        // Bind para ID
        bindings.bind(root.id)
                .first((NumberPath<Long> path, Long value) -> path.eq(value));

        // Bind para placa
        bindings.bind(root.placa)
                .first((StringPath path, String value) -> path.containsIgnoreCase(value));

        // Bind para número do chassi
        bindings.bind(root.chassi)
                .first((StringPath path, String value) -> path.containsIgnoreCase(value));
        
        // Bind para renavam
        bindings.bind(root.renavam)
                .first((StringPath path, String value) -> path.containsIgnoreCase(value));
    }
}
