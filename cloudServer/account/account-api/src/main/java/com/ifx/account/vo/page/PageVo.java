package com.ifx.account.vo.page;

//import javax.validation.constraints.Min;

import jakarta.validation.constraints.Min;

/**
 * @author pengpeng
 * @date 2023/7/26
 */
public record PageVo(
        @Min(value = 0,message = "pageNumber  index  start at zero")
        Integer pageNumber,
        @Min(value = 1, message = "pageSize at least  1")
        Integer pageSize
) {

        public static PageVo defaultPageVo(){
                return new PageVo(0,100);
        }
}
