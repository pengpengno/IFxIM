## Im系统中的协议选择
本文中的 即时通讯系统主要设计为C/S架构 ， 使用TCP进行传输，所以在 序列化业务信息的时候要选择合适的协议，下面将会介绍几种协议 的实现方式以及其特性


跟移动端IM中追求数据传输效率、网络流量消耗等需求一样，随手记客户端与服务端交互的过程中，对部分数据的传输大小和效率也有较高的要求，普通的数据格式如 JSON 或者 XML 已经不能满足，因此决定采用 Google 推出的 Protocol Buffers 以达到数据高效传输。

金蝶随手记团队分享：还在用JSON? Protobuf让数据传输更省更快(原理篇)_07222158_Ey54.png
2、系列文章

本文是系列文章中的第1篇，总目录如下：

《金蝶随手记团队分享：还在用JSON? Protobuf让数据传输更省更快(原理篇)》（本文）
《金蝶随手记团队分享：还在用JSON? Protobuf让数据传输更省更快(实战篇)》

另外，如果您还打算系统地了解IM的开发知识，可以阅读《新手入门一篇就够：从零开发移动端IM》。
3、参考资料

《Protobuf通信协议详解：代码演示、详细原理介绍等》
《一个基于Protocol Buffer的Java代码演示》
《如何选择即时通讯应用的数据传输格式》
《强列建议将Protobuf作为你的即时通讯应用数据传输格式》
《全方位评测：Protobuf性能到底有没有比JSON快5倍？》
《移动端IM开发需要面对的技术问题（含通信协议选择）》
《简述移动端IM开发的那些坑：架构设计、通信协议和客户端》
《理论联系实际：一套典型的IM通信协议设计详解》
《详解如何在NodeJS中使用Google的Protobuf》
>> 更多同类文章 ……
4、Protubuf介绍

Protocol buffers 为 Google 提出的一种跨平台、多语言支持且开源的序列化数据格式。相对于类似的 XML 和 JSON，Protocol buffers 更为小巧、快速和简单。其语法目前分为proto2和proto3两种格式。

相对于传统的 XML 和 JSON, Protocol buffers 的优势主要在于：更加小、更加快。

对于自定义的数据结构，Protobuf 可以通过生成器生成不同语言的源代码文件，读写操作都非常方便。

假设现在有下面 JSON 格式的数据:
{
        "id":1,
        "name":"jojo",
        "email":"123@qq.com",
}


使用 JSON 进行编码，得出byte长度为43的的二进制数据：
7b226964 223a312c 226e616d 65223a22 6a6f6a6f 222c2265 6d61696c 223a2231 32334071 712e636f 6d227d


如果使用 Protobuf 进行编码，得到的二进制数据仅有20个字节：
0a046a6f 6a6f1001 1a0a3132 33407171 2e636f6d


5、编码原理

相对于基于纯文本的数据结构如 JSON、XML等，Protobuf 能够达到小巧、快速的最大原因在于其独特的编码方式。《Protobuf通信协议详解：代码演示、详细原理介绍等》对 Protobuf 的 Encoding 作了很好的解析。

例如，对于int32类型的数字，如果很小的话，protubuf 因为采用了Varint方式，可以只用 1 个字节表示。
6、Varint原理

Varint 中每个字节的最高位 bit 表示此 byte 是否为最后一个 byte 。1 表示后续的 byte 也表示该数字，0 表示此 byte 为结束的 byte。

例如数字 300 用 Varint 表示为 1010 1100 0000 0010：
金蝶随手记团队分享：还在用JSON? Protobuf让数据传输更省更快(原理篇)_1.jpg
图片源自《Protobuf通信协议详解：代码演示、详细原理介绍等》

注意：
需要注意解析的时候会首先将两个 byte 位置互换，因为字节序采用了 little-endian 方式。

但 Varint 方式对于带符号数的编码效果比较差。因为带符号数通常在最高位表示符号，那么使用 Varint 表示一个带符号数无论大小就必须要 5 个 byte（最高位的符号位无法忽略，因此对于 -1 的 Varint 表示就变成了 010001）。

Protobuf 引入了 ZigZag 编码很好地解决了这个问题。
7、ZigZag编码

金蝶随手记团队分享：还在用JSON? Protobuf让数据传输更省更快(原理篇)_2.png

