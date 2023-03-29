package com.ifx.account.util;

import cn.hutool.bloomfilter.BitMapBloomFilter;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.StopWatch;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.Set;

/**
 * @author pengpeng
 * @description
 * @date 2023/3/22
 */
@Slf4j
public class BloomFilterTest {

    @Test
    public void bloomPerformanceTest(){
        int size = 200;
        Set<String> sets = CollectionUtil.newHashSet();
        String message = "109the one";
        for (int i=0 ; i<size ;i++ ){
            sets.add(i +"the one");
        }
        BitMapBloomFilter bitMapBloomFilter = new BitMapBloomFilter(19);
        sets.stream().forEach(e-> bitMapBloomFilter.add(e));
        StopWatch stopWatch = new StopWatch();
        stopWatch.start("bloomFilter ");
        bitMapBloomFilter.contains(message);
        stopWatch.stop();
        log.info(" {} cost time is {} nacos",stopWatch.getLastTaskName(),stopWatch.getLastTaskTimeNanos());


        stopWatch.start("hash set contains  ");
        sets.contains(message);
        stopWatch.stop();
        log.info(" {} cost time is {} nacos",stopWatch.getLastTaskName(),stopWatch.getLastTaskTimeNanos());

    }
}
