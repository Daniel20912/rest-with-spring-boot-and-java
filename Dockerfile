FROM alpine:3.21
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
# comando que vai ser executado assim que o container inicializar
ENTRYPOINT ["java","-jar","/app.jar"]