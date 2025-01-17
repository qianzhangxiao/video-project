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

### 表设计 
- 用户表：vo_user
- 权限表：vo_role
- 用户权限表：vo_user_role
- 菜单表：vo_menu
- 权限菜单表：vo_role_menu
- 附件表：vo_file
- 字典分类表：vo_dic_type
- 字典值表：vo_dic_value
- 视频信息：vo_video_info
- 视频播放历史表：vo_video_history（保留7天数据，播放时长>3min计算）
- 点赞记录表：vo_video_like （个人只能点一次）
- 评论表：vo_video_comment
- 视频附件关联表：vo_video_file_rel
- 好友表：vo_friend
- 群组：vo_group
- 群组人员：vo_user_group
- 视频分享表：vo_video_share
- 聊天记录表：vo_chat

### 微服务模块
	- common-config：
		描述：用户登录鉴权配置（spring security+jwtToken）、常用工具类、基础表查询
	
	- common-seata：
		描述:分布式事务
	
	- video-gateway：
		描述：网关
		端口：9090

	- user-system：
		描述：用户注册、登录、用户信息维护等
		端口：9091
	
	- video-service：
		描述：视频管理服务
		端口：9092
	
	- email-service：
		描述：邮件服务，发送邮件
		端口：9095
	
	- socket-service：
		描述：websocket服务端，后续聊天服务使用
		端口：9098

