#!/bin/bash

# Exit on error
set -e
# arg count check
if [ -z "$2" ]
then
  echo "Usage: eb_deploy.sh <app_name> <deploy_environment>"
  exit 1
fi

app_name = $1
deploy_env = $2

# Init Beanstalk app and set environment
eb init $app_name --platform java --region us-east-1
eb use $deploy_env

# Generate new deployment file
rm -rf .elasticbeanstalk
cat >> .elasticbeanstalk/config.yml << _EOF
deploy:
    artifact: springboot/target/trading-1.0-SNAPSHOT.jar
_EOF

# Deploy trading app
eb deploy
exit 0
