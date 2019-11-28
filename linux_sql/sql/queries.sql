/* SQL Queries for "SQL Queries" Implementation task
	1st - Group by CPU count, sort Memory size descending per group
	2nd - Average used memory over last 5 mins per host using windows
	3rd - Optional, find when node failed to write more than 3 times
*/

-- Question 1, If you order by CPU count first, they will be "grouped" without having to use GROUP BY
select 
	cpu_count, id, mem_total 
from 
	host_info
order by
	cpu_count, mem_total desc;

-- Question 2, what a mess
select
	host_usage.id,
	 host_info.hostname, 
	host_info.mem_total,
	(
		avg(
			(host_info.mem_total - host_usage.memory_free)/host_info.mem_total
		) * 100
	)::int as avg_mem_used
from 
	host_usage
	inner join host_info on host_usage.id = host_info.id
group by 
	host_usage.id, 
	host_info.hostname, 
	host_info.mem_total,
	date_trunc('hour', host_usage.curr_time) + interval '5 minute' * round(
		date_part('minute', host_usage.curr_time) / 5.0
	)
order by host_usage.id;

-- Question 3, for this question I use an expanded version of the host_usage table
-- This new table includes the "is_failed" column mentioned in the question
select
	host_usage_plus.id,
	min(host_usage_plus.curr_time) over( partition by host_usage_plus.id) as current_time,
	count(is_failed) over( partition by host_usage_plus.id) as failed_times
from
	host_usage_plus
where
	is_failed = 'failed'
order by
	host_usage_plus.id;
