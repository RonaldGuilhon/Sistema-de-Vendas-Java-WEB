package br.com.vendas.bean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.vendas.domain.Fornecedor;
import br.com.vendas.domain.Produto;
import br.com.vendas.exception.BusinessException;
import br.com.vendas.service.FornecedorService;
import br.com.vendas.service.ProdutoService;
import br.com.vendas.service.impl.FornecedorServiceImpl;
import br.com.vendas.service.impl.ProdutoServiceImpl;
import br.com.vendas.util.JSFUtil;

@ManagedBean(name = "MBProdutos")
@ViewScoped
public class ProdutoBean {

    private Produto produto;
    private ArrayList<Produto> itens;
    private ArrayList<Produto> itensFiltrados;
    private String acao;
    private Long codigo;
    private List<Fornecedor> listaFornecedor;

    private final ProdutoService produtoService;
    private final FornecedorService fornecedorService;

    public ProdutoBean() {
        this.produtoService = new ProdutoServiceImpl();
        this.fornecedorService = new FornecedorServiceImpl();
    }

    public void setListaFornecedor(List<Fornecedor> listaFornecedor) {
        this.listaFornecedor = listaFornecedor;
    }

    public List<Fornecedor> getListaFornecedor() {
        return listaFornecedor;
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

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public ArrayList<Produto> getItens() {
        return itens;
    }

    public void setItens(ArrayList<Produto> itens) {
        this.itens = itens;
    }

    public ArrayList<Produto> getItensFiltrados() {
        return itensFiltrados;
    }

    public void setItensFiltrados(ArrayList<Produto> itensFiltrados) {
        this.itensFiltrados = itensFiltrados;
    }

    public void prepararPesquisa() {
        try {
            itens = (ArrayList<Produto>) produtoService.listarTodos();
        } catch (RuntimeException e) {
            JSFUtil.adicionarMensagemErro(e.getMessage());
        }
    }

    public void carregarCadastro() {
        try {
            if (codigo != null) {
                produto = produtoService.buscarPorCodigo(codigo);
            } else {
                produto = new Produto();
            }
            listaFornecedor = fornecedorService.listarTodos();
        } catch (RuntimeException e) {
            JSFUtil.adicionarMensagemErro(e.getMessage());
        }
    }

    public void novo() {
        produto = new Produto();
    }

    public void salvar() {
        try {
            produtoService.salvar(produto);
            produto = new Produto();
            JSFUtil.adicionarMensagemSucesso("Produto salvo com sucesso!");
        } catch (BusinessException e) {
            JSFUtil.adicionarMensagemErro(e.getMessage());
        } catch (RuntimeException e) {
            JSFUtil.adicionarMensagemErro("Erro ao salvar produto. Tente novamente.");
        }
    }

    public void excluir() {
        try {
            produtoService.excluir(produto);
            JSFUtil.adicionarMensagemSucesso("Produto excluido com sucesso!");
        } catch (BusinessException e) {
            JSFUtil.adicionarMensagemErro(e.getMessage());
        } catch (RuntimeException e) {
            JSFUtil.adicionarMensagemErro("Erro ao excluir produto. Tente novamente.");
        }
    }

    public void editar() {
        try {
            produtoService.editar(produto);
            JSFUtil.adicionarMensagemSucesso("Produto editado com sucesso!");
        } catch (BusinessException e) {
            JSFUtil.adicionarMensagemErro(e.getMessage());
        } catch (RuntimeException e) {
            JSFUtil.adicionarMensagemErro("Erro ao editar produto. Tente novamente.");
        }
    }
}
