package com.loopin.loopinbackend.domain.chat.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "Auth", description = "인증 API")
@RequiredArgsConstructor
@RequestMapping("/api/chat")
@Controller
public class ChatController {
    @MessageMapping("chat.sendDm")
    public void sendDm() {
    }
}
