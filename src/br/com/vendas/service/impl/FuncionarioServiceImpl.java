package br.com.vendas.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.vendas.dao.FuncionarioDAO;
import br.com.vendas.domain.Funcionario;
import br.com.vendas.exception.BusinessException;
import br.com.vendas.service.FuncionarioService;
import br.com.vendas.util.PasswordUtil;

public class FuncionarioServiceImpl implements FuncionarioService {

    private static final Logger logger = LoggerFactory.getLogger(FuncionarioServiceImpl.class);
    private final FuncionarioDAO funcionarioDAO;

    public FuncionarioServiceImpl() {
        this.funcionarioDAO = new FuncionarioDAO();
    }

    @Override
    public void salvar(Funcionario funcionario) {
        logger.info("Salvando funcionário: {}", funcionario.getNome());

        if (funcionario.getNome() == null || funcionario.getNome().trim().isEmpty()) {
            throw new BusinessException("O nome do funcionário é obrigatório.");
        }

        if (funcionario.getCpf() == null || funcionario.getCpf().trim().isEmpty()) {
            throw new BusinessException("O CPF do funcionário é obrigatório.");
        }

        funcionario.setSenha(PasswordUtil.criptografar(funcionario.getSenha()));
        funcionarioDAO.salvar(funcionario);
        logger.info("Funcionário salvo com sucesso: {}", funcionario.getCodigo());
    }

    @Override
    public void editar(Funcionario funcionario) {
        logger.info("Editando funcionário: {}", funcionario.getCodigo());

        if (funcionario.getCodigo() == null) {
            throw new BusinessException("Código do funcionário é obrigatório para edição.");
        }

        funcionarioDAO.editar(funcionario);
        logger.info("Funcionário editado com sucesso: {}", funcionario.getCodigo());
    }

    @Override
    public void excluir(Funcionario funcionario) {
        logger.info("Excluindo funcionário: {}", funcionario.getCodigo());

        if (funcionario.getCodigo() == null) {
            throw new BusinessException("Código do funcionário é obrigatório para exclusão.");
        }

        try {
            funcionarioDAO.excluir(funcionario);
            logger.info("Funcionário excluído com sucesso: {}", funcionario.getCodigo());
        } catch (RuntimeException e) {
            logger.error("Erro ao excluir funcionário {}: {}", funcionario.getCodigo(), e.getMessage());
            throw new BusinessException("Não é possível excluir um funcionário que tenha uma venda associada.", e);
        }
    }

    @Override
    public Funcionario buscarPorCodigo(Long codigo) {
        return funcionarioDAO.buscarPorCodigo(codigo);
    }

    @Override
    public List<Funcionario> listarTodos() {
        return funcionarioDAO.listar();
    }

    @Override
    public Funcionario autenticar(String cpf, String senha) {
        logger.info("Tentativa de autenticação para CPF: {}", cpf);

        List<Funcionario> funcionarios = funcionarioDAO.listar();

        String senhaCriptografada = PasswordUtil.criptografar(senha);

        for (Funcionario func : funcionarios) {
            if (func.getCpf().equals(cpf) && func.getSenha().equals(senhaCriptografada)) {
                logger.info("Autenticação bem-sucedida para funcionário: {}", func.getNome());
                return func;
            }
        }

        logger.warn("Falha na autenticação para CPF: {}", cpf);
        return null;
    }
}
