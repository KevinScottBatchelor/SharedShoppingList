BEGIN TRANSACTION;

DROP TABLE IF EXISTS accounts, items, lists, groups, account_groups CASCADE;


CREATE TABLE accounts (
        account_id SERIAL,
        user_id int NOT NULL,
        
        CONSTRAINT PK_account_id PRIMARY KEY (account_id),
        CONSTRAINT FK_user_id FOREIGN KEY (user_id) REFERENCES users(user_id)

);

CREATE TABLE lists (
        list_id SERIAL,
        list_name varchar(30) NOT NULL,
        account_id int NOT NULL,
        claimed_by varchar(50), 
        
        CONSTRAINT PK_list_id PRIMARY KEY (list_id),
        CONSTRAINT FK_account_id FOREIGN KEY (account_id) REFERENCES accounts(account_id)   
);

CREATE TABLE items (
        item_id SERIAL,
        list_id int NOT NULL,
        item_name varchar(30) NOT NULL,
        quantity int NOT NULL,
        date_added date NOT NULL,
        created_by varchar(50), 
        
        CONSTRAINT PK_item_id PRIMARY KEY (item_id),
        CONSTRAINT FK_list_id FOREIGN KEY (list_id) REFERENCES lists(list_id)
        
);

CREATE TABLE groups (
        group_id SERIAL,
        group_name varchar(30) NOT NULL,
            
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

INSERT INTO accounts(user_id)
VALUES (1);

INSERT INTO accounts(user_id)
VALUES (2);

INSERT INTO lists(list_name, account_id, claimed_by)
VALUES ('Max''s shopping list', 2, 'admin');

INSERT INTO lists(list_name, account_id)
VALUES ('Kevin''s shopping list', 1);

INSERT INTO items(list_id, item_name, quantity, date_added, created_by)
VALUES (1, 'Apple', 10, current_date, 'user');

INSERT INTO items(list_id, item_name, quantity, date_added, created_by)
VALUES (2, 'Peach', 10, current_date, 'admin');

INSERT INTO groups(group_name)
VALUES ('GROUP 1');

INSERT INTO groups(group_name)
VALUES ('GROUP 2');

INSERT INTO lists_in_group(group_id, list_id)
VALUES (1,1);

INSERT INTO lists_in_group(group_id, list_id)
VALUES (2,2);

INSERT INTO account_groups(group_id, member_of_group_id, joined_date)
VALUES (1,1, current_date);

INSERT INTO account_groups(group_id, member_of_group_id, joined_date)
VALUES (2,2, current_date);

COMMIT TRANSACTION; 
