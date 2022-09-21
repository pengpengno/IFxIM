## Java 反射类型Type
在反射获取**Method**属性时，基本的Class并不能描述集合类中泛型的数据类型，例如```list<String```、```List<Map<String,Long>>```，在获取通过**Method**或**Class** 获取上述类型时候，得到的结果是```java.util.List```，这并非预期中的数据类型，由此Java引入了**Type**用以解决此场景；
### Type类基本介绍
Type 位于路径```java.lang.reflect.Type```下，官网文档中的介绍如下
```
 * Type is the common superinterface for all types in the Java
 * programming language. These include raw types, parameterized types,
 * array types, type variables and primitive types.
 
 翻译:
 Type 是 Java 编程语言中所有类型的通用超接口。这些包括原始类型、参数化类型、数组类型、类型变量和原始类型。
```
