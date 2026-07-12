package br.com.vendas.bean;

import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.vendas.domain.Fornecedor;
import br.com.vendas.exception.BusinessException;
import br.com.vendas.service.FornecedorService;
import br.com.vendas.service.impl.FornecedorServiceImpl;
import br.com.vendas.util.JSFUtil;

@ManagedBean(name = "MBFornecedores")
@ViewScoped
public class FornecedorBean {

    private Fornecedor fornecedor;
    private ArrayList<Fornecedor> itens;
    private ArrayList<Fornecedor> itensFiltrados;
    private String acao;
    private Long codigo;

    private final FornecedorService fornecedorService;

    public FornecedorBean() {
        this.fornecedorService = new FornecedorServiceImpl();
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public String getAcao() {
        return acao;
    }

    public void setAcao(String acao) {
        this.acao = acao;
    }

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }

    public ArrayList<Fornecedor> getItens() {
        return itens;
    }

    public void setItens(ArrayList<Fornecedor> itens) {
        this.itens = itens;
    }

    public ArrayList<Fornecedor> getItensFiltrados() {
        return itensFiltrados;
    }

    public void setItensFiltrados(ArrayList<Fornecedor> itensFiltrados) {
        this.itensFiltrados = itensFiltrados;
    }

    public void prepararPesquisa() {
        try {
            itens = (ArrayList<Fornecedor>) fornecedorService.listarTodos();
        } catch (RuntimeException e) {
            JSFUtil.adicionarMensagemErro(e.getMessage());
        }
    }

    public void carregarCadastro() {
        try {
            if (codigo != null) {
                fornecedor = fornecedorService.buscarPorCodigo(codigo);
            } else {
                fornecedor = new Fornecedor();
            }
        } catch (RuntimeException e) {
            JSFUtil.adicionarMensagemErro(e.getMessage());
        }
    }

    public void novo() {
        fornecedor = new Fornecedor();
    }

    public void salvar() {
        try {
            fornecedorService.salvar(fornecedor);
            fornecedor = new Fornecedor();
            JSFUtil.adicionarMensagemSucesso("Fornecedor salvo com sucesso!");
        } catch (BusinessException e) {
            JSFUtil.adicionarMensagemErro(e.getMessage());
        } catch (RuntimeException e) {
            JSFUtil.adicionarMensagemErro("Erro ao salvar fornecedor. Tente novamente.");
        }
    }

    public void excluir() {
        try {
            fornecedorService.excluir(fornecedor);
            JSFUtil.adicionarMensagemSucesso("Fornecedor excluido com sucesso!");
        } catch (BusinessException e) {
            JSFUtil.adicionarMensagemErro(e.getMessage());
        } catch (RuntimeException e) {
            JSFUtil.adicionarMensagemErro("Erro ao excluir fornecedor. Tente novamente.");
        }
    }

    public void editar() {
        try {
            fornecedorService.editar(fornecedor);
            JSFUtil.adicionarMensagemSucesso("Fornecedor editado com sucesso!");
        } catch (BusinessException e) {
            JSFUtil.adicionarMensagemErro(e.getMessage());
        } catch (RuntimeException e) {
            JSFUtil.adicionarMensagemErro("Erro ao editar fornecedor. Tente novamente.");
        }
    }
}
