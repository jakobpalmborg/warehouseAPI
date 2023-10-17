FROM bitnami/wildfly:29.0.1
COPY target/WarehouseApi-1.0.war /app
ENV WILDFLY_USERNAME=user, WILDFLY_PASSWORD=password


#FROM quay.io/wildfly/wildfly

#EXPOSE 8080

#COPY /target/warehouseAPI-1.0.war /opt/jboss/wildfly/standalone/deployments/

#ENTRYPOINT ["top", "-b"]
