package br.com.vendas.test;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import br.com.vendas.dao.ItemDAO;
import br.com.vendas.dao.ProdutoDAO;
import br.com.vendas.dao.VendaDAO;
import br.com.vendas.domain.Item;
import br.com.vendas.domain.Produto;
import br.com.vendas.domain.Venda;

public class ItemDAOTest {

    @Test
    @Ignore
    public void salvar() {
        ProdutoDAO dao = new ProdutoDAO();
        Produto produto = dao.buscarPorCodigo(1L);

        VendaDAO vdao = new VendaDAO();
        Venda venda = vdao.buscarPorCodigo(3L);

        Item f1 = new Item();
        f1.setQuantidade(8);
        f1.setValorParcial(new BigDecimal(150.99D));
        f1.setProduto(produto);
        f1.setVenda(venda);

        ItemDAO itensdao = new ItemDAO();
        itensdao.salvar(f1);
    }

    @Test
    @Ignore
    public void listar() {
        ItemDAO dao = new ItemDAO();
        List<Item> itens = dao.listar();
        System.out.println(itens);
    }

    @Test
    @Ignore
    public void buscarPorCodigo() {
        ItemDAO dao = new ItemDAO();
        Item f1 = dao.buscarPorCodigo(2L);
        System.out.println(f1);
    }

    @Test
    @Ignore
    public void excluir() {
        ItemDAO dao = new ItemDAO();
        Item item = dao.buscarPorCodigo(2L);
        dao.excluir(item);
    }

    @Test
    @Ignore
    public void editar() {
        ProdutoDAO pdao = new ProdutoDAO();
        Produto produto = pdao.buscarPorCodigo(3L);

        VendaDAO vdao = new VendaDAO();
        Venda venda = vdao.buscarPorCodigo(1L);

        ItemDAO dao = new ItemDAO();
        Item item = dao.buscarPorCodigo(3L);
        item.setValorParcial(new BigDecimal(5.99D));
        item.setQuantidade(2);
        item.setProduto(produto);
        item.setVenda(venda);
        dao.editar(item);
    }
}
