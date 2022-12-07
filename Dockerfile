ARG NSPBUILDER_TAG=2.1.0
ARG BASE_TAG=3.0.2
FROM registry.nspop.dk/tools/nspbuilder:${NSPBUILDER_TAG} AS bob

COPY apache-axis2 /pack/openxds/apache-axis2/
COPY common-utils /pack/openxds/common-utils/
COPY common-ws /pack/openxds/common-ws/
COPY iheos /pack/openxds/iheos/
COPY openxds-api /pack/openxds/openxds-api/
COPY openxds-config /pack/openxds/openxds-config/
COPY openxds-core /pack/openxds/openxds-core/
COPY openxds-log /pack/openxds/openxds-log/
COPY openxds-registry-adapter-omar /pack/openxds/openxds-registry-adapter-omar/
COPY openxds-registry-patient-lightweight /pack/openxds/openxds-registry-patient-lightweight/
COPY openxds-repository /pack/openxds/openxds-repository/
COPY openxds-web /pack/openxds/openxds-web/
COPY src /pack/openxds/src/
COPY build.xml /pack/openxds/
COPY build.properties /pack/openxds/
COPY pom.xml /pack/openxds/
COPY README.txt /pack/openxds/
COPY LICENSE.txt /pack/openxds/
COPY NOTICE.txt  /pack/openxds/

RUN mkdir /pack/openxds/build
RUN cd /pack/openxds && mvn clean install


#OpenXDS is not compatible with Java 8 and above because of Spring 2.5.6
#Hence, we cannot use the normal NSP base image
FROM openjdk:7u211-jdk-alpine3.9

# Only needed for envsubst in entrypoint?
RUN apk update
RUN apk add gettext

# Copy template files
RUN mkdir -p /pack/openxds-templates/
COPY etc/log4j.xml /pack/openxds-templates
COPY etc/omar.properties /pack/openxds-templates
COPY etc/openxds.properties /pack/openxds-templates
COPY etc/repository.jdbc.cfg.xml /pack/openxds-templates
COPY etc/XdsRepositoryConnections.xml /pack/openxds-templates

# Copy entrypoint and jar
RUN mkdir =p /pack/openxds
COPY entrypoint.sh /pack/openxds
RUN chmod +x /pack/openxds/entrypoint.sh
COPY --from=bob /pack/openxds/build/ /pack/openxds/

RUN echo "Europe/Copenhagen" > /etc/timezone

WORKDIR /pack/openxds/
ENTRYPOINT ["/pack/openxds/entrypoint.sh"]
