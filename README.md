# Cloud-Api-Gateway

#### Description
1. API网关
2. 权限管理
3. 日志监控
4. Oauth2 鉴权管理

#### Software Architecture
Software architecture description

#### Develop Plan
1. 网关路由web端配置管理
2. 用户管理，权限管理
3. error页面设计

#### Installation

```jshelllanguage
$:  mvn clean package -DskipTests=true
```


#### Instructions

1. zuul边缘服务，默认支持consul注册中心；
2. 实现API网关动态路由功能。新增、修改、删除路由配置无需重启服务，通过API接口刷新立刻生效；
3. spring-security 权限控制。支持白名单动态配置（默认存储在数据库）, 账号管理，授权管理；
4. spring-securit-oauth 支持授权、鉴权。支持动态配置授权client_id和client_secret.

#### Contribution

1. Fork the repository
2. Create Feat_xxx branch
3. Commit your code
4. Create Pull Request