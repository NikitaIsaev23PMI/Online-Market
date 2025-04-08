create table if not exists products_schema.t_discount
(
    product_id int primary key references products_schema.t_product(id) on delete cascade,
    c_amount int check ( c_amount >= 1 and c_amount <= 100),
    c_start TIMESTAMP,
    c_end TIMESTAMP
);