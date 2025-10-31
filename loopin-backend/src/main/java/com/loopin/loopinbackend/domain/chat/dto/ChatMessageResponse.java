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
@Schema(name = "ChatMessageResponse", description = "메시지 응답 DTO")
public class ChatMessageResponse {
    @Schema(description = "메시지 ID", example = "1")
    private Long messageId;

    @Schema(description = "보낸 사람 ID", example = "1")
    private Long senderId;

    @Schema(description = "메시지 내용", example = "메시지 내용")
    private String content;

    @Schema(description = "채팅방 ID", example = "1")
    private Long chatRoomId;
}
