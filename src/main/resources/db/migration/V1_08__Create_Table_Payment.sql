CREATE TYPE enum_payment_method AS ENUM ('VNPAY', 'PAYPAL', 'CREDIT_CARD', 'DEBIT_CARD', 'CASH');
CREATE TYPE enum_payment_status AS ENUM ('PENDING', 'CANCELED', 'COMPLETED', 'REFUNDED', 'FAILED');
CREATE TABLE tbl_payments
(
    id                 SERIAL PRIMARY KEY,
    col_created_at     TIMESTAMP(6) WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP(6) NOT NULL,
    col_created_by     TEXT,
    col_updated_at     TIMESTAMP(6) WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP(6) NOT NULL,
    col_updated_by     TEXT,
    col_deleted_at     TIMESTAMP(6) WITH TIME ZONE,
    col_deleted_by     TEXT,
    col_order_id       INT                                                      NOT NULL,
    col_amount         BIGINT                                                   NOT NULL CHECK (col_amount >= 0),
    col_payment_method enum_payment_method                                      NOT NULL,
    col_payment_status enum_payment_status         DEFAULT 'PENDING'            NOT NULL,
    col_transaction_id TEXT UNIQUE                                              ,
    col_user_id        INT                                                      NOT NULL,
    col_payment_code   TEXT UNIQUE                                              NOT NULL,
    CONSTRAINT fk_orders
        FOREIGN KEY (col_order_id)
            REFERENCES tbl_orders (id),
    CONSTRAINT fk_users
        FOREIGN KEY (col_user_id)
            REFERENCES tbl_users (id)
);
CREATE INDEX payments_order_id_index ON tbl_payments (col_order_id);
CREATE INDEX payments_user_id_index ON tbl_payments (col_user_id);
CREATE INDEX payment_status_index ON tbl_payments (col_payment_status);
CREATE INDEX payment_code_index ON tbl_payments (col_payment_code);
CREATE INDEX payment_transaction_id_index ON tbl_payments (col_transaction_id);
