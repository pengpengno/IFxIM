package com.ifx.client.app.pane.viewMain.message;

import com.ifx.account.vo.ChatMsgVo;
import com.ifx.client.util.FxApplicationThreadUtil;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.scene.control.ScrollBar;
import javafx.scene.layout.FlowPane;
import lombok.extern.slf4j.Slf4j;

import java.net.URL;
import java.util.Collection;
import java.util.ResourceBundle;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * @author pengpeng
 * @description
 * @date 2023/7/26
 */

@Slf4j
public class ChatBubbleShowPane extends FlowPane implements Initializable {

    private LinkedBlockingDeque<ChatBubblePane> linkedBlockingBubblePane ;


    private ScrollBar scrollBar;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initPane();
    }


    public void initPane(){

        linkedBlockingBubblePane = new LinkedBlockingDeque<>();

//        this.setHgap(USE_PREF_SIZE);

        this.setVgap(1);

        this.setOrientation(Orientation.VERTICAL);


    }


    public void chatBubble(Collection<ChatMsgVo> chatMsgVos){
        chatMsgVos.stream().limit(100)
                .forEach(this::addChatBubblePane);
    }


    public void clear(){
        FxApplicationThreadUtil.invoke(()-> {
            linkedBlockingBubblePane.clear();
            this.getChildren().clear();
        });
    }


    public void addChatBubblePane(ChatMsgVo chatMsgVo){

        FxApplicationThreadUtil.invoke(()-> {

            ChatBubblePane chatBubblePane = new ChatBubblePane().wiredChatMsgVo(chatMsgVo);
//            chatBubblePane.prefWidthProperty().bind(this.prefWidthProperty());
            try {
                linkedBlockingBubblePane.putLast(chatBubblePane);
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
            this.getChildren().add(chatBubblePane);

        });

    }

}
