#! /bin/bash

# Script to control the Docker'd PostgreSQL instance

command=$1
dbpass=$2
docker_arg_string="--name jrvs_psql -e POSTGRES_PASSWORD=$dbpass -d -v pgdata:/var/lib/postgresql/data -p 5432:5432 postgres"

systemctl status docker > /dev/null
docker_running=$?
docker_db_running=$(docker inspect --format='{{ .State.Running }}' jrvs_psql)

# Check for less than 1 or more than 2 arguments
# First argument should be start or stop, second should be the db password
if [[ $# -gt "2" ]] || [[ $# -lt "1"  ]]; then
	echo 'Incorrect number of arguments.
Please invoke with psql_docker.sh start|stop [db_password]'
>&2
	exit 1
fi

# Start Docker if the user wants to start the psql container and it's not already running
if [[ $docker_running -ne "0" ]]; then
	if [[ $command = "stop" ]]; then
		echo "Docker isn't running."
		exit 0
	fi
	if [[ $command = "start" ]]; then
		echo "Docker isn't running. Starting docker..."
		systemctl start docker
	fi
fi

#if user wants to start the instance and provided a password
if [[ $command = "start" ]] && [[ $# -eq "2" ]]; then

	#If docker doesn't have psql, pull it
	docker image ls | grep "^postgres" > /dev/null
	docker_has_psql=$?
	if [[ $docker_has_psql -ne "0" ]]; then
		echo "No Postgres image found. Pulling..."
		docker pull postgres > /dev/null
	fi

	#If the local volume doesn't exist, create it
	docker volume ls | grep "pgdata$" > /dev/null
	docker_has_volume=$?
	if [[ $docker_has_volume -ne "0" ]]; then
		echo "Docker volume pgdata not found. Creating..."
		docker volume create pgdata > /dev/null
	fi

	#find out Docker's psql container status
	if [[ $docker_db_running = "true" ]]; then
		echo 'The database is already docked!' >&2
		exit 2
	fi

	#if the container isn't created, docker run, else docker start
	docker container ls -a | grep "jrvs_psql$" > /dev/null
	docker_container_exists=$?
	if [[ $docker_container_exists -eq "0" ]]; then
		docker start jrvs_psql > /dev/null
		echo "Postgres container started"
		exit 0
	fi
	echo "Creating and starting new Postgres container..."
	docker run $docker_arg_string > /dev/null
	echo "Postgres container running"
	exit 0
fi

#if user wants to stop the instance
if [[ $command = 'stop' ]]; then
	#find out Docker's psql container status
	if [[ $docker_db_running = "false" ]]; then
		echo "Docker has already stopped the database container!" >&2
		exit 2
	fi
	docker stop jrvs_psql > /dev/null
	echo "Postgres container stopped"
	exit 0
fi

#If we made it here, we didn't recognize any commands
echo "Command $command not recognized.
Please use ./psql_docker.sh start|stop [password]"
exit 1
