DROP DATABASE IF EXISTS host_agent;

CREATE DATABASE host_agent;
\c host_agent;

CREATE TABLE host_info (
	id		SERIAL PRIMARY KEY,
	hostname	VARCHAR not null UNIQUE,
	cpu_count	INTEGER not null,
	cpu_arch	VARCHAR not null,
	cpu_model	VARCHAR not null,
	cpu_mhz		INTEGER not null,
	l2_cache	INTEGER not null,
	mem_total	INTEGER not null,
	curr_time	TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE host_usage (
	id		SERIAL UNIQUE REFERENCES host_info(id) ON DELETE CASCADE,
	memory_free	REAL not null,
	cpu_idle	REAL not null,
	cpu_kernel	REAL not null,
	disk_io		REAL not null,
	disk_avail	REAL not null,
	curr_time	TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
