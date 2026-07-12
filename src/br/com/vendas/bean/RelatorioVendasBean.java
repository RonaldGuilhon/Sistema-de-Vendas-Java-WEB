package br.com.vendas.bean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.vendas.domain.Venda;
import br.com.vendas.service.VendaService;
import br.com.vendas.service.impl.VendaServiceImpl;
import br.com.vendas.util.JSFUtil;

@ManagedBean(name = "MBRelatorioVendas")
@ViewScoped
public class RelatorioVendasBean {

    private Date dataInicio;
    private Date dataFim;
    private List<Venda> vendas;
    private BigDecimal valorTotalVendas;

    private final VendaService vendaService;

    public RelatorioVendasBean() {
        this.vendaService = new VendaServiceImpl();
        this.vendas = new ArrayList<>();
        this.valorTotalVendas = BigDecimal.ZERO;

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        dataInicio = cal.getTime();

        dataFim = new Date();
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Date getDataFim() {
        return dataFim;
    }

    public void setDataFim(Date dataFim) {
        this.dataFim = dataFim;
    }

    public List<Venda> getVendas() {
        return vendas;
    }

    public void setVendas(List<Venda> vendas) {
        this.vendas = vendas;
    }

    public BigDecimal getValorTotalVendas() {
        return valorTotalVendas;
    }

    public void setValorTotalVendas(BigDecimal valorTotalVendas) {
        this.valorTotalVendas = valorTotalVendas;
    }

    public void gerarRelatorio() {
        try {
            if (dataInicio == null || dataFim == null) {
                JSFUtil.adicionarMensagemErro("Informe o período (Data Início e Data Fim).");
                return;
            }

            if (dataInicio.after(dataFim)) {
                JSFUtil.adicionarMensagemErro("A Data Início não pode ser posterior à Data Fim.");
                return;
            }

            vendas = vendaService.listarPorPeriodo(dataInicio, dataFim);

            if (vendas == null) {
                vendas = new ArrayList<>();
            }

            valorTotalVendas = BigDecimal.ZERO;
            for (Venda v : vendas) {
                if (v.getValorTotal() != null) {
                    valorTotalVendas = valorTotalVendas.add(v.getValorTotal());
                }
            }

            JSFUtil.adicionarMensagemSucesso("Relatório gerado com sucesso! " + vendas.size() + " venda(s) encontrada(s).");
        } catch (RuntimeException e) {
            JSFUtil.adicionarMensagemErro("Erro ao gerar relatório de vendas.");
        }
    }

    public void limpar() {
        vendas = new ArrayList<>();
        valorTotalVendas = BigDecimal.ZERO;
        dataInicio = null;
        dataFim = null;
    }
}
