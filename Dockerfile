FROM quay.io/wildfly/wildfly

COPY warehouseAPI-1.0.war /opt/jboss/wildfly/standalone/deployments/

ENTRYPOINT ["top", "-b"]