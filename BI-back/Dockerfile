FROM maven:3.8.1-jdk-8-slim as builder

# 将本地代码复制到容器镜像中。
WORKDIR /app
COPY pom.xml .
COPY src ./src

# 构建发布 artifact。
RUN mvn package -DskipTests

# 第二阶段：创建一个最小的运行时镜像
FROM openjdk:8-jre-slim

# 设置工作目录
WORKDIR /app

# 从构建阶段复制JAR文件
COPY --from=builder /app/target/BI-back-0.0.1-SNAPSHOT.jar ./app.jar

# 安装Redis和RabbitMQ客户端库（假设你的应用程序使用Spring Data Redis和Spring AMQP）
RUN apt-get update && \
    apt-get install -y redis-tools && \
    rm -rf /var/lib/apt/lists/*

# 暴露端口（根据你的应用程序的需要调整这些端口）
EXPOSE 8080 6379 5672

# 指定容器启动时运行的默认命令。
CMD ["java", "-jar", "./app.jar", "--spring.profiles.active=prod"]

# 如果需要，可以添加用于Redis和RabbitMQ配置的环境变量
# ENV SPRING_REDIS_HOST=redis-host
# ENV SPRING_REDIS_PORT=6379
# ENV SPRING_RABBITMQ_HOST=rabbitmq-host
# ENV SPRING_RABBITMQ_PORT=5672
