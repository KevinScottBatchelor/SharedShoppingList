
DROP TABLE IF EXISTS users, accounts, items, lists, groups, account_groups, lists_in_group, invite_status CASCADE;

CREATE TABLE users (
	user_id SERIAL,
	username varchar(50) NOT NULL UNIQUE,
	password_hash varchar(200) NOT NULL,
	role varchar(50) NOT NULL,
	CONSTRAINT PK_user PRIMARY KEY (user_id)
);

CREATE TABLE accounts (
        account_id SERIAL,
        user_id int NOT NULL,
        
        CONSTRAINT PK_account_id PRIMARY KEY (account_id),
        CONSTRAINT FK_user_id FOREIGN KEY (user_id) REFERENCES users(user_id)

);

CREATE TABLE lists (
        list_id SERIAL,
        list_name varchar(25) NOT NULL UNIQUE,
        account_id int NOT NULL,
        claimed_by varchar(50), 
        
        CONSTRAINT PK_list_id PRIMARY KEY (list_id),
        CONSTRAINT FK_account_id FOREIGN KEY (account_id) REFERENCES accounts(account_id)   
);

CREATE TABLE items (
        item_id SERIAL,
        list_id int NOT NULL,
        item_name varchar(25) NOT NULL,
        quantity int NOT NULL,
        date_added date NOT NULL,
        created_by varchar(50),
        date_modified date,
        modified_by varchar(50),
        
        CONSTRAINT PK_item_id PRIMARY KEY (item_id),
        CONSTRAINT FK_list_id FOREIGN KEY (list_id) REFERENCES lists(list_id)
        
);

CREATE TABLE groups (
        group_id SERIAL,
        group_name varchar(25) NOT NULL UNIQUE,
            
        CONSTRAINT PK_group_id PRIMARY KEY (group_id)         
);

CREATE TABLE lists_in_group (
        group_id int NOT NULL,
        list_id int NOT NULL,
        
        CONSTRAINT PK_lists_in_group PRIMARY KEY(group_id, list_id),
        CONSTRAINT FK_group_id FOREIGN KEY (group_id) REFERENCES groups(group_id),
        CONSTRAINT FK_list_id FOREIGN KEY (list_id) REFERENCES lists(list_id)
);

CREATE TABLE account_groups (
        group_id int NOT NULL,
        member_of_group_id int NOT NULL,
        is_creator boolean DEFAULT false,
        joined_date date NOT NULL,
        
        CONSTRAINT PK_account_groups PRIMARY KEY (group_id, member_of_group_id),
        CONSTRAINT FK_group_id FOREIGN KEY (group_id) REFERENCES groups(group_id),
        CONSTRAINT FK_member_of_group_id FOREIGN KEY (member_of_group_id) REFERENCES accounts(account_id) 
         
);

CREATE TABLE invite_status (
        invite_id SERIAL,
        invite_code varchar(6) NOT NULL,
        is_accepted boolean DEFAULT false,
        invited_user int,
        from_user int,
        group_id int,
        
        CONSTRAINT PK_invite_id PRIMARY KEY (invite_id),
        CONSTRAINT FK_invite_user FOREIGN KEY (invited_user) REFERENCES accounts(account_id)
        
        
);

INSERT INTO users (username,password_hash,role) VALUES ('user','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_USER');
INSERT INTO users (username,password_hash,role) VALUES ('admin','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_ADMIN');
INSERT INTO users (username,password_hash,role) VALUES ('final_capstone_appuser','$2a$10$bja7tGgdx9dTNc3InYSCb.qretLPaHYZQ4dK8rnAYW.vXk9IHtdq6','ROLE_USER');
INSERT INTO users (username,password_hash,role) VALUES ('final_capstone_owner','$2a$10$FGCMnOlpJjfxz5dT8DOGkOnj4Tyd2m/XC3LZ5v2efSySGEp7jW16m','ROLE_ADMIN');

INSERT INTO accounts(user_id)
VALUES (1);

INSERT INTO accounts(user_id)
VALUES (2);

INSERT INTO accounts(user_id)
VALUES (3);

INSERT INTO accounts(user_id)
VALUES (4);







