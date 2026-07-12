package br.com.vendas.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.vendas.domain.Fornecedor;
import br.com.vendas.service.FornecedorService;
import br.com.vendas.service.impl.FornecedorServiceImpl;

@FacesConverter("fornecedorConverter")
public class FornecedorConverter implements Converter {

    private final FornecedorService fornecedorService;

    public FornecedorConverter() {
        this.fornecedorService = new FornecedorServiceImpl();
    }

    @Override
    public Object getAsObject(FacesContext ctx, UIComponent componente, String valor) {
        if (valor == null || valor.trim().isEmpty()) {
            return null;
        }

        try {
            Long codigo = Long.parseLong(valor);
            return fornecedorService.buscarPorCodigo(codigo);
        } catch (NumberFormatException ex) {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext ctx, UIComponent componente, Object objeto) {
        if (objeto == null) {
            return "";
        }

        if (objeto instanceof Fornecedor) {
            Fornecedor fornecedor = (Fornecedor) objeto;
            return fornecedor.getCodigo() != null ? fornecedor.getCodigo().toString() : "";
        }

        return "";
    }
}
