package com.example.lbdkolejki.Controllers;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommentController {

    @Autowired
    private AmqpTemplate amqpTemplate;

    @PutMapping("/comment")
    public String createComment(@RequestParam("exchangeName") String exchange, @RequestParam("routingKey") String routingKey){
        amqpTemplate.convertAndSend(exchange,routingKey,"Comment Created");
        return "Message Send to RabbitMq Topic exchange";
    }

    @PostMapping("/comment")
    public String editComment(@RequestParam("exchangeName") String exchange, @RequestParam("routingKey") String routingKey){
        amqpTemplate.convertAndSend(exchange,routingKey,"Comment edited");
        return "Message Send to RabbitMq Topic exchange";
    }
}
