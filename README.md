# joker.logs

### build images

gradle clean build && gradle :democustomerinfo:dBI && gradle :demotransactionslist:dBI && gradle :demodashboardapi:dBI

### start demo

./compose-logs.sh up -d

### call DashboardAPI

curl -X GET "http://\<Your DOCKER-MACHINE IP \>:8070/getInfo"
