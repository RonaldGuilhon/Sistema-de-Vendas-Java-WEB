package br.com.vendas.service;

import java.util.List;

import br.com.vendas.domain.Produto;

public interface ProdutoService {

    void salvar(Produto produto);

    void editar(Produto produto);

    void excluir(Produto produto);

    Produto buscarPorCodigo(Long codigo);

    List<Produto> listarTodos();
}