关于 ZigZag 的编码方式，博客园上的一篇博文《整数压缩编码 ZigZag》做出了详细的解释。

金蝶随手记团队分享：还在用JSON? Protobuf让数据传输更省更快(原理篇)_3.png

ZigZag 编码按照数字的绝对值进行升序排序，将整数通过一个 hash 函数h(n) = (n<<1)^(n>>31)（如果是 sint64 h(n) = (n<<1)^(n>>63)）转换为递增的 32 位 bit 流。

关于为什么 64 的 ZigZag 为 80 01，《整数压缩编码 ZigZag》中有关于其编码唯一可译性的解释。

通过 ZigZag 编码，只要绝对值小的数字，都可以用较少位的 byte 表示。解决了负数的 Varint 位数会比较长的问题。
8、T-V and T-L-V

Protobuf 的消息结构是一系列序列化后的Tag-Value对。其中 Tag 由数据的 field 和 writetype组成，Value 为源数据编码后的二进制数据。

假设有这样一个消息:
message Person {
  int32 id = 1;
  string name = 2;
}


其中，id字段的field为1，writetype为int32类型对应的序号。编码后id对应的 Tag 为 (field_number << 3) | wire_type = 0000 1000，其中低位的 3 位标识 writetype，其他位标识field。

每种类型的序号可以从这张表得到:
金蝶随手记团队分享：还在用JSON? Protobuf让数据传输更省更快(原理篇)_4.png

需要注意，对于string类型的数据（在上表中第三行），由于其长度是不定的，所以 T-V的消息结构是不能满足的，需要增加一个标识长度的Length字段，即T-L-V结构。
9、Protobuf的反射机制

Protobuf 本身具有很强的反射机制，可以通过 type name 构造具体的 Message 对象。陈硕的文章《一种自动反射消息类型的 Google Protobuf 网络传输方案》中对 GPB 的反射机制做了详细的分析和源码解读。这里通过 protobuf-objectivec 版本的源码，分析此版本的反射机制。

金蝶随手记团队分享：还在用JSON? Protobuf让数据传输更省更快(原理篇)_5.gif

陈硕对 protobuf 的类结构做出了详细的分析 —— 其反射机制的关键类为Descriptor类：
每个具体 Message Type 对应一个 Descriptor 对象。尽管我们没有直接调用它的函数，但是Descriptor在“根据 type name 创建具体类型的 Message 对象”中扮演了重要的角色，起了桥梁作用。


同时，陈硕根据 GPB 的 C++ 版本源代码分析出其反射的具体机制：DescriptorPool类根据 type name 拿到一个 Descriptor的对象指针，在通过MessageFactory工厂类根据Descriptor实例构造出具体的Message对象。

示例代码如下：
01
02
03
04
05
06
07
08
09
10
11
12
13
14
Message* createMessage(const std::string& typeName)
{
  Message* message = NULL;
  const Descriptor* descriptor = DescriptorPool::generated_pool()->FindMessageTypeByName(typeName);
  if (descriptor)
  {
    const Message* prototype = MessageFactory::generated_factory()->GetPrototype(descriptor);
    if (prototype)
    {
      message = prototype->New();
    }
  }
  return message;
}

注意：
DescriptorPool 包含了程序编译的时候所链接的全部 protobuf Message types
MessageFactory 能创建程序编译的时候所链接的全部 protobuf Message types


10、以Protobuf-objectivec为例

在 OC 环境下，假设有一份 Message 数据结构如下：
1
2
3
4
5
message Person {
  string name = 1;
  int32 id = 2;
  string email = 3;
}

解码此类型消息的二进制数据：
1
Person *newP = [[Person alloc] initWithData:data error:nil];

这里调用了：
1
2
3
- (instancetype)initWithData:(NSData *)data error:(NSError **)errorPtr {
    return [self initWithData:data extensionRegistry:nil error:errorPtr];
}

其内部调用了另一个构造器：
01
02
03
04
05
06
07
08
09
10
11
12
13
14
- (instancetype)initWithData:(NSData *)data
           extensionRegistry:(GPBExtensionRegistry *)extensionRegistry
                       error:(NSError **)errorPtr {
    if ((self = [self init])) {
    @try {
      [self mergeFromData:data extensionRegistry:extensionRegistry];
          //...
    }
    @catch (NSException *exception) {
      //...  
    }
  }
  return self;
}

