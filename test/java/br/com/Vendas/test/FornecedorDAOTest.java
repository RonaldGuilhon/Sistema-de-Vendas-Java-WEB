package br.com.vendas.test;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import br.com.vendas.dao.FornecedorDAO;
import br.com.vendas.domain.Fornecedor;

public class FornecedorDAOTest {

    @Test
    @Ignore
    public void salvar() {
        Fornecedor f1 = new Fornecedor();
        f1.setDescricao("Ronald");

        FornecedorDAO dao = new FornecedorDAO();
        dao.salvar(f1);
    }

    @Test
    @Ignore
    public void listar() {
        FornecedorDAO dao = new FornecedorDAO();
        List<Fornecedor> fornecedores = dao.listar();

        for (Fornecedor fornecedor : fornecedores) {
            System.out.println(fornecedor);
        }
    }

    @Test
    @Ignore
    public void buscarPorCodigo() {
        FornecedorDAO dao = new FornecedorDAO();
        Fornecedor f1 = dao.buscarPorCodigo(6L);
        Fornecedor f2 = dao.buscarPorCodigo(4L);

        System.out.println(f1);
        System.out.println(f2);
    }

    @Test
    @Ignore
    public void excluir() {
        FornecedorDAO dao = new FornecedorDAO();
        Fornecedor fornecedor = dao.buscarPorCodigo(5L);
        dao.excluir(fornecedor);
    }

    @Test
    @Ignore
    public void editar() {
        FornecedorDAO dao = new FornecedorDAO();
        Fornecedor fornecedor = dao.buscarPorCodigo(7L);
        fornecedor.setDescricao("Paula Campos");
        dao.editar(fornecedor);
    }
}
