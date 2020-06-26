#! /bin/bash

envsubst < /pack/openxds-templates/log4j.xml > /pack/openxds/build/log4j.xml
envsubst < /pack/openxds-templates/omar.properties > /pack/openxds/build/omar.properties
envsubst < /pack/openxds-templates/openxds.properties > /pack/openxds/build/openxds.properties
envsubst < /pack/openxds-templates/repository.jdbc.cfg.xml > /pack/openxds/build/repository.jdbc.cfg.xml
envsubst < /pack/openxds-templates/XdsRepositoryConnections.xml > /pack/openxds/build/conf/actors/XdsRepositoryConnections.xml

# start the application
cd /pack/openxds/build/
java $XARGS -jar openxds.jar
