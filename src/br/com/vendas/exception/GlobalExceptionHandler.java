package br.com.vendas.exception;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GlobalExceptionHandler implements PhaseListener {

    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @Override
    public void afterPhase(PhaseEvent event) {
    }

    @Override
    public void beforePhase(PhaseEvent event) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        if (facesContext == null || !facesContext.getExternalContext().isResponseCommitted()) {
            return;
        }

        Throwable exception = (Throwable) facesContext.getExternalContext()
                .getRequestMap().get("javax.servlet.error.exception");

        if (exception != null) {
            logger.error("Exceção capturada no ciclo de vida JSF: {}", exception.getMessage(), exception);

            while (exception.getCause() != null) {
                exception = exception.getCause();
            }

            FacesMessage message = new FacesMessage(
                    FacesMessage.SEVERITY_ERROR,
                    "Ocorreu um erro inesperado. Tente novamente.",
                    "Erro: " + exception.getMessage());
            facesContext.addMessage(null, message);
        }
    }

    @Override
    public PhaseId getPhaseId() {
        return PhaseId.ANY_PHASE;
    }
}