去掉一些防御代码和错误处理后，可以看到最终由mergeFromData:方法实现构造：
1
2
3
4
5
6
- (void)mergeFromData:(NSData *)data extensionRegistry:(GPBExtensionRegistry *)extensionRegistry {
  GPBCodedInputStream *input = [[GPBCodedInputStream alloc] initWithData:data]; //根据传入的`data`构造出数据流对象
  [self mergeFromCodedInputStream:input extensionRegistry:extensionRegistry]; //通过数据流对象进行merge
  [input checkLastTagWas:0]; //校检
  [input release];
}

这个方法主要做了两件事：

1）通过传入的 data 构造GPBCodedInputStream对象实例；
2）通过上面构造的数据流对象进行 merge 操作。

GPBCodedInputStream负责的工作很简单，主要是把源数据缓存起来，并同时保存一系列的状态信息，例如size, lastTag等。

其数据结构非常简单：
01
02
03
04
05
06
07
08
09
10
11
12
13
14
15
16
17
18
19
typedef struct GPBCodedInputStreamState {
const uint8_t *bytes;
size_t bufferSize;
size_t bufferPos;

// For parsing subsections of an input stream you can put a hard limit on
// how much should be read. Normally the limit is the end of the stream,
// but you can adjust it to anywhere, and if you hit it you will be at the
// end of the stream, until you adjust the limit.
size_t currentLimit;
int32_t lastTag;
NSUInteger recursionDepth;
} GPBCodedInputStreamState;

@interface GPBCodedInputStream () {
@package
struct GPBCodedInputStreamState state_;
NSData *buffer_;
}

merge 操作内部实现比较复杂，首先会拿到一个当前 Message 对象的 Descriptor 实例，这个 Descriptor 实例主要保存 Message 的源文件 Descriptor 和每个 field 的 Descriptor，然后通过循环的方式对 Message 的每个 field 进行赋值。

Descriptor 简化定义如下：
1
2
3
4
5
@interface GPBDescriptor : NSObject<NSCopying>
@property(nonatomic, readonly, strong, nullable) NSArray<GPBFieldDescriptor*> *fields;
@property(nonatomic, readonly, strong, nullable) NSArray<GPBOneofDescriptor*> *oneofs; //用于 repeated 类型的 filed
@property(nonatomic, readonly, assign) GPBFileDescriptor *file;
@end

其中GPBFieldDescriptor定义如下：
01
02
03
04
05
06
07
08
09
10
@interface GPBFieldDescriptor () {
@package
 GPBMessageFieldDescription *description_;
 GPB_UNSAFE_UNRETAINED GPBOneofDescriptor *containingOneof_;

 SEL getSel_;
 SEL setSel_;
 SEL hasOrCountSel_;  // *Count for map<>/repeated fields, has* otherwise.
 SEL setHasSel_;
}

其中GPBMessageFieldDescription保存了 field 的各种信息，如数据类型、filed 类型、filed id等。除此之外，getSel和setSel为这个 field 在对应类的属性的 setter 和 getter 方法。

mergeFromCodedInputStream:方法的简化版实现如下：
```
- (void)mergeFromCodedInputStream:(GPBCodedInputStream *)input
               extensionRegistry:(GPBExtensionRegistry *)extensionRegistry {
 GPBDescriptor *descriptor = [self descriptor]; //生成当前 Message 的`Descriptor`实例
 GPBFileSyntax syntax = descriptor.file.syntax; //syntax 标识.proto文件的语法版本 (proto2/proto3)
 NSUInteger startingIndex = 0; //当前位置
 NSArray *fields = descriptor->fields_; //当前 Message 的所有 fileds

 //循环解码
 for (NSUInteger i = 0; i < fields.count; ++i) {
  //拿到当前位置的`FieldDescriptor`
     GPBFieldDescriptor *fieldDescriptor = fields[startingIndex];
     //判断当前field的类型
     GPBFieldType fieldType = fieldDescriptor.fieldType;
     if (fieldType == GPBFieldTypeSingle) {
       //`MergeSingleFieldFromCodedInputStream` 函数中解码 Single 类型的 field 的数据
       MergeSingleFieldFromCodedInputStream(self, fieldDescriptor, syntax, input, extensionRegistry);
       //当前位置+1
       startingIndex += 1; 
     } else if (fieldType == GPBFieldTypeRepeated) {
        // ...
       // Repeated 解码操作
     } else {  
       // ...
       // 其他类型解码操作
     }
  }  // for(i < numFields)
}
```
可以看到，descriptor在这里是直接通过 Message 对象中的方法拿到的，而不是通过工厂构造：
```
GPBDescriptor *descriptor = [self descriptor];

//`desciptor`方法定义
- (GPBDescriptor *)descriptor {
 return [[self class] descriptor]; 
}
```
这里的descriptor类方法实际上是由GPBMessage的子类具体实现的。

