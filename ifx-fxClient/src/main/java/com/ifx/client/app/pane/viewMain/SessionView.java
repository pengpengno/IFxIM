package com.ifx.client.app.pane.viewMain;

import cn.hutool.core.util.ObjectUtil;
import com.ifx.account.mapstruct.AccProtoBufMapper;
import com.ifx.account.vo.ChatMsgVo;
import com.ifx.account.vo.session.SessionInfoVo;
import com.ifx.client.app.enums.APPEnum;
import com.ifx.client.app.event.ChatEvent;
import com.ifx.client.app.pane.viewMain.message.ChatMainPane;
import com.ifx.client.app.pane.viewMain.session.SessionListPane;
import com.ifx.client.util.FxApplicationThreadUtil;
import com.ifx.connect.proto.Chat;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import lombok.extern.slf4j.Slf4j;
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
public class SessionView extends Pane implements ViewAction {

    @Autowired
    private SessionListPane sessionListPane;

    @Autowired
    private ChatMainPane chatMainPane;


    @Override
    public APPEnum viewType() {
        return APPEnum.SESSION;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        log.info("init SessionMainView");

        this.setBackground(new Background(new BackgroundFill(Color.rgb(98,109,160),null,null)));

        initPane();

        initChatHandler();

    }

    public Mono<Void> initSessionRefresh(Flux<SessionInfoVo> sessionInfoVo){
            return sessionInfoVo
            .doOnNext(e-> FxApplicationThreadUtil.invoke(()-> sessionListPane.addSession(e)))
                    .then()
        ;
    }

    @Override
    public void size(Long width, Long height) {
        ViewAction.super.size(width, height);
        this.setPrefSize(width,height);
    }

    public void initPane(){
        sessionListPane.initialize(null,null);
        chatMainPane.initialize(null,null);

        sessionListPane.prefHeightProperty().bind(this.heightProperty());
        sessionListPane.prefWidthProperty().setValue(ViewSize.SESSION_LIST_PANE_PREFWIDTH);

        chatMainPane.prefHeightProperty().bind(this.heightProperty());
        chatMainPane.prefWidthProperty().bind(this.prefWidthProperty());
        chatMainPane.setLayoutX(ViewSize.SESSION_LIST_PANE_PREFWIDTH.doubleValue());


        this.getChildren().add(sessionListPane);

        this.getChildren().add(chatMainPane);

    }

    private void initChatHandler (){
        if (log.isDebugEnabled()){
            log.debug("init chat handler");
        }

        this.addEventHandler(ChatEvent.RECEIVE_CHAT , (chat)-> {
            log.info("client receive message");
            Chat.ChatMessage chatMessage = chat.getChatMessage();
            ChatMsgVo chatMsgVo = AccProtoBufMapper.INSTANCE.tran2ProtoChat(chatMessage);
            Mono.justOrEmpty(Optional.ofNullable(chatMainPane.currentSessionInfo()))
                    .filter(e -> ObjectUtil.equal(chatMsgVo.getSessionId(), e.getSessionId()))
                    .hasElement()
                    .flatMap(isCurrentSessionMessage -> {
                        if (isCurrentSessionMessage) {
                            if (log.isDebugEnabled()){
                                log.debug("receive chat message {} add it to  messagePane {}",chatMsgVo.getContent(),chatMainPane.getMessagePane().block().sessionInfoVo().getSessionId());
                            }
                            return chatMainPane.getMessagePane()
                                    .doOnNext(e -> e.addChatBubblePane(chatMsgVo));
                        }
                        return Mono.empty();
                    })
                    .subscribe();
        });
    }




}
