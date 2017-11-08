# 日志管理

标签（空格分隔）： 在线教育平台

修改3个地方：
（1）name字段 ok
（2）设计两个api ok
（3）content的encode，decode

---

## 使用说明

### 功能概述
本服务采用MongoDB为其他模块提供日志管理服务，数据库包含`log`集合（普通日志）与`error`集合（错误日志），所对应的`URI`分别为`logs`与`errors`。日志管理服务可完成如下工作：

- 新增日志（单个）
- 查询日志（单个／列表）
- 删除日志（单个／列表）

### 请求地址
[HTTP动词]:http:ip:port/application/api/[版本号]/[URI]？[请求参数列表]

- 所使用的`HTTP动词`有如下三种：
- `POST`：增加
- `GET`：查询
- `DELETE`：删除
- 目前维护的版本号为`v1`
- `URI`与`请求参数列表`详见具体功能说明

### 集合设计
####集合

1. `log`集合
2. `error`集合

#### 主键
MongoDB自动将`_id`字段设置为主键，在集合中采用`_id - ObjectID`键值对来标识，设计`ObjectID =  时间戳（到秒）+ 机器号 + 线程号 + 秒内序号`，且其自动生成。


----------
## 返回信息说明


----------
## 具体功能

### 普通日志
普通日志由的URI均为`logs`，由`log`集合维护。

#### 1. 新增日志

#### [功能说明]

在日志列表中新增一条日志。

#### [请求地址]

- HTTP动词：`POST`
- URI：`logs`

#### [请求参数]
|参数|类型|描述|
|:-:|:-:|:-:|
|name|String|服务名|
|content|String|日志内容|

#### [示例]

如：课程服务新建一条普通日志，则需请求地址

POST:http:ip:port/application/api/v1/logs?name=courseservice&content=123.207.73.150 - - [04/Nov/2017:11:43:54 +0000] "GET /application/api/v2/class? HTTP/1.1" 200 - "-" "Apache-HttpClient/4.5.2 (Java/1.8.0_151)" 30

成功时，返回参数：
```
{
"_id" : ObjectID("[特定值]"),
"name" : courseservice,
"ip" : "123.207.73.150",
"datetime" : "04/Nov/2017:11:43:54",
"body" : "GET /application/api/v2/class? HTTP/1.1",
"status" : 200,
"client" : "Apache-HttpClient/4.5.2 (Java/1.8.0_151)"
}
```

#### 2. 查询日志

#### [功能说明]

根据限制条件查询日志列表。

#### [请求地址]

- HTTP动词：`GET`
- URI：`logs`

#### [请求参数]

|参数|类型|是否必填|描述|示例|
|:-:|:-:|:-:|:-:|:-:|
|name|String|是|服务名|courseservice|
|ip|String|否|IP地址|123.207.73.150|
|datetime|Date|否|生成日期|2017-11-04|
|client|String|否|客户端|Apache-HttpClient/4.5.2 (Java/1.8.0_151)|

#### [示例]

要获取课程服务在2017-11-04，IP地址为123.207.73.150的普通日志，则请求地址：

GET:http:ip:port/application/api/v1/logs?name=courseservice&ip=123.207.73.150&datetime=2017-11-04

#### 3. 删除日志

#### [功能说明]

根据限制条件删除日志。

#### [请求地址]

- HTTP动词：`DELETE`
- URI：`logs`

#### [请求参数]
|参数|类型|是否必填|描述|示例|
|:-:|:-:|:-:|:-:|:-:|
|name|String|是|服务名称|courseservice|
|ip|String|否|IP地址|123.207.73.150|
|datetime|Date|否|生成日期|2017-11-04|
|client|String|否|客户端|Apache-HttpClient/4.5.2 (Java/1.8.0_151)|

#### [示例]

要删除课程服务2017-11-04，IP地址为123.207.73.150的普通日志，则请求地址：

DELETE:http:ip:port/application/api/v1/logs?name=courseservice&ip=123.207.73.150&datetime=2017-11-04

### 错误日志
错误日志由的URI均为`logs/errors`，由`error`集合维护。

#### 1. 新增日志

#### [功能说明]

在日志列表中新增一条错误日志。

#### [请求地址]

- HTTP动词：`POST`
- URI：`logs/errors`

#### [请求参数]
|参数|类型|描述|
|:-:|:-:|:-:|
|name|String|服务名|
|content|String|日志内容|

#### [示例]

如：课程服务新建一条错误日志，则需请求地址

POST:http:ip:port/application/api/v1/errors?name=courseservice&content=

成功时，返回参数：
```
{
"_id" : ObjectID("[特定值]"),
"name" : courseservice,
"ip" : "123.207.73.150",
"datetime" : "2017-11-04 11:43:42",
"body" : "Exception in thread "main" java.lang.ArithmeticException: / by zero
at Main.main(Main.java:219)
at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
at java.lang.reflect.Method.invoke(Method.java:498)
at com.intellij.rt.execution.application.AppMain.main(AppMain.java:147)"
}
```

#### 2. 查询日志

#### [功能说明]

根据限制条件查询日志列表。

#### [请求地址]

- HTTP动词：`GET`
- URI：`logs/errors`

#### [请求参数]

|参数|类型|是否必填|描述|示例|
|:-:|:-:|:-:|:-:|:-:|
|name|String|是|服务名|courseservice|
|ip|String|否|IP地址|123.207.73.150|
|datetime|Date|否|生成日期|2017-11-04|
|client|String|否|客户端|Apache-HttpClient/4.5.2 (Java/1.8.0_151)|

#### [示例]

要获取课程服务在2017-11-04，IP地址为123.207.73.150的错误日志，则请求地址：

GET:http:ip:port/application/api/v1/errors?name=courseservice&ip=123.207.73.150&datetime=2017-11-04

#### 3. 删除日志

#### [功能说明]

根据限制条件删除日志。

#### [请求地址]

- HTTP动词：`DELETE`
- URI：`logs/errors`

#### [请求参数]
|参数|类型|是否必填|描述|示例|
|:-:|:-:|:-:|:-:|:-:|
|name|String|是|服务名称|courseservice|
|ip|String|否|IP地址|123.207.73.150|
|datetime|Date|否|生成日期|2017-11-04|
|client|String|否|客户端|Apache-HttpClient/4.5.2 (Java/1.8.0_151)|

#### [示例]

要删除课程服务2017-11-04，IP地址为123.207.73.150的错误日志，则请求地址：

DELETE:http:ip:port/application/api/v1/errors?name=courseservice&ip=123.207.73.150&datetime=2017-11-04
