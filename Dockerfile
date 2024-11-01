FROM quay.io/wildfly/wildfly:latest-jdk17
WORKDIR /opt/jboss/wildfly
COPY target/Spotitube-1.0.war /opt/jboss/wildfly/standalone/deployments/ROOT.war
EXPOSE 8080
EXPOSE 9990
CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0"]