package com.example.lbdkolejki.Configuration;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.springframework.integration.dsl.MessageChannels.queue;


@Configuration
public class RabbitMqConfig {

    @Value("${spring.rabbitmq.username}")
    String username;

    @Value("${spring.rabbitmq.password}")
    private String password;
    @Bean
    Queue articleQueue(){
        return new Queue("articleQueue",false);
    }
    @Bean
    Queue commentQueue(){
        return new Queue("commentQueue",false);
    }
    @Bean
    Queue userQueue(){
        return new Queue("userQueue",false);
    }
    @Bean
    TopicExchange topicExchange() {
        return new TopicExchange("topic-exchange");
    }
    @Bean
    Binding articleBinding(Queue articleQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(articleQueue).to(topicExchange).with("queue.article");
    }
    @Bean
    Binding commentBinding(Queue commentQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(commentQueue).to(topicExchange).with("queue.comment");
    }
    @Bean
    Binding userBinding(Queue userQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(userQueue).to(topicExchange).with("queue.user");
    }
    @Bean
    Queue allQueue(){
        return new Queue("allQueue",false);
    }

    @Bean
    Binding allBinding(Queue allQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(allQueue).to(topicExchange).with("queue.*");
    }
    @Bean
    DirectExchange directExchange() {
        return new DirectExchange("direct-exchange");
    }

    @Bean
    Binding marketingBinding(Queue userQueue, DirectExchange directExchange) {
        return BindingBuilder.bind(userQueue).to(directExchange).with("user");
    }

	@Bean
	MessageListenerContainer messageListenerContainer() {
		SimpleMessageListenerContainer simpleMessageListenerContainer = new SimpleMessageListenerContainer();
		simpleMessageListenerContainer.setQueues(userQueue());
        simpleMessageListenerContainer.setConnectionFactory(connectionFactory());
		simpleMessageListenerContainer.setMessageListener(new RabbitMQListener());
		return simpleMessageListenerContainer;
	}

    @Bean
	ConnectionFactory connectionFactory() {
		CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory("localhost");
		cachingConnectionFactory.setUsername(username);
		cachingConnectionFactory.setUsername(password);
		return cachingConnectionFactory;
	}

}
