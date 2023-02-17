//package com.ifx.account.service;
//
//import com.baomidou.mybatisplus.extension.service.IService;
//import com.ifx.account.entity.AccountRelation;
//import com.ifx.account.vo.AccountRelationVo;
//import com.ifx.common.base.AccountInfo;
//
//import java.util.List;
//import java.util.Set;
//
///**
// * 用户关系表服务类
//* @author HP
//* @description 针对表【account_relation(账户关系表)】的数据库操作Service
//* @createDate 2023-01-12 19:16:28
//*/
//public interface AccountRelationService extends IService<AccountRelation> {
//
//
//    public Set<String> listRelationWithAccount(String account);
//
//    /***
//     * 获取所与哦好友关系基本信息
//     * @param account
//     * @return
//     */
//
//    public List<AccountInfo> listAllRelationBaseInfo(String account);
//
//
//    /***
//     * 添加账户关系
//     * @param vo
//     * @return
//     */
//    public Long insertRelation(AccountRelationVo vo);
//
//}
