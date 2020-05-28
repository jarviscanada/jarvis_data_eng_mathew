# Springboot App setup with Kubernetes using minikube (for local cluster)

**These instructions assume you're on linux/CentOS7**

## Initial Preparation

[Download and install `kubectl`](https://kubernetes.io/docs/tasks/tools/install-kubectl/#install-kubectl-on-linux)

[Download and install `minikube`](https://kubernetes.io/docs/tasks/tools/install-minikube/)

Don't worry about the virtualization stuff, we're going to be using Docker.
	
Create your Kubernetes cluster using `minikube start --driver=Docker`. Minikube will pull and run an image in your Docker install.
- The container for your cluster will be named minikube, and it will have one node (for me it was named m01).
- Once the cluster is created, you can use `minikube status` to confirm everything is working.
- You can also check if kubectl picked up the new config with `kubectl config view` or `kubectl config current-context`
	
Tag your docker images for the Springboot API and Postgres database with a specific version number.
- `docker tag <old_image_name>:<old_version> <new_image_name>:<new_version>`
- This is technically not necessary, but it is best practice and will make updating easier.
	
Cache your images in minikube with `minikube cache add <image_name>:<version>`, check the cache with `minikube cache list`.

Point Docker at minikube's Docker installation with `eval $(minikube docker-env)`.
- You can see if this worked by checking `docker image ls`, there will be several kubernetes images listed.
- To change back to your own Docker, run `eval $(minikube docker-env -u)`.

Enable the ingress addon with `minikube addons enable ingress`. `ingress-dns` is not needed.

## Populating the Cluster

From here, there are a couple ways of doing things: 
- Using the CLI like `kubectl create <resource_type> <flags> <args>`
- Using YAML files that describe some part of the system, with `kubectl apply -f <filename>`, like `kubectl apply -f my_k8s_object.yaml`.
  - I used the YAML file approach to make things easier to write and troubleshoot.
	
You will need to create 5 objects: 
1. A deployment for the database
2. A service for the database 
3. A deployment for springboot 
4. A service for springboot 
5. An ingress for springboot

- A Deployment specifies how to construct your pods, how many replica pods should be maintained, their base name, labels, etc.
- A Pod is a grouping of one or more containers, kubernetes will make these for you once you apply your deployment files.
- A Service gathers multiple pods under a single (cluster-local) IP address, and load balances requests it recieves across them.
- An Ingress allows external hosts to access pods inside the cluster by mapping URLs to specific pods or services.
  - An Ingress Controller is required to use Ingress objects. The `ingress` addon should have added one for you.

### Creating the Database

The database objects should be created first as you will need the DNS name of the database service for Springboot's environment variables.

Create the following files (edit as needed) and apply them with `kubectl apply -f <filename>`.

*psql-deploy.yaml*
```
apiVersion: apps/v1
kind: Deployment
metadata:
  name: psql-test
spec:
  selector:
    matchLabels: 
      app: psql-test #Changes to this deployment template affects all pods with this label
  replicas: 1 
  template: #Every pod in this deployment will have the following
    metadata:
      labels: #Key-value pairs used by Selectors to get groups of pods
        app: psql-test
    spec:
      containers:
      - name: psql-test
        image: trading-psql:1.0 #The image to use for the container
        imagePullPolicy: Never #Only check local images
        ports:
        - containerPort: 5432
        env: #Environment variables for your Docker container
        - name: POSTGRES_USER
          value: postgres
        - name: POSTGRES_PASSWORD
          value: password
        - name: POSTGRES_DB
          value: jrvstrading
```

*psql-service.yaml*
```
apiVersion: v1
kind: Service
metadata:
  name: psql-service
  labels:
    app: psql-service
spec:
  type: NodePort #Binds the service to a random port on the Node
  ports:
  - port: 5432
    targetPort: 5432
    protocol: TCP
  selector:
    app: psql-test #Bind all pods with this label to this service
```

Check that the objects were created properly with `kubectl get pod` and `kubectl get service`.
- For more detailed output on a specific object, use `kubectl describe <object_type> <object_name>`.

To check if the database is working properly, run `kubectl get service psql-service` and check the second listed port, it should be somewhere in the 30000's
 - Connect to the DB with `psql -h $(minikube ip) -p <NodePort> -U postgres jrvstrading` and check that the tables were created.

### Creating the Springboot API Server

Once the database is running properly, create the following files (edit as needed) and apply them as before.

*springboot-deploy.yaml*
```
apiVersion: apps/v1
kind: Deployment
metadata:
  name: springboot-test
spec:
  selector:
    matchLabels:
      app: springboot-test #Pods with this label are affected by changes to this deployment
  replicas: 3 #We're making 3 copies to test the service
  template:
    metadata:
      labels:
        app: springboot-test #Put app name here, should match above.
    spec:
      containers:
      - name: springboot-test #Name of the container, should be unique among containers in deployment
        image: trading-app:1.0 #Docker image to use. Tag a specific version.
        imagePullPolicy: Never
        env: #Environment variables for your docker container, edit to match yours
        - name: IEX_TOKEN
          value: pk_00162a2462f64e5e93e5214c1cefff56
        - name: PG_USERNAME
          value: postgres
        - name: PG_PASSWORD
          value: password
        - name: PG_URL
          value: jdbc:postgresql://psql-service:5432/jrvstrading #The URL should match the name of the psql service.
        ports:
        - containerPort: 8080 #Port number used by the container
```

*springboot-service.yaml*
```
apiVersion: v1
kind: Service
metadata:
  name: springboot-service
  labels:
    app: springboot-service
spec:
  type: NodePort
  ports:
  - port: 8080
    targetPort: 8080
    protocol: TCP
  selector:
    app: springboot-test
```
Once these two files are applied, you should see **three** new pods have been created, with the same prefix.

You can now use `minikube service springboot-service` to open your browser to the cluster's IP and that service's NodePort.
- You will most likely immediately 404 because we never set up a default page in the Springboot project. 
- If a web browser is not available or you'd prefer using `curl`, the cluster's IP can be gotten from `minikube ip`, and the port to use is the second port number listed in `kubectl get service springboot-service`.
  
## Adding an Ingress
  
To make accessing the Springboot API more convinient, we can add an Ingress object to the cluster.
The Ingress object allows the Ingress Controller (NGINX in this case) to forward our requests to a service in the cluster.

**If you didn't enable minikube's ingress addon yet, do that now.** `minikube addons enable ingress`

Create and apply the following file (edit as needed):

*springboot-ingress.yaml*
```
apiVersion: networking.k8s.io/v1beta1 # for versions before 1.14 use extensions/v1beta1
kind: Ingress
metadata:
  name: springboot-ingress
spec:
  rules:
  - host: trading.jrvs
    http:
      paths:
      - path: /
        backend:
          serviceName: springboot-service
          servicePort: 8080

```

This will cause any http requests for `trading.jrvs` to be forwarded to the Springboot service on port 8080.
- In order to actually use this, you will need to add your cluster IP (from `minikube ip`) and the domain to `/etc/hosts`.
- The line should look similar to `172.17.0.2 trading.jrvs`. Do not change any other lines.
  
Now you should be able to access the Springboot API in your browser at `http://trading.jrvs/swagger-ui.html`.

If you have many services and/or want to take things a step further, you can enable the addon `ingress-dns`, which gives you a full dns resolver to access your cluster with.
- You can find [setup instructions for `ingress-dns` here](https://github.com/kubernetes/minikube/tree/master/deploy/addons/ingress-dns)
