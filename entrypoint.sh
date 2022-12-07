#!/bin/sh


envsubst < /pack/openxds-templates/log4j.xml > /pack/openxds/log4j.xml
envsubst < /pack/openxds-templates/omar.properties > /pack/openxds/omar.properties
envsubst < /pack/openxds-templates/openxds.properties > /pack/openxds/openxds.properties
envsubst < /pack/openxds-templates/repository.jdbc.cfg.xml > /pack/openxds/repository.jdbc.cfg.xml
mkdir -p /pack/openxds/conf/actors
envsubst < /pack/openxds-templates/XdsRepositoryConnections.xml > /pack/openxds/conf/actors/XdsRepositoryConnections.xml

# start the application
java $XARGS -jar openxds.jar
