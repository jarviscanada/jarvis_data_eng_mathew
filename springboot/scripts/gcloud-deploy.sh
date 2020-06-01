#!/bin/bash
if [[ $# -ne 1 ]]
then
        echo "usage: springboot-deploy.sh <version-number>"
        exit 1
fi
version=$1
ssh -i /tmp/gcloud 10.142.0.4 << _SSH_
docker version >/dev/null
if [[ $? -ne 0 ]]
then
        echo "Docker is not running"
        exit 1
fi
mkdir /tmp/trading-app-deploy
cd /tmp/trading-app-deploy
wget -q --user=jenkins --password=centos1234 \
http://localhost:8081/artifactory/trading-app-repo/ca/jrvs/apps/trading/${version}/trading-${version}.jar
cat > Dockerfile << _EOF
FROM openjdk:8-alpine
COPY trading-${version}.jar /usr/local/app/trading/lib/trading_app.jar
ENTRYPOINT ["java", "-jar", "/usr/local/app/trading/lib/trading_app.jar"]
_EOF
docker build -t trading-app:${version} .
docker ps -a |grep trading-app
if [[ $? -eq 0 ]]
then
        echo "old container detected, removing..."
        docker stop trading-app
        docker rm -f trading-app
fi

docker run -d -p 10101:8080 -e IEX_TOKEN="pk_00162a2462f64e5e93e5214c1cefff56" -e PG_USERNAME="postgres" \
-e PG_PASSWORD="password" -e PG_URL="jdbc:postgresql://trading-psql:5432/jrvstrading" \
--network trading_net --name trading-app trading-app:latest

cd /tmp
rm -r /tmp/trading-app-deploy

_SSH_

exit
