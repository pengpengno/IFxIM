package com.ifx.client.app.pane.session;

import cn.hutool.core.collection.CollectionUtil;
import com.ifx.common.base.AccountInfo;
import com.ifx.session.vo.session.SessionAccountVo;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import lombok.extern.slf4j.Slf4j;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;

/**
 * 任务创建
 */
@Slf4j
public class SessionCreatePane extends Pane implements Initializable {


    private TextArea sessionTitle ;

    private Set<AccountInfo> accounts ;

    private Set<Long> useIdSet;

    private TextArea sessionDetail =  new TextArea();

    private Label  label = new Label("新建任务");

    private Button create ;

    private final SessionAccountVo sessionAccountVo =  new SessionAccountVo();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        log.info(" creating session ing ");
        sessionTitle =  new TextArea();
        sessionDetail =  new TextArea();
        accounts = CollectionUtil.newHashSet();
        label = new Label("新建会话");
        create = new Button(SessionConst.CREATE);
        create.addEventHandler(MouseEvent.MOUSE_CLICKED, (mouse) -> {
            log.debug("开始新建会话");
            sessionAccountVo.setAddUseIdSet(useIdSet);
        });
    }

    private enum INSTANCE{
        INSTANCE;
        public final SessionCreatePane instance ;
        INSTANCE(){
            instance = new SessionCreatePane();
        }
        public static SessionCreatePane getInstance(){
            return INSTANCE.instance;
        }
    }



}
