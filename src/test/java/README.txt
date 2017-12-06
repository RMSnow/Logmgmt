1、1周之内全部改用新接口，并测试通过
2、调用示例写在了Test中
---------------------------------
MesssageBuilder类
可以链式构造MessageDetail，具有原Message类的所有方法，同时支持setParam传入一个javabean，builder会解析内部所有getter加入传参中（不包括null值）
最终调用build即可生成MessageDetail

MessageDetail类
Message类升级，支持传入POST方式传入entity（body）

RestResultGetter类
调用静态方法传入生成的messageDetail对象进行构造，指定http method进行调用，并得到jsonobject结果

maven依赖
<!-- https://mvnrepository.com/artifact/com.alibaba/fastjson -->
<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-databind</artifactId>
    <version>2.5.4</version>
</dependency>
<!--spring相关包-->
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-web</artifactId>
    <version>4.3.1.RELEASE</version>
</dependency>
