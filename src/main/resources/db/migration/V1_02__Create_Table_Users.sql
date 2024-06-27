CREATE TYPE user_status AS ENUM ('ACTIVE', 'DEACTIVATED');
CREATE TABLE tbl_users
(
    id               SERIAL PRIMARY KEY,
    col_created_at   TIMESTAMP(6) WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP(6) NOT NULL,
    col_created_by   TEXT,
    col_updated_at   TIMESTAMP(6) WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP(6) NOT NULL,
    col_updated_by   TEXT,
    col_deleted_at   TIMESTAMP(6) WITH TIME ZONE,
    col_deleted_by   TEXT,
    col_status       user_status                 DEFAULT 'ACTIVE'             NOT NULL,
    col_email        TEXT unique                                              NOT NULL,
    col_full_name    TEXT                                                     NOT NULL,
    col_phone_number TEXT unique,
    col_password     TEXT                                                     NOT NULL
);

CREATE INDEX email_index ON tbl_users (col_email);
CREATE INDEX phone_number_index ON tbl_users (col_phone_number);
