package br.com.vendas.bean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import br.com.vendas.domain.Funcionario;
import br.com.vendas.domain.Item;
import br.com.vendas.domain.Produto;
import br.com.vendas.domain.Venda;
import br.com.vendas.exception.BusinessException;
import br.com.vendas.security.LoginBean;
import br.com.vendas.service.ProdutoService;
import br.com.vendas.service.VendaService;
import br.com.vendas.service.impl.ProdutoServiceImpl;
import br.com.vendas.service.impl.VendaServiceImpl;
import br.com.vendas.util.JSFUtil;

@ManagedBean(name = "MBVendas")
@SessionScoped
public class VendaBean {

    private Produto produto;
    private Venda vendaCadastro;
    private List<Item> itens;
    private List<Item> itensFiltrados;
    private List<Produto> produtos;
    private List<Produto> produtosFiltrados;

    private final ProdutoService produtoService;
    private final VendaService vendaService;

    public VendaBean() {
        this.produtoService = new ProdutoServiceImpl();
        this.vendaService = new VendaServiceImpl();
    }

    public Venda getVendaCadastro() {
        if (vendaCadastro == null) {
            vendaCadastro = new Venda();
            vendaCadastro.setValorTotal(new BigDecimal("0.00"));
        }
        return vendaCadastro;
    }

    public void setVendaCadastro(Venda vendaCadastro) {
        this.vendaCadastro = vendaCadastro;
    }

    public List<Item> getItens() {
        if (itens == null) {
            itens = new ArrayList<>();
        }
        return itens;
    }

    public void setItens(List<Item> itens) {
        this.itens = itens;
    }

    public List<Item> getItensFiltrados() {
        return itensFiltrados;
    }

    public void setItensFiltrados(List<Item> itensFiltrados) {
        this.itensFiltrados = itensFiltrados;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
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

    public void carregarProdutos() {
        try {
            produtos = (ArrayList<Produto>) produtoService.listarTodos();
        } catch (RuntimeException e) {
            JSFUtil.adicionarMensagemErro(e.getMessage());
        }
    }

    public void adicionar(Produto produto) {
        if (produto.getQuantidade() == null || produto.getQuantidade() <= 0) {
            JSFUtil.adicionarMensagemErro("Produto sem estoque disponível: " + produto.getDescricao());
            return;
        }

        int posicaoEncontrada = -1;
        int quantidadeNoCarrinho = 0;

        for (int pos = 0; pos < itens.size() && posicaoEncontrada < 0; pos++) {
            Item itemTemp = itens.get(pos);
            if (itemTemp.getProduto().equals(produto)) {
                posicaoEncontrada = pos;
                quantidadeNoCarrinho = itemTemp.getQuantidade();
            }
        }

        if (quantidadeNoCarrinho >= produto.getQuantidade()) {
            JSFUtil.adicionarMensagemErro("Estoque insuficiente para adicionar mais de "
                    + produto.getDescricao() + ". Disponível: " + produto.getQuantidade()
                    + ", No carrinho: " + quantidadeNoCarrinho);
            return;
        }

        Item item = new Item();
        item.setProduto(produto);

        if (posicaoEncontrada < 0) {
            item.setQuantidade(1);
            item.setValorParcial(produto.getPreco());
            itens.add(item);
        } else {
            Item itemTemp = itens.get(posicaoEncontrada);
            item.setQuantidade(itemTemp.getQuantidade() + 1);
            item.setValorParcial(produto.getPreco().multiply(new BigDecimal(item.getQuantidade())));
            itens.set(posicaoEncontrada, item);
        }

        vendaCadastro.setValorTotal(vendaCadastro.getValorTotal().add(produto.getPreco()));
    }

    public void remover(Item item) {
        int posicaoEncontrada = -1;

        for (int pos = 0; pos < itens.size() && posicaoEncontrada < 0; pos++) {
            Item itemTemp = itens.get(pos);
            if (itemTemp.getProduto().equals(item.getProduto())) {
                posicaoEncontrada = pos;
            }
        }

        if (posicaoEncontrada > -1) {
            itens.remove(posicaoEncontrada);
            vendaCadastro.setValorTotal(vendaCadastro.getValorTotal().subtract(item.getValorParcial()));
        }
    }

    public void carregarDadosVenda() {
        vendaCadastro.setHorario(new Date());

        LoginBean loginBean = (LoginBean) FacesContext.getCurrentInstance()
                .getExternalContext().getSessionMap().get("MBLogin");

        if (loginBean != null && loginBean.getFuncionarioLogado() != null) {
            vendaCadastro.setFuncionario(loginBean.getFuncionarioLogado());
        }
    }

    public void salvar() {
        try {
            vendaService.finalizarVenda(vendaCadastro, itens);

            vendaCadastro = new Venda();
            vendaCadastro.setValorTotal(new BigDecimal("0.00"));
            itens = new ArrayList<>();

            JSFUtil.adicionarMensagemSucesso("Venda finalizada com sucesso!");
        } catch (BusinessException e) {
            JSFUtil.adicionarMensagemErro(e.getMessage());
        } catch (RuntimeException e) {
            JSFUtil.adicionarMensagemErro("Erro ao finalizar venda. Tente novamente.");
        }
    }
}
