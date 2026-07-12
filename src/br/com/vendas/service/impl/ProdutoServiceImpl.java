package br.com.vendas.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.vendas.dao.ProdutoDAO;
import br.com.vendas.domain.Produto;
import br.com.vendas.exception.BusinessException;
import br.com.vendas.service.ProdutoService;

public class ProdutoServiceImpl implements ProdutoService {

    private static final Logger logger = LoggerFactory.getLogger(ProdutoServiceImpl.class);
    private final ProdutoDAO produtoDAO;

    public ProdutoServiceImpl() {
        this.produtoDAO = new ProdutoDAO();
    }

    @Override
    public void salvar(Produto produto) {
        logger.info("Salvando produto: {}", produto.getDescricao());

        if (produto.getDescricao() == null || produto.getDescricao().trim().isEmpty()) {
            throw new BusinessException("A descrição do produto é obrigatória.");
        }

        if (produto.getPreco() == null || produto.getPreco().doubleValue() < 0) {
            throw new BusinessException("O preço do produto deve ser maior ou igual a zero.");
        }

        if (produto.getQuantidade() == null || produto.getQuantidade() < 0) {
            throw new BusinessException("A quantidade do produto deve ser maior ou igual a zero.");
        }

        produtoDAO.salvar(produto);
        logger.info("Produto salvo com sucesso: {}", produto.getCodigo());
    }

    @Override
    public void editar(Produto produto) {
        logger.info("Editando produto: {}", produto.getCodigo());

        if (produto.getCodigo() == null) {
            throw new BusinessException("Código do produto é obrigatório para edição.");
        }

        produtoDAO.editar(produto);
        logger.info("Produto editado com sucesso: {}", produto.getCodigo());
    }

    @Override
    public void excluir(Produto produto) {
        logger.info("Excluindo produto: {}", produto.getCodigo());

        if (produto.getCodigo() == null) {
            throw new BusinessException("Código do produto é obrigatório para exclusão.");
        }

        produtoDAO.excluir(produto);
        logger.info("Produto excluído com sucesso: {}", produto.getCodigo());
    }

    @Override
    public Produto buscarPorCodigo(Long codigo) {
        return produtoDAO.buscarPorCodigo(codigo);
    }

    @Override
    public List<Produto> listarTodos() {
        return produtoDAO.listar();
    }
}
