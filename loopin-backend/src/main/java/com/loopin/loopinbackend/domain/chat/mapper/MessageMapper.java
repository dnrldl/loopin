package com.loopin.loopinbackend.domain.chat.mapper;

import com.loopin.loopinbackend.domain.chat.dto.ChatMessageResponse;
import com.loopin.loopinbackend.domain.chat.entity.Message;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MessageMapper {
//    default ChatMessageResponse toResponse(Message e) {
//        return new MessageResponse(
//                e.getId(), e.getChatRoomId(), e.getSenderId(), e.getReceiverId(),
//                e.getType().name(), e.getContent(), e.getFileUrl(),
//                e.getStatus().name(), e.getCreatedAt(), e.getReadAt()
//        );
//    }
//
//    default MessageEvent toEvent(MessageEntity e) {
//        return new MessageEvent(
//                e.getId(), e.getChatRoomId(), e.getSenderId(), e.getReceiverId(),
//                e.getType().name(), e.getContent(), e.getFileUrl(),
//                e.getStatus().name(), e.getCreatedAt()
//        );
//    }
}
