package com.ifx.client.app.pane.session;

import cn.hutool.core.collection.CollectionUtil;
import com.ifx.account.entity.Account;
import com.ifx.session.service.ISessionLifeStyle;
import com.ifx.session.vo.SessionCreateVo;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;

/**
 * 任务创建
 */
@Slf4j
public class SessionCreatePane extends Pane implements Initializable {


    private TextArea sessionTitle ;

    private Set<Account> accounts ;

    private TextArea sessionDetail =  new TextArea();

    private Label  label = new Label("新建任务");

    private Button create ;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        log.info(" creating session ing ");
        sessionTitle =  new TextArea();
        sessionDetail =  new TextArea();
        accounts = CollectionUtil.newHashSet();
        label = new Label("新建任务");
        create = new Button(SessionConst.CREATE);
        create.addEventHandler(MouseEvent.MOUSE_CLICKED, (mouse) -> {
            Object source = mouse.getSource();
            SessionCreateVo sessionCreateVo = new SessionCreateVo();
            sessionCreateVo.setSessionTitle(sessionTitle.getText());
            sessionCreateVo.setSessionDetail(sessionDetail.getText());
            sessionCreateVo.setAccounts(accounts);
            try {
                Method create = ISessionLifeStyle.class.getMethod("create", SessionCreateVo.class);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
//            DubboApiMetaData metaData = DubboGenericParse.applyMeta(ISessionLifeStyle.class, "create", new Object[]{sessionCreateVo});

//            DubboGenericParse.applyMeta(create,sessionCreateVo);
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
