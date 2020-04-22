#!/bin/bash

# Deployment script for pushing the Jarvis Trading Simulation to a k8s cluster
# Run this script from the project root (springboot/)

# Get image repo and base image name from args, error out if there isn't two args
if [ $# -ne "3" ]
then
	echo "Usage: scripts/k8s/k8s_deploy.sh <app_name> <image_repo> <rds_url>"
	exit 1
fi

# Check if kubectl is configured
kubectl config current-context
k8s_configured=$?
if [ $k8s_configured -ne "0" ]
then
	echo "kubectl is not configured, attempting to create config"
	aws --region us-east-1 eks update-kubeconfig --name mathew-k8s
	kubectl config current-context
	if [ $? -ne "0" ]
	then
		echo "kubectl still not configured, aborting"
		exit 5
	fi
fi

# Get app name, image repo, RDS url from cli
app_name=$1
image_repo=$2
rds_url=$3

# Get app version number from Maven
echo "Retrieving version info from Maven"
version=$(mvn help:evaluate -Dexpression=project.version | grep ^[[:digit:]])

# Insert the app name and the image version
echo "Creating new k8s deployment file"
sed -e "s/app-name/${app_name}/" \
 -e "s;img-ver;${image_repo}/${app_name}:${version};" \
 -e "s/psql-server/${rds_url}/" \
 scripts/k8s/app-deploy-template.yaml > scripts/k8s/${app_name}-deploy-${version}.yaml

kubectl apply -f scripts/k8s/${app_name}-deploy-${version}.yaml


# Check if the service and ingress objects exist. If not, create them.
echo "Checking if app service exists"
kubectl get service {app_name}-service &>/dev/null
svc_exists=$?
if [[ $svc_exists -ne "0" ]]
then
	echo "Creating new app service"
	sed "s/app-name/${app_name}/" scripts/k8s/app-service-template.yaml > scripts/k8s/${app_name}-service.yaml
	kubectl apply -f scripts/k8s/${app_name}-service.yaml
fi

echo "Checking if Ingress policy exists"
kubectl get ingress ${app_name}-ingress &>/dev/null
ingress_exists=$?
if [[ $ingress_exists -ne "0" ]]
then
	echo "Creating Ingress policy"
	sed "s/app-name/${app_name}/" scripts/k8s/app-ingress-template.yaml > scripts/k8s/${app_name}-ingress.yaml
	kubectl apply -f scripts/k8s/${app_name}-ingress.yaml
fi

exit 0
