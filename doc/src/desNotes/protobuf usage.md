###  Quick Start 
#### 1. DownLoad ProtoBUf Compiler
It is recommended to download protobuf through the following way;

- Download from offical
- User apt (For Linux) 


pls download the package from [Protobuf Offical Download Document](https://github.com/protocolbuffers/protobuf/releases/tag/v22.2) .
On other hand , Ubuntu could use the apt order below
```asciidoc
 sudo apt install protobuf-compiler
```
#### 2. Define the proto
First of all , This article is refer to the [ProtoBuf Code Guide Java](https://protobuf.dev/reference/java/java-generated/#invocation)


#### 3. Compile Proto
```asciidoc
protoc --proto_path=/home/pengpeng/workdir/github/pengpengon/IFxIM/ifx-connect/src/main/proto/ --java_out=/home/pengpeng/workdir/github/pengpengon/IFxIM/ifx-connect/src/main/java/ Message.proto
```

#### 4. Write And Read In Network Transport
