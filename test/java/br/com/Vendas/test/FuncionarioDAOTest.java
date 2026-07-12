package br.com.vendas.test;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import br.com.vendas.dao.FuncionarioDAO;
import br.com.vendas.domain.Funcionario;

public class FuncionarioDAOTest {

    @Test
    @Ignore
    public void salvar() {
        Funcionario f1 = new Funcionario();
        f1.setNome("Pedro Freitas");
        f1.setCpf("07014588936");
        f1.setSenha("123");
        f1.setFuncao("funcionário");

        FuncionarioDAO dao = new FuncionarioDAO();
        dao.salvar(f1);
    }

    @Test
    @Ignore
    public void listar() {
        FuncionarioDAO dao = new FuncionarioDAO();
        List<Funcionario> funcionarios = dao.listar();

        for (Funcionario funcionario : funcionarios) {
            System.out.println(funcionario);
        }
    }

    @Test
    @Ignore
    public void buscarPorCodigo() {
        FuncionarioDAO dao = new FuncionarioDAO();
        Funcionario f1 = dao.buscarPorCodigo(6L);
        Funcionario f2 = dao.buscarPorCodigo(4L);

        System.out.println(f1);
        System.out.println(f2);
    }

    @Test
    @Ignore
    public void excluir() {
        FuncionarioDAO dao = new FuncionarioDAO();
        Funcionario funcionario = dao.buscarPorCodigo(5L);
        dao.excluir(funcionario);
    }

    @Test
    @Ignore
    public void editar() {
        FuncionarioDAO dao = new FuncionarioDAO();
        Funcionario funcionario = dao.buscarPorCodigo(4L);
        funcionario.setNome("Paula Campos");
        funcionario.setSenha("456");
        funcionario.setCpf("0646454");
        funcionario.setFuncao("Gerente");
        dao.editar(funcionario);
    }
}
