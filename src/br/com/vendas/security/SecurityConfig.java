package br.com.vendas.security;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SecurityConfig {

    private static final Map<String, List<Role>> restricoesPagina = new HashMap<>();

    static {
        restricoesPagina.put("/pages/funcionarioCadastro.xhtml",
                Arrays.asList(Role.ADMIN, Role.GERENTE));
        restricoesPagina.put("/pages/funcionarioPesquisa.xhtml",
                Arrays.asList(Role.ADMIN, Role.GERENTE));
        restricoesPagina.put("/pages/relatorioVendas.xhtml",
                Arrays.asList(Role.ADMIN, Role.GERENTE));
        restricoesPagina.put("/pages/relatorioEstoque.xhtml",
                Arrays.asList(Role.ADMIN, Role.GERENTE));
    }

    public static boolean acessoPermitido(String pagina, Role role) {
        if (role == null) {
            return false;
        }

        List<Role> rolesPermitidas = restricoesPagina.get(pagina);

        if (rolesPermitidas == null) {
            return true;
        }

        return rolesPermitidas.contains(role);
    }

    public static boolean isPaginaProtegida(String pagina) {
        return restricoesPagina.containsKey(pagina);
    }
}