例如在Person这个消息结构中，其descriptor方法定义如下：
01
02
03
04
05
06
07
08
09
10
11
12
13
14
15
16
17
18
19
20
21
+ (GPBDescriptor *)descriptor {
 static GPBDescriptor *descriptor = nil;
 if (!descriptor) {
   static GPBMessageFieldDescription fields[] = {
     {
       .name = "name",
       .dataTypeSpecific.className = NULL,
       .number = Person_FieldNumber_Name,
       .hasIndex = 0,
       .offset = (uint32_t)offsetof(Person__storage_, name),
       .flags = GPBFieldOptional,
       .dataType = GPBDataTypeString,
     },
     //...
     //每个field都会在这里定义出`GPBMessageFieldDescription`
   };
   GPBDescriptor *localDescriptor = //这里会根据fileds和其他一系列参数构造出一个`Descriptor`对象
   descriptor = localDescriptor;
 }
 return descriptor;
}

接下来，在构造出 Message 的 Descriptor 后，会对所有的 fields 进行遍历解码。解码时会根据不同的fieldType调用不同的解码函数。

例如对于fieldType == GPBFieldTypeSingle，会调用 Single 类型的解码函数:
01
02
03
04
05
06
07
08
09
10
11
12
13
14
15
16
17
18
19
20
MergeSingleFieldFromCodedInputStream(self, fieldDescriptor, syntax, input, extensionRegistry);
MergeSingleFieldFromCodedInputStream内部提供了一系列宏定义，针对不同的数据类型进行数据解码。
#define CASE_SINGLE_POD(NAME, TYPE, FUNC_TYPE)                             \
   case GPBDataType##NAME: {                                              \
     TYPE val = GPBCodedInputStreamRead##NAME(&input->state_);            \
     GPBSet##FUNC_TYPE##IvarWithFieldInternal(self, field, val, syntax);  \
     break;                                                               \
           }
#define CASE_SINGLE_OBJECT(NAME)                                           \
   case GPBDataType##NAME: {                                              \
     id val = GPBCodedInputStreamReadRetained##NAME(&input->state_);      \
     GPBSetRetainedObjectIvarWithFieldInternal(self, field, val, syntax); \
     break;                                                               \
   }

     CASE_SINGLE_POD(Int32, int32_t, Int32)
  ...
        
#undef CASE_SINGLE_POD
#undef CASE_SINGLE_OBJECT

例如对于int32类型的数据，最终会调用int32_t GPBCodedInputStreamReadInt32(GPBCodedInputStreamState *state);函数读取数据并赋值。

这里内部实现其实就是对于 Varint 编码的解码操作：
```
int32_t GPBCodedInputStreamReadInt32(GPBCodedInputStreamState *state) {
 int32_t value = ReadRawVarint32(state);
 return value;
}
```
在对数据解码完成后，拿到一个int32_t，此时会调用GPBSetInt32IvarWithFieldInternal进行赋值操作，其简化实现如下：

```

void GPBSetInt32IvarWithFieldInternal(GPBMessage *self,
                                     GPBFieldDescriptor *field,
                                     int32_t value,
                                     GPBFileSyntax syntax) {

 //最终的赋值操作
 //此处`self`为`GPBMessage`实例
 uint8_t *storage = (uint8_t *)self->messageStorage_;
 int32_t *typePtr = (int32_t *)&storage[field->description_->offset];
 *typePtr = value;

}

```

其中typePtr为当前需要赋值的变量的指针。至此，单个 field 的赋值操作已经完成。

总结一下，在 protobuf-objectivec 版本中，反射机制中构建 Message 对象的流程大致为：

1）通过 Message 的具体子类构造其 Descriptor，Descriptor 中包含了所有 field 的 FieldDescriptor；
2）循环通过每个 FieldDescriptor 对当前 Message 对象的指定 field 赋值。
