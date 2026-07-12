package br.com.vendas.bean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.vendas.domain.Produto;
import br.com.vendas.service.ProdutoService;
import br.com.vendas.service.impl.ProdutoServiceImpl;
import br.com.vendas.util.JSFUtil;

@ManagedBean(name = "MBRelatorioEstoque")
@ViewScoped
public class RelatorioEstoqueBean {

    private List<Produto> produtos;
    private List<Produto> produtosFiltrados;
    private boolean somenteEstoqueBaixo;
    private int totalProdutos;
    private int produtosEstoqueBaixo;
    private int produtosSemEstoque;

    private final ProdutoService produtoService;

    public RelatorioEstoqueBean() {
        this.produtoService = new ProdutoServiceImpl();
        this.produtos = new ArrayList<>();
        this.produtosFiltrados = new ArrayList<>();
        this.somenteEstoqueBaixo = false;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
    }

    public List<Produto> getProdutosFiltrados() {
        return produtosFiltrados;
    }

    public void setProdutosFiltrados(List<Produto> produtosFiltrados) {
        this.produtosFiltrados = produtosFiltrados;
    }

    public boolean isSomenteEstoqueBaixo() {
        return somenteEstoqueBaixo;
    }

    public void setSomenteEstoqueBaixo(boolean somenteEstoqueBaixo) {
        this.somenteEstoqueBaixo = somenteEstoqueBaixo;
    }

    public int getTotalProdutos() {
        return totalProdutos;
    }

    public void setTotalProdutos(int totalProdutos) {
        this.totalProdutos = totalProdutos;
    }

    public int getProdutosEstoqueBaixo() {
        return produtosEstoqueBaixo;
    }

    public void setProdutosEstoqueBaixo(int produtosEstoqueBaixo) {
        this.produtosEstoqueBaixo = produtosEstoqueBaixo;
    }

    public int getProdutosSemEstoque() {
        return produtosSemEstoque;
    }

    public void setProdutosSemEstoque(int produtosSemEstoque) {
        this.produtosSemEstoque = produtosSemEstoque;
    }

    public void gerarRelatorio() {
        try {
            List<Produto> todos = produtoService.listarTodos();

            if (todos == null) {
                todos = new ArrayList<>();
            }

            totalProdutos = todos.size();
            produtosEstoqueBaixo = 0;
            produtosSemEstoque = 0;

            List<Produto> resultado = new ArrayList<>();
            for (Produto p : todos) {
                int qtd = p.getQuantidade() != null ? p.getQuantidade() : 0;

                if (qtd == 0) {
                    produtosSemEstoque++;
                } else if (qtd <= 5) {
                    produtosEstoqueBaixo++;
                }

                if (somenteEstoqueBaixo) {
                    if (qtd <= 5) {
                        resultado.add(p);
                    }
                } else {
                    resultado.add(p);
                }
            }

            produtos = resultado;

            JSFUtil.adicionarMensagemSucesso("Relatório gerado com sucesso! " + produtos.size() + " produto(s) encontrado(s).");
        } catch (RuntimeException e) {
            JSFUtil.adicionarMensagemErro("Erro ao gerar relatório de estoque.");
        }
    }

    public void limpar() {
        produtos = new ArrayList<>();
        produtosFiltrados = new ArrayList<>();
        somenteEstoqueBaixo = false;
        totalProdutos = 0;
        produtosEstoqueBaixo = 0;
        produtosSemEstoque = 0;
    }
}
