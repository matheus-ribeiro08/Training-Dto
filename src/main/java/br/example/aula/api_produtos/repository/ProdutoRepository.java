package br.example.aula.api_produtos.repository;

import br.example.aula.api_produtos.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repositório responsável pelo acesso dos dados de produtos
 * */

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    /**
     * Verifica se existe produto com o nome informado
     * Ignora diferença entre letras maiúsculas e minúsculas
     * @param nome nome a ser pesquisado
     * @return {@code true} caso o produto exista com o nome
     * */
    boolean existsByNomeIgnoreCase(String nome);

    /**
     * Busca produtos cujo nome contenha o texto informado
     * @param nome parte do nome do produto
     * @return produtos encontrados
     * */
    List<Produto> findByNomeContainingIgnoreCase(String nome);

}
