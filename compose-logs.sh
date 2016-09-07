#!/bin/bash

D=$(dirname $BASH_SOURCE)

docker-compose -f $D/docker-es.yml -f $D/docker-rsyslog-kafka.yml  -f $D/docker-zookeeper.yml -f $D/docker-kafka-static-cluster.yml -f $D/docker-compose.yml $@
