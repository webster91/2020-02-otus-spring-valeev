FROM maven:3.6.3-jdk-11

ENV PROJECT_DIR=/opt/project

RUN mkdir -p $PROJECT_DIR
WORKDIR $PROJECT_DIR

ADD ./pom.xml $PROJECT_DIR
RUN mvn dependency:resolve

ADD ./src/ $PROJECT_DIR/src
ADD ./package.json $PROJECT_DIR
ADD ./webpack.config.dev.js $PROJECT_DIR
ADD ./webpack.config.js $PROJECT_DIR
ADD ./.babelrc $PROJECT_DIR
RUN mvn install

FROM openjdk:11

ENV PROJECT_DIR=/opt/project
RUN mkdir -p $PROJECT_DIR
WORKDIR $PROJECT_DIR
COPY --from=0 $PROJECT_DIR/target/homework-18-1.0.jar $PROJECT_DIR/

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/opt/project/homework-18-1.0.jar"]
