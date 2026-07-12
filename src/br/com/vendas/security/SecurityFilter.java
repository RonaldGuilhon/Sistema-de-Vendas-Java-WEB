package br.com.vendas.security;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.vendas.domain.Funcionario;

public class SecurityFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(SecurityFilter.class);

    private static final String[] PAGINAS_PUBLICAS = {
            "/login.xhtml",
            "/templates/",
            "/resources/",
            "/javax.faces.resource/"
    };

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("SecurityFilter inicializado.");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        String uri = httpServletRequest.getRequestURI();
        String contextPath = httpServletRequest.getContextPath();

        String pagina = uri.substring(contextPath.length());

        if (isPaginaPublica(pagina)) {
            chain.doFilter(request, response);
            return;
        }

        HttpSession session = httpServletRequest.getSession(false);
        Funcionario funcionarioLogado = null;

        if (session != null) {
            funcionarioLogado = (Funcionario) session.getAttribute("funcionarioLogado");
        }

        if (funcionarioLogado == null) {
            logger.debug("Acesso não autenticado redirecionado para login. URI: {}", uri);
            httpServletResponse.sendRedirect(contextPath + "/login.xhtml");
            return;
        }

        Role role = Role.fromDescricao(funcionarioLogado.getFuncao());

        if (!SecurityConfig.acessoPermitido(pagina, role)) {
            logger.warn("Acesso negado para funcionário: {} [{}] na página: {}",
                    funcionarioLogado.getNome(), funcionarioLogado.getFuncao(), pagina);
            httpServletResponse.sendRedirect(contextPath + "/pages/principal.xhtml");
            return;
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        logger.info("SecurityFilter destruído.");
    }

    private boolean isPaginaPublica(String pagina) {
        for (String paginaPublica : PAGINAS_PUBLICAS) {
            if (pagina.startsWith(paginaPublica)) {
                return true;
            }
        }
        return false;
    }
}
