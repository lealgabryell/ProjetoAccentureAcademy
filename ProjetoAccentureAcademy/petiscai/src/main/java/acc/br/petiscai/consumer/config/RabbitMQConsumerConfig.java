package acc.br.petiscai.consumer.config;

import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.context.annotation.Bean;


public class RabbitMQConsumerConfig {
    @Bean
    public Queue queue() {
        return new Queue("payment-registration-Equipe9", true); // Fila com durabilidade
    }

    @Bean
    public SimpleMessageListenerContainer messageListenerContainer(ConnectionFactory connectionFactory, MessageListener messageListener) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames("user-registration-Equipe9"); // Define a fila a ser consumida
        container.setMessageListener(messageListener);
        return container;
    }
}
