CREATE TABLE host_info (
	id		SERIAL PRIMARY KEY,
	hostname	VARCHAR not null,
	cpu_count	INTEGER not null,
	cpu_arch	VARCHAR not null,
	cpu_model	VARCHAR not null,
	cpu_mhz		INTEGER not null,
	l2_cache	INTEGER not null,
	timestamp	TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE host_usage (
	id		SERIAL REFERENCES host_info(id) ON DELETE CASCADE,
	memory_free	REAL not null,
	cpu_idle	REAL not null,
	cpu_kernel	REAL not null,
	disk_io		REAL not null,
	disk_avail	REAL not null,
	timestamp	TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
