package com.ifx.client.spi;

import com.ifx.connect.proto.Protocol;
import com.ifx.connect.spi.ClientBusinessSPI;
import com.ifx.connect.task.TaskManager;
import javafx.application.Platform;
import lombok.extern.slf4j.Slf4j;

/**
 * 客户端JavaFx平台处理回调业务
 * @author pengpeng
 * @description
 * @date 2023/2/3
 */
@Slf4j
public class ClientJavaFxBusiness implements ClientBusinessSPI {

    private  TaskManager taskManager;
    public ClientJavaFxBusiness(){
        super();
        taskManager = TaskManager.getInstance();
    }


    @Override
    public Runnable doBusiness() {
        return ()-> log.info("尚未实现");
    }

    @Override
    public Runnable doBusiness(Protocol protocol) {
        return ()-> Platform.runLater(()-> taskManager.doTask(protocol));
    }
}
