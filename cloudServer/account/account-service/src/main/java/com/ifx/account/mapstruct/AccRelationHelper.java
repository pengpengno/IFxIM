package com.ifx.account.mapstruct;

import com.ifx.account.entity.AccountRelation;
import com.ifx.account.vo.AccountRelationVo;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author pengpeng
 * @description
 * @date 2023/2/28
 */
@Mapper(builder = @Builder())

public interface AccRelationHelper {

    AccRelationHelper INSTANCE = Mappers.getMapper( AccRelationHelper.class);

    public AccountRelation tran2Entity(AccountRelationVo vo);
}
