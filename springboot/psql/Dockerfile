FROM postgres:9.6-alpine

#Dockerfile for Jarvis Trading App SQL server
#Copy DB init files to PSQL init folder
COPY *.sql /docker-entrypoint-initdb.d/

EXPOSE 5432/tcp

