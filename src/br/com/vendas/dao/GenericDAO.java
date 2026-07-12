package br.com.vendas.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import br.com.vendas.util.HibernateUtil;

public abstract class GenericDAO<T extends Serializable> {

    private final Class<T> classe;

    @SuppressWarnings("unchecked")
    public GenericDAO() {
        this.classe = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
    }

    public void salvar(T entidade) {
        Session sessao = HibernateUtil.getSessionFactory().openSession();
        Transaction transacao = null;

        try {
            transacao = sessao.beginTransaction();
            sessao.saveOrUpdate(entidade);
            transacao.commit();
        } catch (RuntimeException ex) {
            if (transacao != null) {
                transacao.rollback();
            }
            throw ex;
        } finally {
            sessao.close();
        }
    }

    @SuppressWarnings("unchecked")
    public List<T> listar(String namedQuery) {
        Session sessao = HibernateUtil.getSessionFactory().openSession();
        List<T> resultado = null;

        try {
            Query consulta = sessao.getNamedQuery(namedQuery);
            resultado = consulta.list();
        } catch (RuntimeException ex) {
            throw ex;
        } finally {
            sessao.close();
        }

        return resultado;
    }

    public T buscarPorCodigo(String namedQuery, Long codigo) {
        Session sessao = HibernateUtil.getSessionFactory().openSession();
        T entidade = null;

        try {
            Query consulta = sessao.getNamedQuery(namedQuery);
            consulta.setLong("codigo", codigo);
            entidade = (T) consulta.uniqueResult();
        } catch (RuntimeException ex) {
            throw ex;
        } finally {
            sessao.close();
        }

        return entidade;
    }

    public void excluir(T entidade) {
        Session sessao = HibernateUtil.getSessionFactory().openSession();
        Transaction transacao = null;

        try {
            transacao = sessao.beginTransaction();
            sessao.delete(entidade);
            transacao.commit();
        } catch (RuntimeException ex) {
            if (transacao != null) {
                transacao.rollback();
            }
            throw ex;
        } finally {
            sessao.close();
        }
    }

    public void editar(T entidade) {
        Session sessao = HibernateUtil.getSessionFactory().openSession();
        Transaction transacao = null;

        try {
            transacao = sessao.beginTransaction();
            sessao.update(entidade);
            transacao.commit();
        } catch (RuntimeException ex) {
            if (transacao != null) {
                transacao.rollback();
            }
            throw ex;
        } finally {
            sessao.close();
        }
    }
}
