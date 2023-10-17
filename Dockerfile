FROM quay.io/wildfly/wildfly

EXPOSE 8080

COPY /target/warehouseAPI-1.0.war /opt/jboss/wildfly/standalone/deployments/

ENTRYPOINT ["top", "-b"]
