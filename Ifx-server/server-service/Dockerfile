FROM openjdk:17.0.2-jdk
LABEL authors="王鹏"
MAINTAINER pengpeng_on@163.com

ENV SERVICE_NAME=server-service
ENV SERVICE_VERSION=1.0
ENV JAR_NAME=${SERVICE_NAME}-${SERVICE_VERSION}.jar
ENV SERVER_PORT=8094
EXPOSE ${SERVER_PORT}

WORKDIR /app/service/${SERVICE_NAME}

#RUN echo "http://mirrors.aliyun.com/alpine/v3.8/main" > /etc/apk/repositories \
#    && echo "http://mirrors.aliyun.com/alpine/v3.8/community" >> /etc/apk/repositories \
#    && apk update upgrade \
#    && apk add --no-cache procps unzip curl bash tzdata \
#    && ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime \
#    && echo "Asia/Shanghai" > /etc/timezone \
#
COPY target/${JAR_NAME} ./app.jar
#RUN ["mvn","clean","install","-DskipTests=true"]
ENTRYPOINT ["java", "-jar","app.jar"]
