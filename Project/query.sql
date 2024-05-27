select username, role.name role from users 
join users_roles on users.id = users_roles.user_id
join role on users_roles.roles_id = role.id

select * from users_roles


