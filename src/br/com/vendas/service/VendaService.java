package br.com.vendas.service;

import java.util.Date;
import java.util.List;

import br.com.vendas.domain.Item;
import br.com.vendas.domain.Venda;

public interface VendaService {

    void finalizarVenda(Venda venda, List<Item> itens);

    Venda buscarPorCodigo(Long codigo);

    List<Venda> listarTodas();

    List<Venda> listarPorPeriodo(Date dataInicio, Date dataFim);
}
