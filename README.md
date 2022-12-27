# video-project

#### 介绍
视频前台项目：

	1、注册模块可以自填纯数字靓号
	2、可以使用账号密码、邮箱登录
	3、个人可查看个人保存的视频（正在实现）
	4、可以添加好友，分享某一分类下视频给好友，分享数添加秘钥，秘钥正确方可查看分享的视频（正在实现）
	5、好友可在线聊天（正在实现）
	6、可以分享视频至公共视频池，公共视频池所有人可以观看（正在实现）

#### 软件架构
	springcloud：
		- spring cloud 2021.0.5
		- spring cloud alibaba 2021.0.4.0
		- seata 2021.0.4.0（客户端）
		- seata-serializer-kryo 1.5.2（客户端）
		- nacos：2021.0.4.0（客户端）
		- sentinel：2021.0.4.0（客户端）
		- seata 1.5.2（服务端）
		- nacos-server：2.1.1（服务端）
	mysql
	mybatis-plus
	redis
	rabbitMq
	websocket

### 微服务模块
	- common-config：
		描述：用户登录鉴权配置、常用工具类、基础表查询
	
	- common-seata：
		描述:分布式事务
	
	- video-gateway：
		描述：网关
		端口：9091

	- user-system：
		描述：用户注册、登录、用户信息维护等
		端口：9090
	
	- video-service：
		描述：视频管理服务
		端口：9092
	
	- email-service：
		描述：邮件服务，发送邮件
		端口：9095
	
	- socket-service：
		描述：websocket服务端，后续聊天服务使用
		端口：9098

