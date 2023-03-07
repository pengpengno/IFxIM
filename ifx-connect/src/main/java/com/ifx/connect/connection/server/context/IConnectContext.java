package com.ifx.connect.connection.server.context;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.google.inject.Singleton;
import com.ifx.exec.errorMsg.connect.ConnectErrorMsg;
import com.ifx.exec.ex.connect.ConnectException;
import org.springframework.util.Assert;
import reactor.core.publisher.Mono;
import java.util.Objects;

/**
 * connection 容器
 * @author pengpeng
 * @description
 * @date 2023/3/6
 */
@Singleton
public class IConnectContext implements IConnectContextAction {


    private final Cache<String, IConnection> connectionCache = Caffeine.newBuilder()
            .build();;

    private IConnectContext(){

    }


    @Override
    public IConnection applyConnection(String account) throws ConnectException {
        return Mono.just(Objects.requireNonNull(connectionCache.getIfPresent(account)))
                .onErrorResume((throwable) -> throwable instanceof NullPointerException,
                        (throwable -> Mono.error(()-> new ConnectException(ConnectErrorMsg.NOT_FOUND_CONNECTION))))
                .block();
    }


    @Override
    public IConnection putConnection(IConnection connection) {
        Assert.notNull(connection,"connection could not be null , when put new connection in cache");
        Assert.notNull(connection.accountInfo(),"connection accountInfo could not be null , when put new connection in cache");
        Assert.notNull(connection.accountInfo().getAccount(),"connection accountInfo  accout could not be null , when put new connection in cache");
        Assert.notNull(connection.channel(),"connection channel could not be null , when put new connection in cache");
        Assert.notNull(connection.connection(),"connection connection could not be null , when put new connection in cache");
        String account = connection.accountInfo().getAccount();
        connectionCache.put(account,connection);
        return connection;
    }

    @Override
    public Boolean closeAndRmConnection(String account) throws ConnectException {
        applyConnection(account).close();
        return null;
    }



}
