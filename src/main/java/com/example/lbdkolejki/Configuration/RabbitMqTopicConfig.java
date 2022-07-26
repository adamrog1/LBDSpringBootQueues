package com.example.lbdkolejki.Configuration;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



@Configuration
public class RabbitMqTopicConfig {
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

}
