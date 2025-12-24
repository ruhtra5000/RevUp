package br.com.revup.revup.repository;

import java.time.LocalDate;
import java.util.Iterator;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.dsl.NumberPath;

import br.com.revup.revup.entity.Abastecimento;
import br.com.revup.revup.entity.QAbastecimento;

@Repository
public interface AbastecimentoRepository extends JpaRepository<Abastecimento, Long>, 
                                                 QuerydslPredicateExecutor<Abastecimento>,
                                                 QuerydslBinderCustomizer<QAbastecimento>
{
    @Override
    default void customize(QuerydslBindings bindings, QAbastecimento root) {
        // Bind para ID
        bindings.bind(root.id)
                .as("id")
                .first((NumberPath<Long> path, Long value) -> path.eq(value));

        // Bind para Veiculo (ID)
        bindings.bind(root.veiculo.id)
                .as("veiculo")
                .first((NumberPath<Long> path, Long value) -> path.eq(value));

        // Bind para data
        bindings.bind(root.data)
                .as("data")
                .all((path, values) -> {
                    Iterator<? extends LocalDate> it = values.iterator();

                    LocalDate inicio = it.hasNext() ? it.next() : null;
                    LocalDate fim    = it.hasNext() ? it.next() : null;

                    if (inicio != null && fim != null) 
                        return Optional.of(path.between(inicio, fim));

                    if (inicio != null) 
                        return Optional.of(path.goe(inicio));

                    if (fim != null) 
                        return Optional.of(path.loe(fim));

                    return Optional.empty();
                });

        bindings.excludeUnlistedProperties(true);
    }
}
