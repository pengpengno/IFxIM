package com.ifx.connect.connection.client;

import com.ifx.connect.proto.Protocol;
import com.ifx.connect.task.handler.TaskHandler;
import com.ifx.exec.ex.net.NetException;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.SignalType;

/**
 * 客户端基础行为
 */
public interface ClientAction {


    /**
     * 发送协议包{@link Protocol}
     * @param protocol
     */
    public void sendJsonMsg( Protocol protocol)  throws NetException;

    /**
     * 发送 基于 回调的协议包 即 当任务在成功返回后将会执行{@link  TaskHandler}中的句柄任务
     * {@code  }
     * @param protocol
     * @param taskHandler
     */
    public void sendJsonMsg(Protocol protocol, TaskHandler taskHandler);



//    public void sendJsonMsg(Mono<Protocol> protocolMono, );

    /**
     * 发送消息协议
     * @param taskHandlerFlux
     */
//    public default void sendJsonMsg(Flux<TaskHandler> taskHandlerFlux){
//        taskHandlerFlux.subscribe(new BaseSubscriber<TaskHandler>() {
//            @Override
//            protected void hookOnCancel() {
//                super.hookOnCancel();
//            }
//
//            @Override
//            protected void hookFinally(SignalType type) {
//                super.hookFinally(type);
//            }
//        })
//    }


}
