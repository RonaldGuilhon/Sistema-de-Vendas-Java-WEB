package br.com.vendas.security;

public enum Role {

    ADMIN("Administrador"),
    GERENTE("Gerente"),
    BALCONISTA("Balconista");

    private final String descricao;

    Role(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public static Role fromDescricao(String descricao) {
        for (Role role : values()) {
            if (role.getDescricao().equalsIgnoreCase(descricao)) {
                return role;
            }
        }
        return null;
    }
}
