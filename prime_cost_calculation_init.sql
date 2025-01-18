CREATE DATABASE prime_cost_calculation;

-- пользователи (для входа)
CREATE TABLE users (
    id integer PRIMARY KEY,
    login varchar(50) NOT NULL UNIQUE,
    password varchar(50) NOT NULL
);

-- постоянные издержки (с указанием месяца, в котором действуют)
CREATE TABLE fixed_cost (
    id integer PRIMARY KEY,
    name varchar(50) NOT NULL,
    cost decimal(10, 2) NOT NULL,
    period timestamp NOT NULL
);

-- сотрудники
CREATE TABLE worker (
    id integer PRIMARY KEY,
    name varchar(50) NOT NULL
);

-- зарплата сотрудников
CREATE TABLE worker_salary (
    id integer PRIMARY KEY,
    worker_id integer REFERENCES worker(id),
    salary decimal(10, 2) NOT NULL,
    period timestamp NOT NULL
);

-- продукция
CREATE TABLE product (
    id integer PRIMARY KEY,
    name varchar(50) NOT NULL UNIQUE
);

-- закрепление сотрудников за продукцией
CREATE TABLE worker_product (
    worker_id integer REFERENCES worker(id),
    product_id integer REFERENCES product(id),
    period timestamp NOT NULL,
    PRIMARY KEY (worker_id, product_id)
);

-- материалы (и иные переменные издержки)
CREATE TABLE material (
    id integer PRIMARY KEY,
    name varchar(50) NOT NULL UNIQUE
);

-- стоимость материалов
CREATE TABLE material_cost (
    id integer PRIMARY KEY,
    material_id integer REFERENCES material(id),
    cost decimal(10, 2) NOT NULL,
    period timestamp NOT NULL
);

-- материалы (переменные издержки) на единицу продукции
CREATE TABLE product_material (
    product_id integer REFERENCES product (id),
    material_id integer REFERENCES material (id),
    number integer NOT NULL,
    period timestamp NOT NULL,
    PRIMARY KEY (product_id, material_id)
);

-- партия
CREATE TABLE production (
    id integer PRIMARY KEY,
    product_id integer REFERENCES product(id),
    number integer NOT NULL,
    period timestamp NOT NULL
);