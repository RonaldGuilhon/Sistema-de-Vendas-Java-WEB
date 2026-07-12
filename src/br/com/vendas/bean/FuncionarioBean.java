package br.com.vendas.bean;

import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.vendas.domain.Funcionario;
import br.com.vendas.exception.BusinessException;
import br.com.vendas.service.FuncionarioService;
import br.com.vendas.service.impl.FuncionarioServiceImpl;
import br.com.vendas.util.JSFUtil;

@ManagedBean(name = "MBFuncionarios")
@ViewScoped
public class FuncionarioBean {

    private Funcionario funcionario;
    private ArrayList<Funcionario> itens;
    private ArrayList<Funcionario> itensFiltrados;
    private String acao;
    private Long codigo;

    private final FuncionarioService funcionarioService;

    public FuncionarioBean() {
        this.funcionarioService = new FuncionarioServiceImpl();
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

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public ArrayList<Funcionario> getItens() {
        return itens;
    }

    public void setItens(ArrayList<Funcionario> itens) {
        this.itens = itens;
    }

    public ArrayList<Funcionario> getItensFiltrados() {
        return itensFiltrados;
    }

    public void setItensFiltrados(ArrayList<Funcionario> itensFiltrados) {
        this.itensFiltrados = itensFiltrados;
    }

    public void prepararPesquisa() {
        try {
            itens = (ArrayList<Funcionario>) funcionarioService.listarTodos();
        } catch (RuntimeException e) {
            JSFUtil.adicionarMensagemErro(e.getMessage());
        }
    }

    public void carregarCadastro() {
        try {
            if (codigo != null) {
                funcionario = funcionarioService.buscarPorCodigo(codigo);
            } else {
                funcionario = new Funcionario();
            }
        } catch (RuntimeException e) {
            JSFUtil.adicionarMensagemErro(e.getMessage());
        }
    }

    public void novo() {
        funcionario = new Funcionario();
    }

    public void salvar() {
        try {
            funcionarioService.salvar(funcionario);
            funcionario = new Funcionario();
            JSFUtil.adicionarMensagemSucesso("Funcionário salvo com sucesso!");
        } catch (BusinessException e) {
            JSFUtil.adicionarMensagemErro(e.getMessage());
        } catch (RuntimeException e) {
            JSFUtil.adicionarMensagemErro("Erro ao salvar funcionário. Tente novamente.");
        }
    }

    public void excluir() {
        try {
            funcionarioService.excluir(funcionario);
            JSFUtil.adicionarMensagemSucesso("Funcionário excluido com sucesso!");
        } catch (BusinessException e) {
            JSFUtil.adicionarMensagemErro(e.getMessage());
        } catch (RuntimeException e) {
            JSFUtil.adicionarMensagemErro("Erro ao excluir funcionário. Tente novamente.");
        }
    }

    public void editar() {
        try {
            funcionarioService.editar(funcionario);
            JSFUtil.adicionarMensagemSucesso("Funcionário editado com sucesso!");
        } catch (BusinessException e) {
            JSFUtil.adicionarMensagemErro(e.getMessage());
        } catch (RuntimeException e) {
            JSFUtil.adicionarMensagemErro("Erro ao editar funcionário. Tente novamente.");
        }
    }
}
