package com.ifx.videoTest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author pengpeng
 * @description
 * @date 2023/1/10
 */
@Accessors(chain=true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Datas implements Serializable {
    private static final long serialVersionUID = 1L;
    private byte type;
    private long data_size;
    private int isfen;
    private int isend;
    private String user_name;
    private String file_name;
    private byte[] datas;

    public Datas(byte type, String user_name, byte[] datas) {
        super();
        this.type = type;
        this.user_name = user_name;
        this.datas = datas;
    }

}