# 使用官方的Java开发镜像作为基础镜像
FROM openjdk:17-jdk

# 将本地文件复制到容器的文件系统中
COPY ./target/CS304-0.0.1-SNAPSHOT.jar /usr/app/

# 设置工作目录
WORKDIR /usr/app

# 运行命令
RUN sh -c 'touch CS304-0.0.1-SNAPSHOT.jar'

# 设置启动命令
ENTRYPOINT ["java","-Dspring.profiles.active=docker","-jar","CS304-0.0.1-SNAPSHOT.jar"]