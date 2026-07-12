package br.com.vendas.test;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import br.com.vendas.dao.FuncionarioDAO;
import br.com.vendas.dao.VendaDAO;
import br.com.vendas.domain.Funcionario;
import br.com.vendas.domain.Venda;

public class VendaDAOTest {

    @Test
    @Ignore
    public void salvar() {
        FuncionarioDAO dao = new FuncionarioDAO();
        Funcionario funcionario = dao.buscarPorCodigo(4L);

        Venda f1 = new Venda();
        f1.setHorario(new Date());
        f1.setValorTotal(new BigDecimal(10.00D));
        f1.setFuncionario(funcionario);

        VendaDAO vendasdao = new VendaDAO();
        vendasdao.salvar(f1);
    }

    @Test
    @Ignore
    public void listar() {
        VendaDAO dao = new VendaDAO();
        List<Venda> vendas = dao.listar();
        System.out.println(vendas);
    }

    @Test
    @Ignore
    public void buscarPorCodigo() {
        VendaDAO dao = new VendaDAO();
        Venda f1 = dao.buscarPorCodigo(2L);
        System.out.println(f1);
    }

    @Test
    @Ignore
    public void excluir() {
        VendaDAO dao = new VendaDAO();
        Venda venda = dao.buscarPorCodigo(2L);
        dao.excluir(venda);
    }

    @Test
    @Ignore
    public void editar() {
        FuncionarioDAO fdao = new FuncionarioDAO();
        Funcionario funcionario = fdao.buscarPorCodigo(1L);

        VendaDAO dao = new VendaDAO();
        Venda f1 = dao.buscarPorCodigo(3L);
        f1.setHorario(new Date());
        f1.setValorTotal(new BigDecimal(20.00D));
        f1.setFuncionario(funcionario);
        dao.editar(f1);
    }
}
