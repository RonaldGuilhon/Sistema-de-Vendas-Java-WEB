package br.com.vendas.util;

import java.util.List;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.vendas.dao.FuncionarioDAO;
import br.com.vendas.domain.Funcionario;

public class ContextListener implements ServletContextListener {

    private static final Logger logger = LoggerFactory.getLogger(ContextListener.class);

    @Override
    public void contextInitialized(ServletContextEvent arg0) {
        logger.info("Inicializando Hibernate...");
        HibernateUtil.getSessionFactory();

        try {
            seedAdmin();
        } catch (Exception e) {
            logger.error("Erro ao criar admin padrão: {}", e.getMessage(), e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent arg0) {
        HibernateUtil.getSessionFactory().close();
    }

    private void seedAdmin() {
        logger.info("Verificando admin padrão...");

        FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
        List<Funcionario> funcionarios = funcionarioDAO.listar();

        for (Funcionario func : funcionarios) {
            if ("Administrador".equals(func.getFuncao())) {
                logger.info("Administrador já existe: {} (CPF: {})", func.getNome(), func.getCpf());
                return;
            }
        }

        Funcionario admin = new Funcionario();
        admin.setNome("Administrador");
        admin.setCpf("000.000.000-00");
        admin.setSenha(PasswordUtil.criptografar("admin123"));
        admin.setFuncao("Administrador");

        funcionarioDAO.salvar(admin);
        logger.info("Administrador padrão criado! CPF: 000.000.000-00 | Senha: admin123");
    }
}
