ALTER TABLE t_order add column c_status varchar not null;
ALTER TABLE t_order add column c_time_of_update timestamp;
ALTER TABLE t_order add column c_time_of_delivery timestamp;