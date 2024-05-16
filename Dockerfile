# 使用 Maven 镜像作为基础镜像
FROM maven:3.5.4 AS build

# 设置工作目录
WORKDIR /app

# 如果你想要设置 Maven 镜像源，你可以将 Maven 的 settings.xml 文件拷贝到容器中
COPY settings.xml /root/.m2/settings.xml

COPY pom.xml pom.xml

RUN mvn compile
#RUN mvn dependency:go-offline

# 将 Maven 项目的 pom.xml 和其他依赖项拷贝到容器中
COPY . .

RUN ls -alh

# 下载 Maven 项目的依赖项，但不执行构建
RUN mvn clean

# 使用 Maven 构建 Spring Boot 项目
RUN mvn package -DskipTests

RUN ls -alh

RUN pwd

# 使用 AdoptOpenJDK 镜像作为运行时环境
FROM openjdk:8-jdk-alpine AS runtime

# 设置工作目录
WORKDIR /app

# 解决openjdk镜像中缺少字体导致无法生成验证码的问题【https://blog.csdn.net/weixin_42389247/article/details/108083842】
RUN apk add --update font-adobe-100dpi ttf-dejavu fontconfig

# 安装wkhtmltopdf
RUN apk add --no-cache wkhtmltopdf

# 从构建阶段中复制构建好的 JAR 文件到运行时环境
COPY --from=build /app/target/*.jar app.jar

# 暴露 Spring Boot 应用程序的端口（如果需要）
EXPOSE 16666

# 定义容器启动命令
CMD ["java", "-jar", "app.jar"]
