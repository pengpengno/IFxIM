package com.ifx.client.util;

import com.ifx.client.ClientApplication;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.List;

public class ClientFileUtil {
    /**
     * 获取路劲下所有的fxml
     * 用作 自动化装配
     *
     * @param path
     * @return 返回fxml文件
     */
    public List<File>  getAllFxml(String path){
//TODO
        File file = new File(path);
        if (file.isDirectory()){
//            Arrays.stream(file.list()).map(getAllFxml())

        }else{

        }
//        getAllFxml()
        return null;
    }

    public static void main(String[] args) {
//        File file = new File("com//");
//        String[] list = file.list();
//        System.out.println(list);
//        System.out.println(ClientApplication.class.getPackage().getName());
    }

}
