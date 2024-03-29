package com.ifx.account.mapstruct;

import com.ifx.account.entity.Account;
import com.ifx.account.vo.AccountVo;
import com.ifx.common.base.AccountInfo;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: fulin
 * @Date: 2022/08/02/17:52
 * @Description:
 */
@Mapper(builder = @Builder())
public interface AccountMapper {

    AccountMapper INSTANCE = Mappers.getMapper( AccountMapper.class);

    /**
     * vo转po
     * @param accountVo
     * @return
     */
    Account transform(AccountVo accountVo);

    /**
     * vo转po
     * @param accountVo
     * @return
     */
    Account transform4(AccountVo accountVo, @MappingTarget Account account);

    Account bulidAccount(AccountVo accountVo);


//    default Chat.ChatMessage buildSearch(Iterable<AccountInfo> accountInfos){
//        if (accountInfos == null){
//            return null;
//        }
//        Iterator<AccountInfo> iterator = accountInfos.iterator();
//        ArrayList<com.ifx.connect.proto.Account.AccountInfo> list = new ArrayList<>();
//        if (iterator.hasNext()) {
//            AccountInfo next = iterator.next();
//            com.ifx.connect.proto.Account.AccountInfo accountInfo = ProtoBufMapper.INSTANCE.protocolAccMap(next);
//            list.add(accountInfo);
//        }
//        return OnLineUser.UserSearch.newBuilder().addAllAccounts(list).build();
//    }
    /**
     * po转vo
     * @param account
     * @return
     */
    AccountVo transform4Init(Account account);



    @Mapping(source = "id",target = "userId")
    AccountInfo buildAccountInfo(Account acc);

    List<AccountInfo> buildAccountInfoList(List<Account> accounts);



}
