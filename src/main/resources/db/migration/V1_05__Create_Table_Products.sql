CREATE TABLE tbl_products
(
    id               SERIAL PRIMARY KEY,
    col_created_at   TIMESTAMP(6) WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP(6) NOT NULL,
    col_created_by   TEXT,
    col_updated_at   TIMESTAMP(6) WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP(6) NOT NULL,
    col_updated_by   TEXT,
    col_deleted_at   TIMESTAMP(6) WITH TIME ZONE,
    col_deleted_by   TEXT,
    col_name         TEXT                                                     NOT NULL,
    col_description  TEXT,
    col_price        BIGINT                                                   NOT NULL CHECK (col_price >= 0), /* In VIETNAM DONG ex: 10000 */
    col_product_code TEXT UNIQUE                                              NOT NULL
);
CREATE INDEX idx_tbl_products_col_product_code ON tbl_products (col_product_code);