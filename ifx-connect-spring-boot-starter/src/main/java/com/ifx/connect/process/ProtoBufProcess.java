package com.ifx.connect.process;

import com.google.protobuf.Message;
import com.ifx.connect.enums.ProtocolMessageMapEnum;
import org.springframework.lang.Nullable;
import reactor.netty.Connection;

/***
 * connect  message process
 */
@FunctionalInterface
public interface ProtoBufProcess{

    /***
     * 获取类型
     * @return 返回 业务对应的类型
     */
    public default ProtocolMessageMapEnum type(){
        return null;
    }

    /***
     * process client network IO
     * @param con connection within clinet
     * @param message  IO byte data
     * @throws IllegalArgumentException  when the connection  is invalid  , program will throw exception
     */
    public void process(@Nullable Connection con , Message message) throws IllegalArgumentException;

}
