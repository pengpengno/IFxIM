package com.ifx.client.app.pane.viewMain;

import com.ifx.account.vo.session.SessionInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author pengpeng
 * @description
 * @date 2023/7/8
 */
@Component
public class ShowAction {

    @Autowired
    private SessionMainView sessionMainView;

    public Mono<Void> showSession(Flux<SessionInfoVo> sessionInfoVoFlux){
        return sessionMainView.initSessionRefresh(sessionInfoVoFlux)
                ;


    }
}
