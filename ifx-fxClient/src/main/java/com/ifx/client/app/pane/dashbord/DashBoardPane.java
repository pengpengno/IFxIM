package com.ifx.client.app.pane.dashbord;

import cn.hutool.core.collection.CollectionUtil;
import com.ifx.common.base.AccountInfo;
import com.ifx.common.context.AccountContext;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * @author pengpeng
 * @description
 * @date 2023/6/29
 */
@Slf4j
public class DashBoardPane extends FlowPane implements Initializable {


    private AccountInfo accountInfo;

    private Label accountName ;

    @Autowired
    private List<MiniApplication> dashBoardMiniApps;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        this.setBackground(new Background(new BackgroundFill(Color.rgb(1,180,100),null,null)));

        log.info (this.getClass().getName() + "has been build ");

        initPane();

    }


    public void setAccountInfo(AccountInfo accountInfo){
        this.accountInfo = accountInfo ;
    }



    public void initPane(){

        setAccountInfo(AccountContext.getCurAccount());

        accountName = new Label(accountInfo.getUserName());

        accountName.setPrefSize(70,140);
        this.setPadding(new Insets(10));
        this.setHgap(10);
        this.setVgap(dashBoardMiniApps.size());
//        this.setAlignment(Pos.TOP_CENTER);
        this.getChildren().add( accountName);
        if (CollectionUtil.isNotEmpty(dashBoardMiniApps)){
            log.info("  app size is {}",dashBoardMiniApps.size());
            dashBoardMiniApps.stream().filter(Objects::nonNull).forEach(e-> {

                if (e instanceof Initializable initializable){
                    initializable.initialize(null,null);
                }

                if (e instanceof Node node){
                    this.getChildren().add(node);

                }

            });
        }
    }



    private DashBoardPane(){

    }

    private enum SingleInstance{
        INSTANCE;
        private final DashBoardPane instance;
        SingleInstance(){
            instance = new DashBoardPane();
        }
        private DashBoardPane getInstance(){
            return instance;
        }
    }
    public static DashBoardPane getInstance(){
        return DashBoardPane.SingleInstance.INSTANCE.getInstance();
    }


}
