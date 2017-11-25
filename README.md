[API说明文档](https://www.zybuluo.com/RuuuuuunNER/note/940255)

## To Modify

### 数据库设计

【增加日志】  
1. RequestLog  
（1）URL的处理：是否不存HTTP/1.1这样的信息  
（2）URI：是否要存储到URI，即把URL细化到 course、courseware、class等的存储  
2. 查询时，\n的处理  
3. DAO层，serviceName与name（原有的name属性删去）  
4. 连接MongoDB时，控制台的输出  

