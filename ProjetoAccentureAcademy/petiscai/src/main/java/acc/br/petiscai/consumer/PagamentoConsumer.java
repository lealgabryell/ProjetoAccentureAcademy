package acc.br.petiscai.consumer;

import acc.br.petiscai.consumer.dtos.PagamentoConfirmationPayload;
import acc.br.petiscai.consumer.service.PagamentoConfirmationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PagamentoConsumer implements MessageListener {
    @Autowired
    private PagamentoConfirmationService pagamentoConfirmationService;

    @Override
    public void onMessage(org.springframework.amqp.core.Message message) {
        try {
            // Converte a mensagem JSON recebida em um objeto PagamentoConfirmationPayload
            ObjectMapper objectMapper = new ObjectMapper();
            PagamentoConfirmationPayload payload = objectMapper.readValue(message.getBody(), PagamentoConfirmationPayload.class);

            // Processa o pagamento confirmado
            pagamentoConfirmationService.processarPagamentoConfirmado(payload);

        } catch (Exception e) {
            e.printStackTrace();
            // Log de erro ou tratamento de falhas de deserialização/consumo
        }
    }
}
