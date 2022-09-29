## RxJava : Jvm 响应式扩展集
RxJava 是  实现了响应式的 Java vm： 通过使用可观察的序列
1.0 版本将会再2018年3月31日停用,不会有开发，支持，维护，pr和更新计划。最后一个版本1.3.8的 Javadoc 仍然可以访问

### 快速开始
#### 准备依赖
首先 需要将 RxJava3 加入到你的工程中,举个例子,如果使用Gradle 构建依赖
```
implementation "io.reactivex.rxjava3:rxjava:3.x.y"
```
(请使用最新的版本号替换```x```和```y```)

#### Hello world
第二步是开始编写一个 ```Hello World``` 
```
package rxjava.examples;

import io.reactivex.rxjava3.core.*;

public class HelloWorld {
    public static void main(String[] args) {
        Flowable.just("Hello world").subscribe(System.out::println);
    }
}
```
请注意 RxJava3组件现在位于 io.reactivex.rxjava3下，而基类和接口位于 io.reactivex.rxjava3.core 下。

#### 基类
RxJava 3 具有几个基础类, 你可以下如下路径找到这些运算符
```
io.reactivex.rxjava3.core.Flowable: 0..N flows, supporting Reactive-Streams and backpressure
io.reactivex.rxjava3.core.Observable: 0..N flows, no backpressure,
io.reactivex.rxjava3.core.Single: a flow of exactly 1 item or an error,
io.reactivex.rxjava3.core.Completable: a flow without items but only a completion or error signal,
io.reactivex.rxjava3.core.Maybe: a flow with no items, exactly one item or an error.
```