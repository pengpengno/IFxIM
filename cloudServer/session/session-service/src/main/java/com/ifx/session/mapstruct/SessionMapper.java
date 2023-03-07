package com.ifx.session.mapstruct;

import com.ifx.session.entity.Session;
import com.ifx.session.entity.SessionAccount;
import com.ifx.session.vo.session.SessionInfoVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 *
 * @description:  消息实体映射工具
 */
@Mapper
public interface SessionMapper {

    SessionMapper INSTANCE = Mappers.getMapper(SessionMapper.class);

    /**
     * @param sessionAccount
     * @return
     */
    SessionInfoVo transform(SessionAccount sessionAccount);


    /**
     * @param sessionAccount
     * @return
     */
    @Mappings(
        @Mapping(source = "group",target = "sessionGroup")
    )
    Session transform(SessionInfoVo sessionAccount);



}
