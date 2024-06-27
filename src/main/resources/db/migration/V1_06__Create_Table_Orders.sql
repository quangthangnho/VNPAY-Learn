CREATE TYPE order_status AS ENUM ('PENDING', 'PAID', 'SHIPPED', 'CANCELED', 'COMPLETED', 'REFUNDED');
CREATE TABLE tbl_orders
(
    id               SERIAL PRIMARY KEY,
    col_created_at   TIMESTAMP(6) WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP(6) NOT NULL,
    col_created_by   TEXT,
    col_updated_at   TIMESTAMP(6) WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP(6) NOT NULL,
    col_updated_by   TEXT,
    col_deleted_at   TIMESTAMP(6) WITH TIME ZONE,
    col_deleted_by   TEXT,
    col_order_date   TIMESTAMP(6) WITH TIME ZONE                              NOT NULL,
    col_order_status order_status                DEFAULT 'PENDING'            NOT NULL,
    col_order_total  BIGINT                                                   NOT NULL CHECK (col_order_total >= 0),
    col_user_id      INT,
    col_order_code   TEXT UNIQUE                                              NOT NULL,
    CONSTRAINT fk_users
        FOREIGN KEY (col_user_id)
            REFERENCES tbl_users (id)
);
CREATE INDEX order_status_index ON tbl_orders (col_order_status);
CREATE INDEX order_code_index ON tbl_orders (col_order_code);
CREATE INDEX orders_user_id_index ON tbl_orders (col_user_id);