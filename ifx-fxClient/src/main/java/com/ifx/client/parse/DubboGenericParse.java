package com.ifx.client.parse;

import cn.hutool.core.util.StrUtil;
import com.ifx.account.service.AccountService;
import com.ifx.account.vo.AccountBaseInfo;
import com.ifx.connect.proto.dubbo.DubboApiMetaData;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Slf4j
@Component
public class DubboGenericParse {


    /**
     * ！！！！ 此处传入的 args 必须严格按照接口签名的参数顺序注入
     * e.g
     * @see AccountService#login(AccountBaseInfo)
     * 上面的接口只有一个参数 那么在注入 args 的时候就需要 做如下操作
     * AccountBaseInfo accBaseInfo = new AccountBaseInfo()
     * List<Object>
     * @param interFaceClass
     * @param methodName
     * @param args
     * @return
     */
    @SneakyThrows
    public DubboApiMetaData applyMeta(Class interFaceClass, String methodName , List<Object> args){

        String name = interFaceClass.getPackage().getName();

        Method method = Arrays.stream(interFaceClass.getMethods()).filter(meNa -> StrUtil.equals(meNa.getName(), methodName)).
                findFirst().
                orElseThrow(() -> {
                    log.error("  {}  can not find method {} ", name, methodName);
                    return new Exception();
                });

        Class<?>[] parameterTypes = method.getParameterTypes();
        String[] paramTypes = Arrays.stream(parameterTypes).map(paramType -> {
            return paramType.getName();
        }).toArray(size-> new String[size]);
        Object[] objects = args.stream().toArray(size -> new String[size]);
        DubboApiMetaData metaData = new DubboApiMetaData();
        metaData.setApiInterFacePath(name);
        metaData.setArgsType(paramTypes);
        metaData.setArgs(objects);
        metaData.setMethod(methodName);
        return metaData;


    }

    @SneakyThrows
    public DubboApiMetaData applyMeta(Class interFaceClass, Method method, List<Object> args){

        String name = interFaceClass.getName();
        String methodName = method.getName();
        Class<?>[] parameterTypes = method.getParameterTypes();
        String[] paramTypes = Arrays.stream(parameterTypes).map(paramType -> {
            return paramType.getName();
        }).toArray(size-> new String[size]);
        Object[] objects = args.stream().toArray(size -> new String[size]);
        DubboApiMetaData metaData = new DubboApiMetaData();
        metaData.setApiInterFacePath(name);
        metaData.setArgsType(paramTypes);
        metaData.setMethod(method.getName());
        metaData.setArgs(objects);
        return metaData;

    }

    public static void main(String[] args) {
        Integer[] integers = new Integer[]{12,34,4,5,5,5};
        String[] strings = Arrays.stream(integers).map(k -> k.toString()).toArray(size -> new String[size]);
        Arrays.stream(strings).forEach(System.out::print);

        System.out.println(strings);

        System.out.println(AccountService.class.getPackage().getName());
        System.out.println(AccountService.class.getPackage().getName());
    }
}
