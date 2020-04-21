#!/bin/bash

# This script builds the trading simulation app as a docker image
# then pushes it to a docker image repository. Run it from springboot/

# Exit on error
set -e

# 2 args should be passed: app name, and remote repo name
if [[ $# -ne "2" ]]
then
	echo "Usage scripts/k8s/k8s_build.sh <app_name> <repo_url>"
	exit 1
fi

app_name=$1
repo_url=$2

echo "Retrieving version info from Maven"
version=$(mvn help:evaluate -Dexpression=project.version | grep ^[[:digit:]])

echo "Building image ${app_name}:${version}"
docker build --no-cache --rm -t ${repo_url}/${app_name}:${version} . >/dev/null
echo "Pushing image to ${repo_url}"
docker push ${repo_url}/${app_name}:${version}

exit 0
