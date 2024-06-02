-- to insert only if table is empty
INSERT INTO role (name) SELECT 'ROLE_USER' WHERE NOT EXISTS (SELECT * FROM role WHERE role.name='ROLE_USER');
INSERT INTO role (name) SELECT 'ROLE_DOCTOR' WHERE NOT EXISTS (SELECT * FROM role WHERE role.name='ROLE_DOCTOR');
INSERT INTO role (name) SELECT 'ROLE_ADMIN' WHERE NOT EXISTS (SELECT * FROM role WHERE role.name='ROLE_ADMIN');

-- FROM role;
--INSERT INTO role (name) VALUES ('ROLE_ADMIN');
--INSERT INTO role (name) VALUES ('ROLE_USER');
