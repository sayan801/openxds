#! /bin/bash

cp -R /openxds-template/* /build/

envsubst < /openxds-template/template/log4j.xml > /build/log4j.xml
envsubst < /openxds-template/template/omar.properties > /build/omar.properties
envsubst < /openxds-template/template/openxds.properties > /build/openxds.properties
envsubst < /openxds-template/template/repository.jdbc.cfg.xml > /build/repository.jdbc.cfg.xml
envsubst < /openxds-template/template/XdsRepositoryConnections.xml > /build/conf/actors/XdsRepositoryConnections.xml

# start the application
echo "java $XARGS -jar openxds-1.0.1.jar"
java $XARGS -jar openxds-1.0.1.jar
