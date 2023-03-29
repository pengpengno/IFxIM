package com.ifx.session.mapstruct;

import com.ifx.session.entity.Session;
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
    @Mappings(
        {
            @Mapping(source = "sessionId",target = "id"),
            @Mapping(source = "createInfo.userId",target = "createUserId")
        }
    )
    Session tyrmore(SessionInfoVo sessionAccount);


    /**
     * @return
     */
    @Mappings({
            @Mapping(source = "id",target = "sessionId"),
            @Mapping(source = "createUserId",target = "createInfo.userId")
    }
    )
    SessionInfoVo tyrmore(Session session);


}
