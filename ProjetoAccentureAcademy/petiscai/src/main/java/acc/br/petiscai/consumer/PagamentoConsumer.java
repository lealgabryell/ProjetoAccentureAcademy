package acc.br.petiscai.consumer;

import acc.br.petiscai.consumer.dtos.PagamentoConfirmationPayload;
import acc.br.petiscai.consumer.service.PagamentoConfirmationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class PagamentoConsumer implements MessageListener {
    @Autowired
    private PagamentoConfirmationService pagamentoConfirmationService;

    private static Logger log = Logger.getLogger(PagamentoConfirmationService.class.getName());

    @Override
    public void onMessage(Message message) {
        try {
            log.info("PagamentoConsumer received a message");
            // Converte a mensagem JSON recebida em um objeto PagamentoConfirmationPayload
            ObjectMapper objectMapper = new ObjectMapper();

            PagamentoConfirmationPayload payload = objectMapper.readValue(message.getBody(), PagamentoConfirmationPayload.class);


            // Processa o pagamento confirmado
            pagamentoConfirmationService.processarPagamentoConfirmado(payload);
            log.info("PagamentoConsumer processed a message");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
