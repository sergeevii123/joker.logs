#!/bin/bash

if [[ -n $RSYSLOG_HOSTS ]]; then
    declare -i i=0
    primary=""
    secondaries=""
    IFS=','; for rhost in $RSYSLOG_HOSTS; do
        echo "add rsyslog host to log forwarding: $rhost"
        if [ $i = 0 ]; then
            primary='@@'$rhost
        else
            secondaries=$secondaries'\&@@'$rhost'\'$'\n'
        fi
        i+=1
    done
    sed -e "s/\${primaryHost}/$primary/" -e "s/\${secondaryHost}/$secondaries/" $1 > $2
fi