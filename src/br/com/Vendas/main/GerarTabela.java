package br.com.vendas.main;

import br.com.vendas.util.HibernateUtil;

public class GerarTabela {

    public static void main(String[] args) {
        HibernateUtil.getSessionFactory();
        HibernateUtil.getSessionFactory().close();
    }
}
