package com.ifx.client.app.event;

import com.ifx.connect.proto.Chat;
import javafx.event.Event;
import javafx.event.EventTarget;
import javafx.event.EventType;

/**
 * 消息事件
 */
public class ChatEvent extends Event {
    public static final EventType<ChatEvent> CHAT_ANY = new EventType<>(Event.ANY, "CHAT");


    public static final EventType<ChatEvent> RECEIVE_CHAT = new EventType<>(CHAT_ANY, "CHAT_RECEIVE");


    private final Chat.ChatMessage chatMessage ;


    public ChatEvent(  EventType<? extends Event> eventType, Chat.ChatMessage chatMessage ) {
        super(eventType);
        this.chatMessage = chatMessage ;
    }

    @Override
    public Object getSource() {
        return chatMessage;
    }


    public Chat.ChatMessage getChatMessage(){
        return chatMessage;
    }


    public ChatEvent(Object source, EventTarget target, EventType<? extends Event> eventType,Chat.ChatMessage chatMessage) {
        super(source, target, eventType);
        this.chatMessage = chatMessage;
    }
}
