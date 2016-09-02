#!/bin/bash

if [[ -n $KAFKA_HOSTS ]]; then
    declare -i i=0
    kafka_hosts=""
    IFS=','; for khost in $KAFKA_HOSTS; do
        echo "add kafka host to log forwarding: $khost"
        kafka_hosts="$kafka_hosts,\"$khost\""
    done
    kafka_hosts=${kafka_hosts:1}
    sed -e "s/\${kafka_hosts}/$kafka_hosts/" $1 > $2
fi