package com.imooc.controller;

import com.imooc.utils.IMOOCJSONResult;
import com.mengdx.entity.RedisMessage;
import com.mengdx.entity.enums.MessageTypeEnum;
import com.mengdx.message.RedisMessageProduct;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ConcurrentReferenceHashMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 使用redis操作消息队列
 * @author Mengdl
 * @date 2022/07/07
 */
@AllArgsConstructor
@RestController
@RequestMapping(value = "message")
@Api(value = "redis消息管理", tags = "redis消息管理")
@Slf4j
public class MessageController {

    private final RedisMessageProduct<Object> redisMessageProduct;


    @PostMapping(value = "send")
    @ApiOperation(value = "发送消息")
    public IMOOCJSONResult sendMessage(){
        Map<String, Object> map = new ConcurrentReferenceHashMap<>();
        map.put("name", "张三");
        map.put("age", 18);
        RedisMessage<Object> redisMessage = RedisMessage.builder()
                .key("redis-list")
                .topic("channel_first")
                .data(map)
//                .messageType(MessageTypeEnum.LIST)
                .messageType(MessageTypeEnum.QUEUE)
                .build();
        redisMessageProduct.redisSendMessage(redisMessage);
        return IMOOCJSONResult.ok();
    }

}
