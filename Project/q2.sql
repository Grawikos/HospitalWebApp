select * from users

select * from user_roles

select users.id, users.username, role.name as role from users
join user_roles on user_roles.user_id = users.id
join role on user_roles.role_id = role.id

select * from role

select * from visit

select * from patients

select * from doctors


update user_roles
set role_id = 3
where user_id = 2

update users
set name = 'Anna', surname = 'Kowalska'
where id = 3

update doctors
set speciality = 'Surgeon'
where id = 3

truncate patients cascade
drop table users_roles

INSERT INTO role (name) SELECT 'ROLE_USER' WHERE NOT EXISTS (SELECT * FROM role WHERE role.name='ROLE_USER');
INSERT INTO role (name) SELECT 'ROLE_DOCTOR' WHERE NOT EXISTS (SELECT * FROM role WHERE role.name='ROLE_DOCTOR');
INSERT INTO role (name) SELECT 'ROLE_ADMIN' WHERE NOT EXISTS (SELECT * FROM role WHERE role.name='ROLE_ADMIN');
