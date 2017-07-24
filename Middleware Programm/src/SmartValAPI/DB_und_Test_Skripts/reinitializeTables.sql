-- deletes all rows from existing tables for reimport.
use smart_meter;
delete from meter_data;
delete from meter_management;
delete from meter_type;
delete from meter_protocol;
delete from meter_manufactor;
delete from log_events;
delete from log_types;
delete from customer;