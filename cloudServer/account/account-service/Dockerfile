FROM openjdk:17.0.2-jdk
LABEL authors="王鹏"
MAINTAINER pengpeng_on@163.com

ENV SERVICE_NAME=account-service
ENV SERVICE_VERSION=1.0
ENV JAR_NAME=${SERVICE_NAME}-${SERVICE_VERSION}.jar
ENV SERVICE_PORT=8001
#ENV RD2BC_URL="r2dbc:mysql://fx-mysql:3306/ifx?useUnicode=true&characterEncoding=utf-8&txcAppName=accout-service&useSSL=true"

EXPOSE ${SERVICE_PORT}

WORKDIR /app/service/${SERVICE_NAME}

#RUN echo "http://mirrors.aliyun.com/alpine/v3.8/main" > /etc/apk/repositories \
#    && echo "http://mirrors.aliyun.com/alpine/v3.8/community" >> /etc/apk/repositories \
#    && apk update upgrade \
#    && apk add --no-cache procps unzip curl bash tzdata \
#    && ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime \
#    && echo "Asia/Shanghai" > /etc/timezone \
#
COPY target/${JAR_NAME} ./account-service.jar
COPY src/main/resources/application-docker.yaml .


#RUN ["mvn","clean","install","-DskipTests=true"]
ENTRYPOINT ["java", "-jar","account-service.jar" ,"--spring.config.location=application-docker.yaml"]