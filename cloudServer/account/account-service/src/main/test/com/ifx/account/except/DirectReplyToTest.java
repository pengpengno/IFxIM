package com.ifx.account.except;

import com.alibaba.fastjson2.JSON;
import com.google.protobuf.InvalidProtocolBufferException;
import com.ifx.account.AccountApplication;
import com.ifx.common.base.AccountInfo;
import com.ifx.connect.mapstruct.ProtoBufMapper;
import com.ifx.connect.proto.Account;
import com.ifx.connect.proto.OnLineUser;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @author pengpeng
 * @description
 * @date 2023/3/27
 */
@Slf4j
@SpringBootTest(classes = {AccountApplication.class})
@RunWith(value = SpringJUnit4ClassRunner.class)
public class DirectReplyToTest {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void sendUserSearch(){
        OnLineUser.UserSearch pengpeng = OnLineUser.UserSearch.newBuilder()
                .addAccounts(Account.AccountInfo.newBuilder().setAccount("pengpeng").build())
                .build();
//        OnLineUser.UserSearch userSearch = SessionMapper.INSTANCE.buildSearch(accountInfos);
        Message message = new Message(pengpeng.toByteArray());
        Message returnMessage = rabbitTemplate.sendAndReceive(message, new CorrelationData());
        byte[] body = returnMessage.getBody();
        try {
            OnLineUser.UserSearch returnSearch = OnLineUser.UserSearch.parseFrom(body);
            log.info("发送的数据为 {} ", JSON.toJSONString(returnSearch));
            List<Account.AccountInfo> accountsList = returnSearch.getAccountsList();
            List<AccountInfo> accountInfos = ProtoBufMapper.INSTANCE.proto2AccIterable(accountsList);
            log.info("返回的数据为 {} ", JSON.toJSONString(accountInfos));
        } catch (InvalidProtocolBufferException e) {
            throw new RuntimeException("格式异常！");
        }

    }
}
