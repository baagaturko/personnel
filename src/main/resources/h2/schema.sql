 
CREATE SEQUENCE GEN_A_ID
  MINVALUE 1
  MAXVALUE 9999999999999999
  START WITH 1
  INCREMENT BY 1
  CACHE 1;

create table A_DEPARTAMENT (
  departament_id number(10) primary key, 
  department_name varchar2(250) not null
)