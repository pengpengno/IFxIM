## Fxml 生命周期浅谈
手头正在开发微型本地化的IM客户端需要使用到GUI，选择了FX 综上需要深入研究此工具，本文将会介绍关于fxml整个生命周期的源码分析及其应用最佳实践

### 一、初始化流程
#### 1.1 开始一个fxml资源加载
> 指定路径加载

```
    private Scene applyScene(URL url) {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(url);
            fxmlLoader.setControllerFactory(SpringUtil::getBean);
            Scene scene = new Scene(fxmlLoader.load());
            return scene;
        }
        catch (Exception e){
            log.error("create stage fail {}", ExceptionUtil.stacktraceToString(e));
        }
        return null;
    }
    
```

#### 1.2 源码分析
##### 1.2.1 load 源码

### 结束流程
