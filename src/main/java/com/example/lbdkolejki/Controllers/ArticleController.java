package com.example.lbdkolejki.Controllers;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ArticleController {

    @Autowired
    private AmqpTemplate amqpTemplate;

    @PostMapping("/article")
    public String createArticle(@RequestParam("exchangeName") String exchange, @RequestParam("routingKey") String routingKey){
        amqpTemplate.convertAndSend(exchange,routingKey,"Article Created");
        return "Message Send to RabbitMq Topic exchange";
    }

    @PutMapping("/article")
    public String updateArticle(@RequestParam("exchangeName") String exchange, @RequestParam("routingKey") String routingKey){
        amqpTemplate.convertAndSend(exchange, routingKey, "Article Updated");
        return "Message Send to RabbitMq Topic exchange";
    }
}
