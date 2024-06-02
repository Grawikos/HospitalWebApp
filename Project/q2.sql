select * from users

select * from user_roles

select user_id, patients.username, doctors.username from user_roles
full outer join patients on user_roles.user_id = patients.id
full outer join doctors on user_roles.user_id = doctors.id

select * from role

select * from visit

select * from patients

select * from doctors

insert into patients (id, username)
values (2, 'qwerty')


insert into visit (doctor_id, patient_id)
values (1, 2)

update user_roles
set role_id = 3
where user_id = 2

update users
set name = 'Anna', surname = 'Kowalska'
where id = 3

truncate patients cascade
drop table users_roles

INSERT INTO role (name) SELECT 'ROLE_USER' WHERE NOT EXISTS (SELECT * FROM role WHERE role.name='ROLE_USER');
INSERT INTO role (name) SELECT 'ROLE_DOCTOR' WHERE NOT EXISTS (SELECT * FROM role WHERE role.name='ROLE_DOCTOR');
INSERT INTO role (name) SELECT 'ROLE_ADMIN' WHERE NOT EXISTS (SELECT * FROM role WHERE role.name='ROLE_ADMIN');
