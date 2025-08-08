Create Database Foodie_India;
use Foodie_India;

drop database Foodie_India;
show tables;


delete  from user where user_id = 2;

update user_subscriptions set is_active = false where id = 3;
							
select * from user;
select * from user_subscriptions;		
select * from plan;
select * from plan_recepies;
select * from user_purchased_plans;
select * from subscription;
select * from recepie;
select * from resources;
select * from text;
select * from image;
select * from video;

delete from plan where planid = 305;


Update user set name = 'prathamesh patil' where user_id = 1;
Update user set name = 'sai patil' where user_id = 2;
Update user set name = 'manisha patil' where user_id = 52;
Update user set name = 'prince patil' where user_id = 102;


INSERT INTO user VALUES (5, 'superadmin@example.com', 'Male',  'User Super Admin',  'Secure Password',',9876543210', 'ROLE_SUPER_ADMIN');
-- Delete from user where user_id = 2;

-- desc resources;
-- desc video;
-- desc text;
-- desc image;
-- desc recepie;
-- desc user;
-- desc plan;
-- desc plan_recepies;
-- Admin
-- INSERT INTO user(user_id, email, gender, is_paid_admin, name, password, payment_date, payment_txn_id, phone, role) VALUES (4, 'admin4@example.com', 'Male', 1, 'Admin User', 'secureHashedPassword123', '2025-07-18 10:00:00', 'TXN123456789', '9876543210', 'ROLE_ADMIN');

-- Another Admin
-- INSERT INTO user(user_id, email, gender, is_paid_admin, name, password, payment_date, payment_txn_id, phone, role) VALUES (6, 'Anotheradmin4@example.com', 'Male', 1, 'Another Admin User', 'secureHashedPassword123', '2025-07-18 10:00:00', 'TXN123456789', '9876543210', 'ROLE_ADMIN');

-- User
-- INSERT INTO user(user_id, email, gender, is_paid_admin, name, password, payment_date, payment_txn_id, phone, role) VALUES (3, 'prince@example.com', 'Male', 0, 'User Prince', 'secureHashedPassword123', '2025-07-18 10:00:00', 'TXN123456789', '9876543210', 'ROLE_USER');

-- Super Admin
-- INSERT INTO user(user_id, email, gender, is_paid_admin, name, password, payment_date, payment_txn_id, phone, role) VALUES (5, 'superadmin@example.com', 'Male', 0, 'User Super Admin', 'secureHashedPassword123', '2025-07-18 10:00:00', 'TXN123456789', '9876543210', 'ROLE_SUPER_ADMIN');

