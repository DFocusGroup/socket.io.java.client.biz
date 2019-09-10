#!/usr/bin/env bash

SHELL_FOLDER=$(dirname "$0")

cd $SHELL_FOLDER
cd ..

mvn clean install

rm -rf ${SHELL_FOLDER}/*.jar

mv target/socket.io.java.client.biz-1.0-SNAPSHOT.jar $SHELL_FOLDER
mv target/socket.io.java.client.biz-1.0-SNAPSHOT-sources.jar $SHELL_FOLDER

mvn install:install-file \
 -Dfile=${SHELL_FOLDER}/socket.io.java.client.biz-1.0-SNAPSHOT.jar \
 -Dsources=${SHELL_FOLDER}/socket.io.java.client.biz-1.0-SNAPSHOT-sources.jar \
 -DgroupId=com.dfocus \
 -DartifactId=socket.io.java.client.biz \
 -Dversion=1.0-SNAPSHOT \
 -Dpackaging=jar