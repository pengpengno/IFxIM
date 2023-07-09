package com.ifx.client.app.pane.viewMain;

import cn.hutool.core.util.ObjectUtil;
import com.ifx.account.mapstruct.AccProtoBufMapper;
import com.ifx.account.vo.ChatMsgVo;
import com.ifx.account.vo.session.SessionInfoVo;
import com.ifx.client.app.event.ChatEvent;
import com.ifx.client.app.pane.message.ChatMainPane;
import com.ifx.client.app.pane.session.SessionListPane;
import com.ifx.client.util.FxApplicationThreadUtil;
import com.ifx.connect.proto.Chat;
import javafx.scene.layout.Pane;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * @author pengpeng
 * @description
 * @date 2023/7/8
 */
@Component
@Slf4j
public class SessionMainView extends Pane implements MainViewAction , InitializingBean {

    @Autowired
    private SessionListPane sessionListPane;

    @Autowired
    private ChatMainPane chatMainPane;


    @Override
    public void afterPropertiesSet() throws Exception {
        initialize(null,null);
    }

    @Override
    public APPEnum viewType() {
        return APPEnum.SESSION;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initChatHandler()
                .doOnNext(e->chatMainPane.initialize(null,null))
                .subscribe();
    }

    public Mono<Void> initSessionRefresh(Flux<SessionInfoVo> sessionInfoVo){
            return sessionInfoVo
            .doOnNext(e-> FxApplicationThreadUtil.invoke(()-> sessionListPane.addSession(e)))
                    .then()
        ;

    }

    private Mono<Void> initChatHandler (){
        return Mono.just(this)
                .doOnNext(obj-> {
                    obj.addEventHandler(ChatEvent.RECEIVE_CHAT , (chat)-> {
                        log.info("client receive message");
                        Chat.ChatMessage chatMessage = chat.getChatMessage();
                        ChatMsgVo chatMsgVo = AccProtoBufMapper.INSTANCE.tran2ProtoChat(chatMessage);
                        Mono.justOrEmpty(Optional.ofNullable(chatMainPane.currentSessionInfo()))
                                .filter(e-> ObjectUtil.equal(chatMsgVo.getSessionId(),e.getSessionId()))
                                .hasElement()
                                .flatMap(isCurrentSessionMessage-> {
                                    if (isCurrentSessionMessage) {
                                        return chatMainPane.getMessagePane().doOnNext(e->e.addMessage(chatMsgVo));
                                    }
                                    return Mono.empty();
                                })
                                .subscribe();
                    });
                }).then();
    }




}
