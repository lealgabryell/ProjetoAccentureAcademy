package acc.br.petiscai.consumer.service;

import acc.br.petiscai.consumer.dtos.PagamentoConfirmationPayload;
import acc.br.petiscai.entity.Pedido;
import acc.br.petiscai.repository.PedidoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PagamentoConfirmationService {
    @Autowired
    private PedidoRepository pedidoRepository;

    @Transactional
    public void processarPagamentoConfirmado(PagamentoConfirmationPayload payload) {
        // Verifica se o pedido existe
        Pedido pedido = pedidoRepository.findById(payload.idPedido())
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado: " + payload.idPedido()));

        // Verifica o status da confirmação
        if (payload.statusConfirmation()) {
            pedido.getPagamento().setStatus(true); // Marca o pagamento como confirmado
            this.pedidoRepository.save(pedido); // Salva o pedido com o status de pagamento alterado
            System.out.println("Pagamento confirmado para o Pedido ID: " + pedido.getId());
        } else {
            System.out.println("Falha no pagamento para o Pedido ID: " + pedido.getId());
            // Ações em caso de pagamento não confirmado (por exemplo, notificação, tentativas de reprocessamento, etc.)
        }
    }
}
