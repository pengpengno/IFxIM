package com.ifx.client.app.event;

import com.ifx.account.vo.session.SessionInfoVo;
import javafx.event.Event;
import javafx.event.EventType;
import reactor.core.publisher.Mono;

import java.util.Optional;

public class SessionEvent extends Event {

    public static final EventType<SessionEvent> SESSION_EVENT = new EventType<>(Event.ANY, "SESSION_EVENT");

    public static final EventType<SessionEvent> SESSION_SWITCH = new EventType<>(SESSION_EVENT, "SESSION_SWITCH");


    private SessionInfoVo sessionInfoVo;


    public SessionEvent(EventType<? extends Event> eventType, SessionInfoVo sessionInfoVo ) {
        super(eventType);
        this.sessionInfoVo = sessionInfoVo ;
    }

    @Override
    public Object getSource() {
        return sessionInfoVo;
    }

    public Mono<SessionInfoVo> getSessionInfoVo(){
        return Mono.justOrEmpty(Optional.ofNullable(sessionInfoVo));
    }
}
