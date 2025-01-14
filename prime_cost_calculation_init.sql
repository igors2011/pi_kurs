CREATE DATABASE prime_cost_calculation;

-- пользователи (для входа)
CREATE TABLE user (
    id integer PRIMARY KEY,
    login varchar(50) NOT NULL,
    password varchar(50) NOT NULL
);

-- постоянные издержки (с указанием месяца, в котором действуют)
CREATE TABLE fixed_cost (
    id integer PRIMARY KEY,
    name varchar(50) NOT NULL,
    cost decimal(10, 2) NOT NULL,
    period timestamp
);

-- сотрудники
CREATE TABLE worker (
    id integer PRIMARY KEY,
    name varchar(50) NOT NULL,
    salary decimal(10, 2) NOT NULL
);

-- продукция
CREATE TABLE product (
    id integer PRIMARY KEY,
    name varchar(50) NOT NULL,
    prime_cost decimal(10, 2) NOT NULL
);

-- материалы (и иные переменные издержки)
CREATE TABLE material (
    id integer PRIMARY KEY,
    name varchar(50) NOT NULL UNIQUE
)

-- материалы (переменные издержки) на единицу продукции
CREATE TABLE product_material (
    product_id integer REFERENCES product (id),
    material_id integer REFERENCES material (id),
    PRIMARY KEY (product_id, material_id),
)