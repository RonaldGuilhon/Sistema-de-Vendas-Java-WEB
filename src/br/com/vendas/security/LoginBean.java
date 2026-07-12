package br.com.vendas.security;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.vendas.domain.Funcionario;
import br.com.vendas.service.FuncionarioService;
import br.com.vendas.service.impl.FuncionarioServiceImpl;
import br.com.vendas.util.JSFUtil;

@ManagedBean(name = "MBLogin")
@SessionScoped
public class LoginBean {

    private static final Logger logger = LoggerFactory.getLogger(LoginBean.class);

    private String cpf;
    private String senha;
    private Funcionario funcionarioLogado;

    private final FuncionarioService funcionarioService;

    public LoginBean() {
        this.funcionarioService = new FuncionarioServiceImpl();
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Funcionario getFuncionarioLogado() {
        return funcionarioLogado;
    }

    public String getNomeFuncionario() {
        if (funcionarioLogado != null) {
            return funcionarioLogado.getNome();
        }
        return "";
    }

    public String getFuncaoFuncionario() {
        if (funcionarioLogado != null) {
            return funcionarioLogado.getFuncao();
        }
        return "";
    }

    public Role getRoleLogado() {
        if (funcionarioLogado != null) {
            return Role.fromDescricao(funcionarioLogado.getFuncao());
        }
        return null;
    }

    public boolean isLogado() {
        return funcionarioLogado != null;
    }

    public boolean isAdmin() {
        return getRoleLogado() == Role.ADMIN;
    }

    public boolean isGerente() {
        Role role = getRoleLogado();
        return role == Role.ADMIN || role == Role.GERENTE;
    }

    public String autenticar() {
        if (cpf == null || cpf.trim().isEmpty()) {
            JSFUtil.adicionarMensagemErro("Informe o CPF.");
            return null;
        }

        if (senha == null || senha.trim().isEmpty()) {
            JSFUtil.adicionarMensagemErro("Informe a senha.");
            return null;
        }

        try {
            funcionarioLogado = funcionarioService.autenticar(cpf, senha);

            if (funcionarioLogado != null) {
                logger.info("Login realizado com sucesso. Funcionário: {} [{}]",
                        funcionarioLogado.getNome(), funcionarioLogado.getFuncao());

                inicializarSessao();

                cpf = null;
                senha = null;

                return "/pages/principal.xhtml?faces-redirect=true";
            } else {
                logger.warn("Tentativa de login falhou para CPF: {}", cpf);
                JSFUtil.adicionarMensagemErro("CPF ou senha inválidos.");
                return null;
            }
        } catch (Exception e) {
            logger.error("Erro durante autenticação: {}", e.getMessage(), e);
            JSFUtil.adicionarMensagemErro("Erro ao realizar login. Tente novamente.");
            return null;
        }
    }

    public String logout() {
        logger.info("Logout realizado. Funcionário: {}",
                funcionarioLogado != null ? funcionarioLogado.getNome() : "desconhecido");

        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        if (session != null) {
            session.invalidate();
        }

        funcionarioLogado = null;
        cpf = null;
        senha = null;

        return "/login.xhtml?faces-redirect=true";
    }

    public boolean podeAcessar(String pagina) {
        if (!isLogado()) {
            return false;
        }

        Role role = getRoleLogado();
        return SecurityConfig.acessoPermitido(pagina, role);
    }

    private void inicializarSessao() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
        session.setAttribute("funcionarioLogado", funcionarioLogado);
    }
}
