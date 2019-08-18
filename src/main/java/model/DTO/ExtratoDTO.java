package model.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExtratoDTO {

    List<TransacaoDTO> pagamentos;

    List<TransacaoDTO> recebimentos;

    public List<TransacaoDTO> getPagamentos() {
        return pagamentos;
    }

    public void setPagamentos(List<TransacaoDTO> pagamentos) {
        this.pagamentos = pagamentos;
    }

    public List<TransacaoDTO> getRecebimentos() {
        return recebimentos;
    }

    public void setRecebimentos(List<TransacaoDTO> recebimentos) {
        this.recebimentos = recebimentos;
    }
}
