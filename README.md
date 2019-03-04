# aliyun

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
    <version>0.0.1</version>
</dependency>
```

* 编码:

```
AcsClient.register(accessKeyId, secretAccessKey);
System.out.println(AcsClient.getInstance().sendSms("XX", mobile, "SMS_XX", "{\"code\":\"1234\"}"));
```
