CREATE TABLE tbl_roles
(
    id              SERIAL PRIMARY KEY,
    col_role_name   TEXT unique NOT NULL,
    col_description TEXT
);
CREATE INDEX role_name_index ON tbl_roles (col_role_name);
INSERT INTO tbl_roles (col_role_name, col_description)
VALUES ('ADMIN', 'admin');
INSERT INTO tbl_roles (col_role_name, col_description)
VALUES ('USER', 'user');

