package com.loopin.loopinbackend.domain.chat.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "ChatMessageSendRequest", description = "메시지 전송 요청 DTO")
public class ChatMessageSendRequest {
    @Schema(description = "메시지 내용", example = "메시지 내용")
    private String content;

    @Schema(description = "메시지 내용", example = "메시지 내용")
    private Long chatRoomId;
}
