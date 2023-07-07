##  Quick start 

### Installation

Use docker to pull the images , refer to the order below 
```asciidoc
docker pull rabbitmq:3-management
```
and then start the image with docker command 
```asciidoc
 docker run -d --hostname myrabbitmq --name rabbitmq -e RABBITMQ_DEFAULT_USER=admin -e RABBITMQ_DEFAULT_PASS=admin -p 15672:15672 -p5672:5672 --log-opt max-size=10m --log-opt max-file=3 rabbitmq:3-management
```
when the rabbitmq container is ready , type the url ```localhost:15672``` to enter the rabbitmq console page ,then fill the blank with the user and password that we defined in docker command just before . Eventually , you' ll get in the rabbit mq console.

### Usage in Java

