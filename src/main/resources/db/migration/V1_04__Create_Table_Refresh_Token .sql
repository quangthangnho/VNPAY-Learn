CREATE TABLE tbl_refresh_tokens
(
    id             SERIAL PRIMARY KEY,
    col_token      TEXT UNIQUE                 NOT NULL,
    col_expired_at TIMESTAMP(6) WITH TIME ZONE NOT NULL,
    col_user_id    INT,
    CONSTRAINT fk_users
        FOREIGN KEY (col_user_id)
            REFERENCES tbl_users (id)
);
CREATE INDEX token_index ON tbl_refresh_tokens (col_token);
CREATE INDEX refresh_token_user_id_index ON tbl_refresh_tokens (col_user_id);