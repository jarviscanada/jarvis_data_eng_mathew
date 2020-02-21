# Hadoop & Hive Introduction Project

## Table of Contents
* [Introduction](#introduction)
* [Hadoop Cluster](#hadoop-cluster)
* [The Hive Project](#the-hive-project)
* [Improvements](#improvements)

## Introduction

This project serves as an introduction to big data processing through the use of distributed
computing, facilitated by a suite of tools including Hadoop, HDFS, MapReduce, Yarn, Hive, and 
Zeppelin Notebook.

In Addition, Google BigQuery and Storage were used to collect and store the data used for the 
project. Specifically, we used the publicly available data-set 
`patents-public-data/worldbank_wdi/wdi_2016`.

Hadoop is a distributed computing framework that uses a virtual filesystem called the Hadoop 
Distributed File System, or HDFS, to store data with high availability and integrity. Hadoop also
provides the ability to use the cluster to perform distributed computations on the data stored in 
HDFS, traditionally through the MapReduce workflow, but other workflows have been created over time.

Yarn, while not specially configured or interacted with directly, allowed Hive to run its queries
through TEZ, without Hive having to worry about allocating cluster resources directly. Yarn manages
resource allocation across the cluster, job queues, and job container management. TEZ is an
expansion of the MapReduce workflow which executes jobs represented by Directed Acyclic Graphs.

Hive is a special form of database built specifically to work with distributed file systems like
HDFS. Instead of storing all of its data in specialized, vendor-specific files, Hive works with
files as-is, using either its' warehouse directory or external files as data sources for its tables.
Hive's tables only enforce their schema when data is loaded from them, using a Serializer-Deserializer,
or SerDe.

Zeppelin Notebook is a service which provides a web-interface for writing distributed jobs using
various languages, as long as there is an interpreter available to run it with. For this project,
Zeppelin was used to run Hive queries on the cluster and render Markdown documentation of the
various tasks which were performed over the course of the project.

## Hadoop Cluster

The Hadoop cluster used for this project is a 3 node cluster, 1 master and 2 workers, which is
provisioned and hosted by the Google Cloud Platform. The Master node hosts the various tools and
services which were used in this project, while the worker nodes store our data and process jobs.
All three nodes in the cluster have 2 vCPUs, 12 GB of RAM, and 100 GB of storage.

![Hadoop Cluster Diagram](assets/hadoop_cluster_diagram.png)

### Tools used

* HDFS
  * A distributed virtual file system, which is interacted with via Hadoop. HDFS consists of one
  NameNode, which contains HDFS' metadata and facilitates HDFS operations, and multiple DataNodes,
  which each locally store blocks of data. The NameNode's metadata consists of information related
  to the replication status and location of file blocks.
   
  * HDFS focuses on data availability and integrity, and uses block replication to achieve these. 
  HDFS automatically replicates data until by default 3 copies of that data exist in the system. If 
  any of those replicas should become corrupted, the other replicas are used to restore it. If a
  replica were removed, then a new replica is made.
  
* Hadoop
  * Hadoop manages HDFS and provides an interface to perform distributed processing on the cluster
  using specialized scripts broadly referred to as jobs. Traditionally, MapReduce jobs are used, but
  other job types have been introduced to Hadoop such as TEZ, which represents jobs with Directed
  Acyclic Graphs, rather than MapReduce's three phase workflow.
  
* MapReduce
  * MapReduce is a distributed computing workflow that consists of three phases: The Mapping phase,
  The Shuffle phase, and the Reduce phase. A MapReduce job typically consists of one or more Mappers
  and one or more Reducers, though there are typically less Reducers than Mappers.
  
  * The mapping phase consists of the Mappers taking some section of the data set and transforming
  it in some way, then writing it out to temporary files. The Shuffle phase is used to pass data from
  the Mappers to the Reducers. Some jobs may specify how the Mappers should distribute their data.
  
  * The Reduce phase involves filtering, combining, and otherwise aggregating the data received by the 
  Mappers in order to generate some useful output.
  
* Yarn
  * Yarn acts as an intermediary between Hadoop and other third party applications. Yarn consists of
  two major components, the Resource Manager and the Application Manager. Yarn maintains information
  on the nodes in the cluster through the use of a "heartbeat" message periodically sent by each node
  that contains information on the node's resource usage and container status.
  
  * The Resource Manager tracks the resource availability on every node in the cluster, and uses this
  information to schedule jobs and allocate resources on the cluster. The resource manager features
  configurable queues which control resource allocation, FIFO queues where the first job in gets all
  the resources it needs, Capacity queues which can stretch beyond their configured allocation, and
  Fair share queues which divide resources equally across all jobs in the queue.
  
  * The Application Manager handles creating containers in which to run jobs, tracking their progress,
  and assembling the job's output for the user. The Application Manager may also be configured to kill
  and/or re-run containers if a node experiences an unexpected failure or takes too long to run.
  
* Hive
  * Hive is a special kind of RDBMS that is designed to work in distributed ecosystems, by working
  directly with files instead of storing all of its data in vendor-specific files. Hive reads data
  files through the use of scripts called Serializer-Deserializers, or SerDes, which format data in a
  specific way both when reading and writing data. Hive performs queries by converting them into
  MapReduce or TEZ jobs, that are executed by Yarn.
  
  * Hive features two types of tables, managed tables, and external tables. Managed tables have their
  data files stored in Hive's configured warehouse directory, while external tables could be anywhere.
  Some features are not available on external tables, such as "Create Table As Select", but they can
  be safer to work with, because, unlike managed tables, dropping an external table will not result 
  in data loss.
  
  * Hive also features a Metastore, which contains database, namespace, and schema information for
  its tables. Since Hive's tables could be all over the distributed file system, storing schema
  information with the tables themselves will result in huge query overhead. The Metastore is stored
  in a "standard" RDBMS. 
  
* Zeppelin Notebook
  * Zeppelin Notebook offers a web interface for using various languages to submit jobs to the Hadoop
  cluster. Any language may be used as long as Zeppelin has a configured interpreter for it. Users
  can configure their own interpreter to use, provided there is a base component available to work
  with, and any needed parameters and dependencies are provided.
  
  * Zeppelin Notebook is organized into pages and paragraphs. Each page can contain many paragraphs,
  each of which contains some code. The language used and paragraph limitations are dependent on the
  interpreter being used. For our Hive interpreter, we were only capable of issuing one query per
  paragraph. Each paragraph on a page may be executed individually, or in sequence, based on the
  order they are placed on the page.
  
## The Hive Project

The Hive project served as an introduction to how Hive and Zeppelin Notebook work. Some work was done
using Hive's CLI client, Beeline, but REPL style programs can be frustrating to work with, and you
cannot save your queries easily. The project itself involved demonstrating various features of Hive,
such as reading data from files, copying data between tables, using SerDes, partitioning, and 
alternate file formats. The data-set used for this project was World Development Index data from 
1960 to 2016.
 
The Zeppelin Notebook page for this project is below.
![Zeppelin Notebook Project Page](assets/mathew-zeppelin-notebook-pagecap.png)

## Improvements

Some potential improvements for this project are as follows:
* Explore bucketing, possibly with a larger cluster.
* Compare combining Parquet files with partitioning to the individual methods.
* Test sub-partitioning.
* Compare Parquet with other columnar file formats.