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
    TopicExchange articleExchange() {
        return new TopicExchange("article-exchange");
    }

    @Bean
    Binding articleBinding(Queue articleQueue, TopicExchange articleExchange) {
        return BindingBuilder.bind(articleQueue).to(articleExchange).with("queue.article");
    }

    @Bean
    Queue commentQueue(){
        return new Queue("commentQueue",false);
    }

    @Bean
    TopicExchange commentExchange() {
        return new TopicExchange("comment-exchange");
    }

    @Bean
    Binding commentBinding(Queue commentQueue, TopicExchange commentExchange) {
        return BindingBuilder.bind(commentQueue).to(commentExchange).with("queue.comment");
    }

    @Bean
    Queue userQueue(){
        return new Queue("userQueue",false);
    }

    @Bean
    TopicExchange userExchange() {
        return new TopicExchange("user-exchange");
    }

    @Bean
    Binding userBinding(Queue userQueue, TopicExchange userExchange) {
        return BindingBuilder.bind(userQueue).to(userExchange).with("queue.user");
    }

    @Bean
    Queue allQueue(){
        return new Queue("allQueue",false);
    }

    @Bean
    TopicExchange allExchange() {
        return new TopicExchange("all-exchange");
    }

    @Bean
    Binding allBinding(Queue allQueue, TopicExchange userExchange) {
        return BindingBuilder.bind(allQueue).to(userExchange).with("queue.*");
    }

    @Bean
    DirectExchange exchange() {
        return new DirectExchange("direct-exchange");
    }

    @Bean
    Binding marketingBinding(Queue userQueue, DirectExchange exchange) {
        return BindingBuilder.bind(userQueue).to(exchange).with("user");
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
