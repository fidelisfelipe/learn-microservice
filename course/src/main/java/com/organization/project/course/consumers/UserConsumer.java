package com.organization.project.course.consumers;

import com.organization.project.course.dtos.UserEventDto;
import com.organization.project.course.model.enums.ActionType;
import com.organization.project.course.services.UserService;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class UserConsumer {
    @Autowired
    UserService userService;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "${broker.queue.userEventQueue.name}", durable = "true"),
            exchange = @Exchange(value = "${broker.exchange.userEvent}",
                                type = ExchangeTypes.FANOUT,
                                ignoreDeclarationExceptions = "true")
    ))
    public void listenEvent(@Payload UserEventDto userEventDto){
        var userModel = userEventDto.convertToUserModel();
        switch (ActionType.valueOf(userEventDto.getActionType())){
            case CREATE:
                userService.save(userModel);
                break;
        }
    }
}
