package acc.br.petiscai.consumer.service;

import acc.br.petiscai.consumer.dtos.PagamentoConfirmationPayload;
import acc.br.petiscai.controller.EstoqueController;
import acc.br.petiscai.entity.ItemPedido;
import acc.br.petiscai.entity.Pedido;
import acc.br.petiscai.repository.PedidoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
public class PagamentoConfirmationService {
    @Autowired
    private PedidoRepository pedidoRepository;

    private static Logger log = Logger.getLogger(PagamentoConfirmationService.class.getName());





    @Transactional
    public void processarPagamentoConfirmado(PagamentoConfirmationPayload payload) {
        // Verifica se o pedido existe
        Pedido pedido = pedidoRepository.findById(payload.idPedido())
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado: " + payload.idPedido()));
        log.info("Cheguei no if do processarPagamentoConfirmado");
        // Verifica o status da confirmação
        if (payload.statusConfirmation()) {
            log.info("Cheguei no if do processarPagamentoConfirmado");
            pedido.getPagamento().setStatus(true); // Marca o pagamento como confirmado
            this.pedidoRepository.save(pedido); // Salva o pedido com o status de pagamento alterado
            System.out.println("Pagamento confirmado para o Pedido ID: " + pedido.getId());
        } else {
            System.out.println("Falha no pagamento para o Pedido ID: " + pedido.getId());
        }
    }
}
