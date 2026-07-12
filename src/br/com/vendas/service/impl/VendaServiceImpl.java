package br.com.vendas.service.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.OptimisticLockException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.vendas.dao.ItemDAO;
import br.com.vendas.dao.ProdutoDAO;
import br.com.vendas.dao.VendaDAO;
import br.com.vendas.domain.Item;
import br.com.vendas.domain.Produto;
import br.com.vendas.domain.Venda;
import br.com.vendas.exception.BusinessException;
import br.com.vendas.service.VendaService;

public class VendaServiceImpl implements VendaService {

    private static final Logger logger = LoggerFactory.getLogger(VendaServiceImpl.class);
    private final VendaDAO vendaDAO;
    private final ItemDAO itemDAO;
    private final ProdutoDAO produtoDAO;

    public VendaServiceImpl() {
        this.vendaDAO = new VendaDAO();
        this.itemDAO = new ItemDAO();
        this.produtoDAO = new ProdutoDAO();
    }

    @Override
    public void finalizarVenda(Venda venda, List<Item> itens) {
        logger.info("Iniciando finalização de venda");

        if (venda.getFuncionario() == null) {
            throw new BusinessException("É necessário informar o funcionário para finalizar a venda.");
        }

        if (itens == null || itens.isEmpty()) {
            throw new BusinessException("É necessário adicionar pelo menos um item à venda.");
        }

        for (Item item : itens) {
            Produto produto = item.getProduto();
            if (produto.getQuantidade() < item.getQuantidade()) {
                throw new BusinessException("Estoque insuficiente para o produto: " + produto.getDescricao()
                        + ". Disponível: " + produto.getQuantidade() + ", Solicitado: " + item.getQuantidade());
            }
        }

        Long codigoVenda = vendaDAO.salvar(venda);
        Venda vendaSalva = vendaDAO.buscarPorCodigo(codigoVenda);

        for (Item item : itens) {
            item.setVenda(vendaSalva);
            itemDAO.salvar(item);

            Produto produto = item.getProduto();
            int novaQuantidade = produto.getQuantidade() - item.getQuantidade();
            produto.setQuantidade(novaQuantidade);
            try {
                produtoDAO.editar(produto);
            } catch (OptimisticLockException e) {
                throw new BusinessException("O produto " + produto.getDescricao()
                        + " foi alterado por outro usuário. Atualize a página e tente novamente.");
            }

            logger.info("Estoque atualizado - Produto: {}, Nova quantidade: {}", produto.getDescricao(), novaQuantidade);
        }

        logger.info("Venda finalizada com sucesso. Código: {}, Valor Total: {}", codigoVenda, venda.getValorTotal());
    }

    @Override
    public Venda buscarPorCodigo(Long codigo) {
        return vendaDAO.buscarPorCodigo(codigo);
    }

    @Override
    public List<Venda> listarTodas() {
        return vendaDAO.listar();
    }

    @Override
    public List<Venda> listarPorPeriodo(Date dataInicio, Date dataFim) {
        return vendaDAO.listarPorPeriodo(dataInicio, dataFim);
    }
}
