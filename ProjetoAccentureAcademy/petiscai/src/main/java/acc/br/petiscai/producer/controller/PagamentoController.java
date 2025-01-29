package acc.br.petiscai.producer.controller;


import acc.br.petiscai.entity.Pagamento;
import acc.br.petiscai.entity.Pedido;
import acc.br.petiscai.producer.dto.PagamentoRegisteredPayload;
import acc.br.petiscai.producer.dto.RegisterPagamentoDto;
import acc.br.petiscai.repository.PagamentoRepository;
import acc.br.petiscai.repository.PedidoRepository;
import acc.br.petiscai.service.PagamentoService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


@RestController
public class PagamentoController {
    @Autowired
    PedidoRepository pedidoRepository;
    @Autowired
    PagamentoService pagamentoService;

    static String QUEUE_NAME = "user-registration-Equipe9";
    private final RabbitTemplate rabbitTemplate;


    public PagamentoController(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> registerUser(@RequestBody RegisterPagamentoDto registerPagamentoDto) throws JsonProcessingException {

        Random random = new Random();

        int confirmationCode = random.nextInt(900000) + 100000;
       PagamentoRegisteredPayload queuePayload = new PagamentoRegisteredPayload(

               registerPagamentoDto.statusConfirmation(),
               registerPagamentoDto.idPedido(),
                confirmationCode
        );
        ObjectMapper objectMapper = new ObjectMapper();
        String queuePayloadString = objectMapper.writeValueAsString(queuePayload);
        rabbitTemplate.convertAndSend(QUEUE_NAME, queuePayloadString);
        Map<String, String> response = new HashMap<>();

        Pagamento pagamento = new Pagamento(); //instancia de um pagamento

        Pedido pedido = this.pedidoRepository.findById(registerPagamentoDto.idPedido()).get(); //encontra um pedido pelo id passado no DTO
        pedido.getPagamento().setStatus(true); //muda o status do pagamento
        this.pedidoRepository.save(pedido); //faz update do pedido no bd
        pagamento.setPedido(pedido); //guarda o pedido na tabela de pagamento
        this.pagamentoService.save(pagamento);
        response.put("message", "Pagamento registrado com sucesso!");
        return ResponseEntity.ok(response);

    }

    //TODO Alterar status do pagamento de FALSE para TRUE, e ao fazer isso, alterar o status do pedido
}