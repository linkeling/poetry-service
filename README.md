# 诗词鉴赏系统服务端

营养包说明：
环境
local：本地开发环境，端口8085
dev：测试环境，端口8085

分支
develop:本地开发，测试环境
master:上线环境，只有发布线上环境才能推到master


本地环境：http://127.0.0.1:8085/docs.html

## 环境依赖
- 数据库   Mysql
- 运行依赖 JDK8+
- 编译依赖 MAVEN
- 插件依赖 Lombok

# 模块说明
poetry-admin：系统业务模块
poetry-base：系统公共类，配置类服务包
poetry-docs-ui：机遇swagger的接口文档配置


## API文档
   采用Swagger注解方式编写web层接口文档，非生产环境中启动项目后，可以通过`http://${address}:${port}/docs.html`的方式进行访问。
   swagger-ui基于2.6.0进行了部分改造，接口可在notes中使用数字编号来调整显示顺序。
