# 日志管理

## 备注
由于`markdown`的解析不适配问题，原文档可参见：[详细文档入口](https://www.zybuluo.com/RuuuuuunNER/note/972563).

----------
## 概述
本服务为其他服务提供日志管理的功能，主要包含以下三个方面：

 - 接收日志：其他服务通过在配置文件`.yml`中增加日志映射属性，即可将产生的日志自动传送至本服务（详见[服务配置日志方法](#yml)）.
 - 查询日志：访问本服务提供的[api](#query)，可查询日志列表.
 - 删除日志：访问本服务提供的[api](#delete)，可删除日志列表


----------
## 使用说明

### 配置文件
服务可通过`logmgmt.yml`文件更改下列配置信息：  
```
name: logmgmt
url: http://123.207.73.150
ip: 119.29.228.21
logmgmtPort: 9999
syslogPort: 9898
mongodbHost: 119.29.228.21
mongodbPort: 27017
mongodbUserName: logmgt
mongodbPassword: logmgt

server:
  type: simple
  connector:
    type: http
    port: 9999
```
各属性的说明如下表所示：  
|属性名|说明|默认值|
|:-:|:-:|:-:|
|name|本服务名称|logmgmt|
|url|注册api的url|http://123.207.73.150|
|ip|注册api的ip地址|119.29.228.21|
|logmgmtPort|注册api的端口（进行日志**查询**与**删除**）|9999|
|syslogPort|接收（**增加**）日志的端口|9898|
|mongodbHost|数据库主机|119.29.228.21|
|mongodbPort|数据库端口|27017|
|mongodbUserName|数据库管理员用户名|logmgt|
|mongodbPassword|数据库管理员用户名|logmgt|  
  
### Mongodb数据库

#### 配置方法
在服务器上进行首次配置时，应依次执行下列命令：
>（1）Mongodb安装
>（2）创建数据库：`use logs`
>（3）创建用户：`db.createUser({user:"logmgt",pwd:"logmgt",roles:[{"role":"readWrite","db":"logs"}]})`
>（4）检查用户权限：`db.auth("logmgt","logmgt")`，若返回值为`1`，则用户权限设置成功

#### 常用命令
1. 数据库操作
> 显示所有数据库：`show dbs`
> 创建/切换数据库：`use logs`

2. 集合（表）操作
> 显示所有集合：`show collections`
> 删除集合：`db.[COLLECTION NAME].drop()`
> 查询文档：`db.[COLLECTION NAME].find().pretty()`
> 查询集合中文档总数：`db.[COLLECTION NAME].find().pretty().count()`

3. 更多命令详见：[MongoDB](http://www.yiibai.com/mongodb/mongodb_query_document.html)


----------
## 接收日志
<span id="yml"></span>
### 服务配置日志方法
日志服务可接收其他服务自动传送的日志，只需在其他服务的`.yml`配置文件中添加如下信息：  
  
以日志服务为例，原配置文件为：  
```
name: courseservice
ip: 119.29.132.15
port: 7000
url: http://123.207.73.150

server:
  type: simple
  connector:
    type: http
    port: 7000
```

添加日志的映射功能后为：

```
name: courseservice
ip: 119.29.132.15
port: 7000
url: http://123.207.73.150

server:
  requestLog:
    appenders:
      - type: syslog
        host: localhost
        port: 9898
        facility: local0
        threshold: ALL

  type: simple
  connector:
    type: http
    port: 7000

logging:

  appenders:
    - type: syslog
      host: localhost
      port: 9898
      threshold: ALL
```
注：在使用时，`requestLog`与`logging`的`host`属性值应与本服务的`host`相同，`port`属性值应与本服务的`syslogPort`属性值相同。

### 日志分类说明
如上方的配置信息所显示，本服务存储的日志共有两类：`logging`与`requestLog`。两类日志的介绍，以及资源所对应的`URI`如下表所示：

|类别|名称|说明|URI|
|:-:|:-:|:-:|:-:|
|logging|运行日志|服务运行时所产生的日志，主要包括INFO信息与**报错信息**等|`loggings`|
|request|访问日志|服务的api受外界访问时所产生的日志|`requests`|


----------
<span id="query"></span>
## 查询日志
|类别|HTTP动词|Path|
|:-:|:-:|:-:|
|运行日志|GET|`/api/v1/loggings`|
|访问日志|GET|`/api/v1/requests`|

访问api的总路径为：`[API前缀] Path [参数列表]`.  
  
其中`[API前缀]`见[附录](#prefix)，`Path`如上表所示，`[参数列表]`将在下文详细叙述。

### 运行日志查询
查询运行日志`logging`时，需使用如下的参数列表：  

|参数|说明|示例|备注|
|:-:|:-:|:-:|:-:|
|serviceName|服务名|`courseservice`|必选|
|fromId|起始ID（不包括本身）|`5a23732ee6022d0372e6e570`|可选|
|level|日志级别|`6`|可选|
|host|主机名|`localhost`|可选|
|fromTimeStamp|起始日期（包括本身）|见下表|可选|
|toTimeStamp|截止日期（包括本身）|见下表|可选|
|errDetails|详细错误信息查看|`1`|可选|
|limit|查询总数量|`50`|可选|

其中，`level`代表运行日志的级别，各值代表的日志级别参见[附录](#level).  
  
`fromTimeStamp`与`toTimeStamp`可采用如下两种方式：
|种类|格式|示例|
|:-:|:-:|:-:|
|1|yyyy-MM-dd hh:mm:ss|`2017-11-24 23:11:40`|
|2|yyyy-MM-dd|`2017-11-24`|
  
`errDetails`参数的默认值为`0`，表示**不查看错误细节**，将其值改为`1`，即可查看错误日志的具体报错堆栈信息。

#### 示例
注：如下所述的示例均为本地数据库查询，在实际使用时需修改API前缀，详见[附录说明](#prefix)。  
  
在本地数据库中，查找服务名为`test`的日志：  
  
`localhost:9999/application/api/v1/loggings?serviceName=test&level=3&fromTimeStamp=2017-12-03 11:44:46`  
  
查询成功时，返回结果为：

```
{
    "msg": "1 results.",
    "status": 200,
    "data": [
        {
            "id": "5a23732ee6022d0372e6e56f",
            "facility": "16",
            "timestamp": "2017-12-03 11:44:46",
            "level": 3,
            "host": "snow.local",
            "serviceName": "test",
            "className": "io.dropwizard.jersey.errors.LoggingExceptionMapper",
            "message": "[dw-20 - GET /application/api/v2/course] Error handling a request: ef745256c764503d [BASIC ERROR-DETAILS] java.lang.NullPointerException: null",
            "errDetails": null
        }
    ]
}
```
对返回结果的说明，详见[附录](#status)。

### 访问日志查询
查询访问日志`requestLog`时，需使用如下的参数列表：  

|参数|说明|示例|备注|
|:-:|:-:|:-:|:-:|
|serviceName|服务名|`courseservice`|必选|
|fromId|起始ID（不包括本身）|`5a23732ee6022d0372e6e570`|可选|
|host|主机名|`localhost`|可选|
|fromDateTime|起始日期（包括本身）|见下表|可选|
|toDateTime|截止日期（包括本身）|见下表|可选|
|method|访问方法|`GET`|可选|
|status|返回状态码|`500`|可选|
|limit|查询总数量|`50`|可选|

其中，`fromDateTime`与`toDateTime`可采用如下两种方式：
|种类|格式|示例|
|:-:|:-:|:-:|
|1|yyyy-MM-dd hh:mm:ss|`2017-11-24 23:11:40`|
|2|yyyy-MM-dd|`2017-11-24`|

#### 示例
在本地数据库中，查找服务名为`test`的日志：  
  
`localhost:9999/application/api/v1/requests?serviceName=test`  
  
成功时返回结果：
```
{
    "msg": "3 results.",
    "status": 200,
    "data": [
        {
            "id": "5a237324e6022d0372e6e56d",
            "host": "snow.local",
            "serviceName": "test",
            "className": "dw-21",
            "facility": 16,
            "clientIP": "0:0:0:0:0:0:0:1",
            "datetime": "2017-12-03 11:44:35",
            "method": "GET",
            "url": "/application/api/v2/class",
            "status": 200,
            "client": "PostmanRuntime/6.4.1"
        },
        {
            "id": "5a23732ae6022d0372e6e56e",
            "host": "snow.local",
            "serviceName": "test",
            "className": "dw-27",
            "facility": 16,
            "clientIP": "0:0:0:0:0:0:0:1",
            "datetime": "2017-12-03 11:44:41",
            "method": "GET",
            "url": "/application/api/v2/courseware",
            "status": 200,
            "client": "PostmanRuntime/6.4.1"
        },
        {
            "id": "5a23732ee6022d0372e6e570",
            "host": "snow.local",
            "serviceName": "test",
            "className": "dw-20",
            "facility": 16,
            "clientIP": "0:0:0:0:0:0:0:1",
            "datetime": "2017-12-03 11:44:46",
            "method": "GET",
            "url": "/application/api/v2/course",
            "status": 500,
            "client": "PostmanRuntime/6.4.1"
        }
    ]
}
```

----------
<span id="delete"></span>
## 删除日志
|类别|HTTP动词|Path|
|:-:|:-:|:-:|
|运行日志|DELETE|`/api/v1/loggings`|
|访问日志|DELETE|`/api/v1/requests`|

访问api的总路径为：`[API前缀] Path [参数列表]`.  
  
其中`[API前缀]`见[附录](#prefix)，`Path`如上表所示，`[参数列表]`将在下文详细叙述。

### 运行日志删除
删除运行日志`logging`时，需使用如下的参数列表：  

|参数|说明|示例|备注|
|:-:|:-:|:-:|:-:|
|serviceName|服务名|`courseservice`|必选|
|level|日志级别|`6`|可选|
|host|主机名|`localhost`|可选|
|fromTimeStamp|起始日期（包括本身）|如上文所示|可选|
|toTimeStamp|截止日期（包括本身）|如上文所示|可选|

#### 示例
在本地数据库中，删除服务名为`courseservice`的日志：  
  
`localhost:9999/application/api/v1/requests?serviceName=courseservice`  
  
删除失败时，返回结果为：  
```
{
    "msg": "NOT FOUND",
    "status": 404,
    "data": ""
}
```

### 访问日志删除
删除访问日志`requestLog`时，需使用如下的参数列表：  

|参数|说明|示例|备注|
|:-:|:-:|:-:|:-:|
|serviceName|服务名|`courseservice`|必选|
|host|主机名|`localhost`|可选|
|fromDateTime|起始日期（包括本身）|如上文所示|可选|
|toDateTime|截止日期（包括本身）|如上文所示|可选|
|method|访问方法|`GET`|可选|
|status|返回状态码|`500`|可选|

#### 示例
在本地数据库中，删除服务名为`test`的日志：  
  
`localhost:9999/application/api/v1/loggings?serviceName=test&level=6`  
  
删除成功时，返回结果为： 
```
{
    "msg": "12 results has been deleted.",
    "status": 200,
    "data": ""
}
```

----------
## 附录

<span id="status"></span>
### 返回结果说明
返回结果格式如下：
```
{
    "msg" : 字段,
    "status" : 状态码,
    "data" : 数组或对象
}
```

状态码`status`说明：
```
Successful 200

Client Error 4XX:
404 NOT FOUND - [*]：用户发出的请求针对的是不存在的记录，服务器没有进行操作，该操作是幂等的。

Server Error 5XX:
500 INTERNAL SERVER ERROR - [*]：服务器发生错误，用户将无法判断发出的请求是否成功。
```

<span id="prefix"></span>
### API前缀说明
访问本服务时，API前缀为：`[主机名]:[logmgmtPort]/application`.  
  
如：在本地访问时，前缀为`localhost:9999/application`.

<span id="level"></span>
### 日志级别说明

各`level`值所对应的日志级别如下所示：
```
0       Emergency: system is unusable
1       Alert: action must be taken immediately
2       Critical: critical conditions
3       Error: error conditions
4       Warning: warning conditions
5       Notice: normal but significant condition
6       Informational: informational messages
7       Debug: debug-level messages
```
其中，`level`值越低的日志，优先级越高。