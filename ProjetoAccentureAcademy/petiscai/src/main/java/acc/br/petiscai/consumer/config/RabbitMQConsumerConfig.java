package acc.br.petiscai.consumer.config;

import acc.br.petiscai.consumer.PagamentoConsumer;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConsumerConfig {
    private static final String QUEUE_NAME = "payment-registration-Equipe9";

    @Bean
    public Queue consumerQueue() {
        return new Queue(QUEUE_NAME, true); // Fila durável
    }

    @Bean
    public SimpleMessageListenerContainer messageListenerContainer(ConnectionFactory connectionFactory, PagamentoConsumer messageListener) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(QUEUE_NAME); // Verifique se o nome da fila está correto
        container.setMessageListener(messageListener);

        return container;
    }
}
