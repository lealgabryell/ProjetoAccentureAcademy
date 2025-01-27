package acc.br.petiscai.dto;

import java.util.List;

public class PedidoDto {
    private Long clienteId;
    private List<ItemPedidoDto> itens;

    public Long getClienteId() {
        return clienteId;
    }
    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }
    public List<ItemPedidoDto> getItens() {
        return itens;
    }
    public void setItens(List<ItemPedidoDto> itens) {
        this.itens = itens;
    }
}
