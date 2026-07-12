package br.com.vendas.main;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.vendas.dao.FuncionarioDAO;
import br.com.vendas.domain.Funcionario;
import br.com.vendas.util.HibernateUtil;
import br.com.vendas.util.PasswordUtil;

public class SeedAdmin {

    private static final Logger logger = LoggerFactory.getLogger(SeedAdmin.class);

    public static void main(String[] args) {
        logger.info("Iniciando verificação de admin padrão...");

        try {
            FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
            List<Funcionario> funcionarios = funcionarioDAO.listar();

            boolean adminExiste = false;
            for (Funcionario func : funcionarios) {
                if ("Administrador".equals(func.getFuncao())) {
                    adminExiste = true;
                    logger.info("Administrador já existe: {} (CPF: {})", func.getNome(), func.getCpf());
                    break;
                }
            }

            if (!adminExiste) {
                Funcionario admin = new Funcionario();
                admin.setNome("Administrador");
                admin.setCpf("000.000.000-00");
                admin.setSenha(PasswordUtil.criptografar("admin123"));
                admin.setFuncao("Administrador");

                funcionarioDAO.salvar(admin);
                logger.info("Administrador padrão criado com sucesso!");
                logger.info("CPF: 000.000.000-00");
                logger.info("Senha: admin123");
            } else {
                logger.info("Administrador já existe. Nenhuma ação necessária.");
            }
        } catch (Exception e) {
            logger.error("Erro ao criar admin padrão: {}", e.getMessage(), e);
        } finally {
            HibernateUtil.getSessionFactory().close();
        }
    }
}
