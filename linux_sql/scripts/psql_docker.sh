#! /bin/bash

# Script to control the Docker'd PostgreSQL instance

command=$1
dbpass=$2
docker inspect --format='{{ .State.Running }}' jrvs_psql >/dev/null #supress output
docker_db_running=$?

# Check for less than 1 or more than 2 arguments
# First argument should be start or stop, second should be the db password
if [[ $# -gt "2" ]] || [[ $# -lt "1"  ]]; then
	echo 'Incorrect number of arguments.
Please invoke with psql_docker.sh start|stop [db_password]'
>&2
	exit 1
fi

#if user wants to stop the instance
if [[ $command = 'stop' ]]; then
	#find out Docker's psql container status
	if [[ $docker_db_running -eq "1" ]]; then
		echo "Docker has already stopped the database!" >&2
		exit 2
	fi
	docker stop jrvs_psql
	exit 0
fi

#if user wants to start the instance and provided a password
if [[ $command = "start" ]] && [[ $# -eq "2" ]]; then
	# find out Docker's psql container status
	if [[ $docker_db_running -eq '0' ]]; then
		echo 'The database is already docked!' >&2
		exit 2
	fi
	docker run --rm --name jrvs_psql -e POSTGRES_PASSWORD=$dbpass -d -v pgdata:/var/lib/postgresql/data -p 5432:5432 postgres
	exit 0
fi

#If we made it here, we didn't recognize any commands
echo "Command $command not recognized.
Please use ./psql_docker.sh start|stop [password]"
exit 1
