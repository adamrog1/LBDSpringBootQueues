package com.example.lbdkolejki.Controllers;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    private AmqpTemplate amqpTemplate;

    @PostMapping("/user")
    public String createUser(@RequestParam("exchangeName") String exchange, @RequestParam("routingKey") String routingKey){
        amqpTemplate.convertAndSend(exchange,routingKey,"User created");
        return "Message Send to RabbitMq Topic exchange";
    }

    @PostMapping("/emailToUser")
    public String sendEmailToUser(@RequestParam("exchangeName") String exchange, @RequestParam("routingKey") String routingKey){
        amqpTemplate.convertAndSend(exchange,routingKey,"Email Send");
        return "Direct Message send to RabbitMq";
    }

}
