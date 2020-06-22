FROM openjdk:7-jdk

ARG MAVEN_VERSION=3.3.9
ARG USER_HOME_DIR="/root"

# Install Maven in the correct version
RUN mkdir -p /usr/share/maven /usr/share/maven/ref \
  && curl -fsSL http://apache.osuosl.org/maven/maven-3/$MAVEN_VERSION/binaries/apache-maven-$MAVEN_VERSION-bin.tar.gz \
    | tar -xzC /usr/share/maven --strip-components=1  \
  && ln -s /usr/share/maven/bin/mvn  /usr/bin/mvn
ENV MAVEN_HOME /usr/share/maven
ENV MAVEN_CONFIG "$USER_HOME_DIR/.m2"

RUN apt-get update && apt-get -y install gettext-base && apt-get clean && rm -rf /var/lib/apt/lists/*

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

RUN cd /pack/openxds && mvn clean install -DskipTests

# Copy template files
RUN mkdir -p /pack/openxds-templates/
COPY compose/configuration/log4j.xml /pack/openxds-templates
COPY compose/configuration/omar.properties /pack/openxds-templates
COPY compose/configuration/openxds.properties /pack/openxds-templates
COPY compose/configuration/repository.jdbc.cfg.xml /pack/openxds-templates
COPY compose/configuration/XdsRepositoryConnections.xml /pack/openxds-templates

# Copy entrypoint
COPY compose/configuration/entrypoint.sh /pack/openxds

WORKDIR /pack/openxds/
ENTRYPOINT ["/pack/openxds/entrypoint.sh"]
