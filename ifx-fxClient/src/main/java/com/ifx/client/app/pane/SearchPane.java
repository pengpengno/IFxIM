package com.ifx.client.app.pane;

import com.ifx.common.base.AccountInfo;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SearchPane extends FlowPane {

    private List<AccountInfo> accountInfoList;

    private ConcurrentHashMap<String,AccountMiniPane> concurrentHashMap;

    private enum INSTANCE{
        INSTANCE;
        public static SearchPane
    }

    public void init (){
        this.setHgap(5);
        this.setVgap(5);
        this.setPadding(new Insets(15,15,15,15));
        accountInfoList.stream().forEach(e->{
            AccountMiniPane accountMiniPane = new AccountMiniPane(e);
            getAccountMap().computeIfAbsent(e.getAccount(),(key)-> accountMiniPane);
        });
    }
    private void initAccountMap(){
        if (concurrentHashMap == null){
            concurrentHashMap = new ConcurrentHashMap<>();
        }
        return;
    }

    private ConcurrentHashMap<String,AccountMiniPane> getAccountMap(){
        initAccountMap();
        return concurrentHashMap;
    }

    private class AccountMiniPane implements Initializable {

        public AccountInfo accountInfo;

        private Label name;

        private ImageView iconView;

        public AccountMiniPane(){

        }
        public AccountMiniPane (AccountInfo accountInfo)
        {
            this.accountInfo = accountInfo;
        }

        @Override
        public void initialize(URL location, ResourceBundle resources) {

        }

        public void init(){
//        1.初始化标签文本
//        2.初始化容器大小
            name.setText(accountInfo.getUserName());
            name.setLayoutX(20d);
            name.setLayoutY(10d);
        }

    }
}
