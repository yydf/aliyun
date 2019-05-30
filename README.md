# aliyun
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/cn.4coder/aliyun/badge.svg)](https://maven-badges.herokuapp.com/maven-central/cn.4coder/aliyun/)
[![GitHub release](https://img.shields.io/github/release/yydf/aliyun.svg)](https://github.com/yydf/aliyun/releases)
[![license](https://img.shields.io/github/license/mashape/apistatus.svg)](https://raw.githubusercontent.com/yydf/aliyun/master/LICENSE)
![Jar Size](https://img.shields.io/badge/jar--size-9.20k-blue.svg)

环境
-------------
- JDK 7

如何使用
-----------------------

* 添加dependency到POM文件::

```
<dependency>
    <groupId>cn.4coder</groupId>
    <artifactId>aliyun</artifactId>
    <version>0.0.2</version>
</dependency>
```

* 编码:

```
AcsClient.register(accessKeyId, secretAccessKey);
System.out.println(AcsClient.getInstance().sendSms("XX", mobile, "SMS_XX", "{\"code\":\"1234\"}"));

OssClient.register(accessKeyId, secretAccessKey).setBucket("test");
OssClient.getInstance().putObject("test.txt", new FileInputStream(new File("d:/a.txt")));
```
