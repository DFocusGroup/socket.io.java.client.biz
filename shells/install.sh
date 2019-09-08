#!/usr/bin/env bash

SHELL_FOLDER=$(dirname "$0")

mvn install:install-file -Dfile=${SHELL_FOLDER}/socket.io.java.client.biz-1.0-SNAPSHOT.jar -DgroupId=com.dfocus -DartifactId=socket.io.java.client.biz -Dversion=1.0-SNAPSHOT -Dpackaging=jar