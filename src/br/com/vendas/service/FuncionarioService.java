package br.com.vendas.service;

import java.util.List;

import br.com.vendas.domain.Funcionario;

public interface FuncionarioService {

    void salvar(Funcionario funcionario);

    void editar(Funcionario funcionario);

    void excluir(Funcionario funcionario);

    Funcionario buscarPorCodigo(Long codigo);

    List<Funcionario> listarTodos();

    Funcionario autenticar(String cpf, String senha);
}
