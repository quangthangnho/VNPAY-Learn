CREATE TABLE tbl_users_roles
(
    id          SERIAL PRIMARY KEY,
    col_user_id INT,
    col_role_id INT,
    CONSTRAINT fk_users
        FOREIGN KEY (col_user_id)
            REFERENCES tbl_users (id),
    CONSTRAINT fk_roles
        FOREIGN KEY (col_role_id)
            REFERENCES tbl_roles (id)
);
CREATE INDEX user_id_index ON tbl_users_roles (col_user_id);
CREATE INDEX role_id_index ON tbl_users_roles (col_role_id);