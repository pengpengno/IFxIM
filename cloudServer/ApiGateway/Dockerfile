FROM openjdk:17.0.2-jdk
LABEL authors="王鹏"
MAINTAINER pengpeng_on@163.com

ENV SERVICE_NAME=ApiGateway
ENV SERVICE_VERSION=1.0
ENV JAR_NAME=${SERVICE_NAME}-${SERVICE_VERSION}.jar
ENV API_PORT=8761
EXPOSE ${API_PORT}
#ENV MYSQL_HOST=124.221.168.200:3306
#ENV MYSQL_USER_NAME=124.221.168.200:3306
#ENV MYSQL_USER_PASSWORD=124.221.168.200:3306
#ENV RABBITMQ_HOST=124.221.168.200:3306
#ENV RABBITMQ_PORT=5672
#ENV RABBITMQ_USER_NAME=admin
#ENV RABBITMQ_PASSWORD=admin

WORKDIR /app/service/${SERVICE_NAME}

#RUN echo "http://mirrors.aliyun.com/alpine/v3.8/main" > /etc/apk/repositories \
#    && echo "http://mirrors.aliyun.com/alpine/v3.8/community" >> /etc/apk/repositories \
#    && apk update upgrade \
#    && apk add --no-cache procps unzip curl bash tzdata \
#    && ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime \
#    && echo "Asia/Shanghai" > /etc/timezone \
#
COPY target/${JAR_NAME} ./${JAR_NAME}
#RUN ["mvn","clean","install","-DskipTests=true"]
ENTRYPOINT ["java", "-jar","${JAR_NAME}"]