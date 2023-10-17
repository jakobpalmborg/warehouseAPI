FROM quay.io/wildfly/wildfly

EXPOSE 8080

RUN mkdir target

ARG WAR_FILE=./target/*.war

COPY ${WAR_FILE} /opt/jboss/wildfly/standalone/deployments/

ENTRYPOINT ["top", "-b"]
