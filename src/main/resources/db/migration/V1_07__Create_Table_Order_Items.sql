CREATE TABLE tbl_order_items
(
    id              SERIAL PRIMARY KEY,
    col_created_at  TIMESTAMP(6) WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP(6) NOT NULL,
    col_created_by  TEXT,
    col_updated_at  TIMESTAMP(6) WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP(6) NOT NULL,
    col_updated_by  TEXT,
    col_deleted_at  TIMESTAMP(6) WITH TIME ZONE,
    col_deleted_by  TEXT,
    col_product_id  INT                                                      NOT NULL,
    col_order_id    INT                                                      NOT NULL,
    col_quantity    INT                                                      NOT NULL CHECK (col_quantity > 0),
    col_price       BIGINT                                                   NOT NULL CHECK (col_price >= 0),
    col_total_price BIGINT                                                   NOT NULL CHECK (col_total_price >= 0 and col_total_price = col_price * col_quantity),
    CONSTRAINT fk_orders
        FOREIGN KEY (col_order_id)
            REFERENCES tbl_orders (id),
    CONSTRAINT fk_products
        FOREIGN KEY (col_product_id)
            REFERENCES tbl_products (id)
);

CREATE INDEX order_items_order_id_index ON tbl_order_items (col_order_id);
CREATE INDEX order_items_product_id_index ON tbl_order_items (col_product_id);