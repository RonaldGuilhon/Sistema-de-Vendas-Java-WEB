package br.com.vendas.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.vendas.dao.FornecedorDAO;
import br.com.vendas.domain.Fornecedor;
import br.com.vendas.exception.BusinessException;
import br.com.vendas.service.FornecedorService;

public class FornecedorServiceImpl implements FornecedorService {

    private static final Logger logger = LoggerFactory.getLogger(FornecedorServiceImpl.class);
    private final FornecedorDAO fornecedorDAO;

    public FornecedorServiceImpl() {
        this.fornecedorDAO = new FornecedorDAO();
    }

    @Override
    public void salvar(Fornecedor fornecedor) {
        logger.info("Salvando fornecedor: {}", fornecedor.getDescricao());

        if (fornecedor.getDescricao() == null || fornecedor.getDescricao().trim().isEmpty()) {
            throw new BusinessException("A descrição do fornecedor é obrigatória.");
        }

        fornecedorDAO.salvar(fornecedor);
        logger.info("Fornecedor salvo com sucesso: {}", fornecedor.getCodigo());
    }

    @Override
    public void editar(Fornecedor fornecedor) {
        logger.info("Editando fornecedor: {}", fornecedor.getCodigo());

        if (fornecedor.getCodigo() == null) {
            throw new BusinessException("Código do fornecedor é obrigatório para edição.");
        }

        fornecedorDAO.editar(fornecedor);
        logger.info("Fornecedor editado com sucesso: {}", fornecedor.getCodigo());
    }

    @Override
    public void excluir(Fornecedor fornecedor) {
        logger.info("Excluindo fornecedor: {}", fornecedor.getCodigo());

        if (fornecedor.getCodigo() == null) {
            throw new BusinessException("Código do fornecedor é obrigatório para exclusão.");
        }

        try {
            fornecedorDAO.excluir(fornecedor);
            logger.info("Fornecedor excluído com sucesso: {}", fornecedor.getCodigo());
        } catch (RuntimeException e) {
            logger.error("Erro ao excluir fornecedor {}: {}", fornecedor.getCodigo(), e.getMessage());
            throw new BusinessException("Não é possível excluir um fornecedor que tenha um produto associado.", e);
        }
    }

    @Override
    public Fornecedor buscarPorCodigo(Long codigo) {
        return fornecedorDAO.buscarPorCodigo(codigo);
    }

    @Override
    public List<Fornecedor> listarTodos() {
        return fornecedorDAO.listar();
    }
}
