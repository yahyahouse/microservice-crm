create table orders
(
    id         varchar(45) not null,
    product_id varchar(45),
    status     varchar(45),
    price      bigint
);

create table notifications
(
    id       varchar(45),
    order_id varchar(45),
    status   varchar(45)
);

create table actions
(
    id     varchar(45),
    action varchar(45)
);

create table steps
(
    id        varchar(45),
    action_id varchar(45),
    steps     varchar(45),
    orders    int
);

