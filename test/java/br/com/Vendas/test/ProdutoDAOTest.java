package br.com.vendas.test;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import br.com.vendas.dao.FornecedorDAO;
import br.com.vendas.dao.ProdutoDAO;
import br.com.vendas.domain.Fornecedor;
import br.com.vendas.domain.Produto;

public class ProdutoDAOTest {

    @Test
    @Ignore
    public void salvar() {
        FornecedorDAO dao = new FornecedorDAO();
        Fornecedor fornecedor = dao.buscarPorCodigo(8L);

        Produto f1 = new Produto();
        f1.setDescricao("Tomate");
        f1.setPreco(new BigDecimal(12.99D));
        f1.setQuantidade(4);
        f1.setFornecedor(fornecedor);

        ProdutoDAO produtosdao = new ProdutoDAO();
        produtosdao.salvar(f1);
    }

    @Test
    @Ignore
    public void listar() {
        ProdutoDAO dao = new ProdutoDAO();
        List<Produto> produtos = dao.listar();
        System.out.println(produtos);
    }

    @Test
    @Ignore
    public void buscarPorCodigo() {
        ProdutoDAO dao = new ProdutoDAO();
        Produto f1 = dao.buscarPorCodigo(2L);
        System.out.println(f1);
    }

    @Test
    @Ignore
    public void excluir() {
        ProdutoDAO dao = new ProdutoDAO();
        Produto produto = dao.buscarPorCodigo(2L);
        dao.excluir(produto);
    }

    @Test
    @Ignore
    public void editar() {
        FornecedorDAO fdao = new FornecedorDAO();
        Fornecedor fornecedor = fdao.buscarPorCodigo(6L);

        ProdutoDAO dao = new ProdutoDAO();
        Produto produto = dao.buscarPorCodigo(3L);
        produto.setDescricao("Tomate");
        produto.setPreco(new BigDecimal(10.99D));
        produto.setQuantidade(2);
        produto.setFornecedor(fornecedor);
        dao.editar(produto);
    }
}
