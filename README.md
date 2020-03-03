![Licence](https://img.shields.io/badge/licence-none-green.svg)
[![GitHub Release](https://img.shields.io/github/release/lihengming/spring-boot-api-project-seed.svg)](https://github.com/lihengming/spring-boot-api-project-seed/releases)
## 简介
Spring Boot API Project Seed 是一个基于Spring Boot & MyBatis的种子项目，用于快速构建中小型API、RESTful API项目，该种子项目已经有过多个真实项目的实践，稳定、简单、快速，使我们摆脱那些重复劳动，专注于业务代码的编写，减少加班。下面是一个简单的使用演示，看如何基于本项目在短短几十秒钟内实现一套简单的API，并运行提供服务。

[![请选择超清](https://raw.githubusercontent.com/lihengming/java-codes/master/shared-resources/github-images/project-example-youku.png)](http://v.youku.com/v_show/id_XMjg1NjYwNDgxNg==.html?spm=a2h3j.8428770.3416059.1)
## 特征&提供
- 最佳实践的项目结构、配置文件、精简的POM（[查看项目结构图](https://github.com/lihengming/java-codes/blob/master/shared-resources/github-images/project-struct.png)）
- 统一响应结果封装及生成工具
- 统一异常处理
- 简单的接口签名认证
- 常用基础方法抽象封装
- 使用Druid Spring Boot Starter 集成Druid数据库连接池与监控
- 使用FastJsonHttpMessageConverter，提高JSON序列化速度
- 集成MyBatis、通用Mapper插件、PageHelper分页插件，实现单表业务零SQL
- 提供代码生成器根据表名生成对应的Model、Mapper、MapperXML、Service、ServiceImpl、Controller等基础代码，其中Controller模板默认提供POST和RESTful两套，根据需求在```CodeGenerator.genController(tableName)```方法中自己选择，默认使用POST模板。代码模板可根据实际项目的需求来扩展，由于每个公司业务都不太一样，所以只提供了一些比较基础、通用的模板，**主要是提供一个思路**来减少重复代码的编写，我在实际项目的使用中，其实根据公司业务的抽象编写了大量的模板。另外，使用模板也有助于保持团队代码风格的统一
- 另有彩蛋，待你探索

## 目录模块介绍
1. model：数据模型层。主要是对数据的封装，model下分为三块do、dto、bo
    * BO（Business Object）：业务对象。由Service层输出的封装业务逻辑的对象。所有http请求的入参对象都带上BO结尾。
    * PO（Persistent Object）：与数据库表结构一一对应，通过DAO层向上传输数据源对象。所有的数据库对象都以PO结尾。
    * DTO（Data Transfer Object）：数据传输对象，Service或Manager向外传输的对象。http请求返回外部都以DTO结尾。

2. web：主要是对访问控制进行转发，各类基本参数校验，或者不复用的业务简单处理等
    * controller：包含所有的对外提供的http接口

3. service：业务逻辑处理层。

4. mapper：数据访问层。与底层MySQL、Oracle、Hbase等进行数据交互。

5. configurer：主要是基础信息的配置，包含所有的Spring boot的配置信息。

6. core：
    * ServiceException：本系统自定义的业务异常类。该系统需要抛出的异常信息，都使用它
    * Service：Service层的基础接口，其他Service接口请继承该接口
    * AbstractService：实现Service接口的抽象类，封装了对数据库的基本操作。基于通用MyBatis Mapper插件的Service接口的实现
    * Mapper：dao层的基础接口。定制版MyBatis Mapper插件接口，如需其他接口参考官方文档自行添加
    * ResponseDTO：接口响应结果的统一封装，所有响应的结果，必须使用ResponseDTO类封装
    * ResultCode：响应码枚举类
    * ProjectConstant：项目常量类
    
7. manager：负责对于外部对接的接口进行一次包装，供service层使用，理论上client不能被service直接调用。
    * 对第三方平台封装的层，预处理返回结果及转化异常信息；
    * 对Service层通用能力的下沉，如缓存方案、中间件通用处理；
    * 与DAO层交互，对多个DAO的组合复用。

8. utils：主要是包含所有的工具类。

9. enums：主要包含所有的枚举信息，常量信息。
 

## 快速开始
1. 克隆项目
2. 对```test```包内的代码生成器```CodeGenerator```进行配置，主要是JDBC，因为要根据表名来生成代码
3. 如果只是想根据上面的演示来亲自试试的话可以使用```test resources```目录下的```demo-user.sql```，否则忽略该步
3. 输入表名，运行```CodeGenerator.main()```方法，生成基础代码（可能需要刷新项目目录才会出来）
4. 根据业务在基础代码上进行扩展
5. 对开发环境配置文件```application-dev.properties```进行配置，启动项目，Have Fun！
 
## 开发建议
- 表名，建议使用小写，多个单词使用下划线拼接
- Model内成员变量建议与表字段数量对应，如需扩展成员变量（比如连表查询）建议创建DTO，否则需在扩展的成员变量上加```@Transient```注解，详情见[通用Mapper插件文档说明](https://mapperhelper.github.io/docs/2.use/)
- 建议业务失败直接使用```ServiceException("message")```抛出，由统一异常处理器来封装业务失败的响应结果，比如```throw new ServiceException("该手机号已被注册")```，会直接被封装为```{"code":400,"message":"该手机号已被注册"}```返回，无需自己处理，尽情抛出
- 需要工具类的话建议先从```apache-commons-*```和```guava```中找，实在没有再造轮子或引入类库，尽量精简项目
- 开发规范建议遵循阿里巴巴Java开发手册（[最新版下载](https://github.com/alibaba/p3c))
- 建议在公司内部使用[ShowDoc](https://github.com/star7th/showdoc)、[SpringFox-Swagger2](https://github.com/springfox/springfox) 、[RAP](https://github.com/thx/RAP)等开源项目来编写、管理API文档
 
## 技术选型&文档
- Spring Boot（[查看Spring Boot学习&使用指南](http://www.jianshu.com/p/1a9fd8936bd8)）
- MyBatis（[查看官方中文文档](http://www.mybatis.org/mybatis-3/zh/index.html)）
- MyBatisb通用Mapper插件（[查看官方中文文档](https://mapperhelper.github.io/docs/)）
- MyBatis PageHelper分页插件（[查看官方中文文档](https://pagehelper.github.io/)）
- Druid Spring Boot Starter（[查看官方中文文档](https://github.com/alibaba/druid/tree/master/druid-spring-boot-starter/)）
- Fastjson（[查看官方中文文档](https://github.com/Alibaba/fastjson/wiki/%E9%A6%96%E9%A1%B5)）
- 其他略

## License
无，纯粹开源分享，感谢大家 [Star](https://github.com/lihengming/spring-boot-api-project-seed/stargazers) & [Fork](https://github.com/lihengming/spring-boot-api-project-seed/network/members) 的支持。
