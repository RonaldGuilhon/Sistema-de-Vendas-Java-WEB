package br.com.vendas.service;

import java.util.List;

import br.com.vendas.domain.Fornecedor;

public interface FornecedorService {

    void salvar(Fornecedor fornecedor);

    void editar(Fornecedor fornecedor);

    void excluir(Fornecedor fornecedor);

    Fornecedor buscarPorCodigo(Long codigo);

    List<Fornecedor> listarTodos();
}
