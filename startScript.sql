CREATE SEQUENCE IF NOT EXISTS user_name_seq START WITH 1 INCREMENT BY 1;
CREATE EXTENSION pgcrypto;

ALTER TABLE group_persons
    ADD CONSTRAINT fk_group
        FOREIGN KEY (id_group) REFERENCES group_ (id)
            ON DELETE CASCADE;


ALTER TABLE recipe_product
    ADD CONSTRAINT fk_product
        FOREIGN KEY (id_product) REFERENCES product (id);
ALTER TABLE recipe_product
    ADD CONSTRAINT fk_recipe
        FOREIGN KEY (id_recipe) REFERENCES recipe (id);
ALTER TABLE recipe_product
    ADD CONSTRAINT fk_metric
        FOREIGN KEY (id_metric) REFERENCES metric (id);


ALTER TABLE check_
    ADD CONSTRAINT fk_group
        FOREIGN KEY (id_group) REFERENCES group_ (id) ON DELETE CASCADE;

ALTER TABLE basket
    ADD CONSTRAINT fk_group
        FOREIGN KEY (id_purchase) REFERENCES purchase (id) ON DELETE CASCADE;

ALTER TABLE target
    ADD CONSTRAINT fk_group
        FOREIGN KEY (id_group) REFERENCES group_ (id) ON DELETE CASCADE;



INSERT INTO category_types (id, name)
VALUES (1, 'Продукты'),
       (2, 'Цели'),
       (3, 'Рецепты');

Insert into metric
values (DEFAULT, 'шт', 'pcs'),
       (DEFAULT, 'кг', 'kgs'),
       (DEFAULT, 'гр', 'gr'),
       (DEFAULT, 'л', 'l'),
       (DEFAULT, 'мл', 'ml');

INSERT INTO currency
values (DEFAULT, 'Российский рубль', 'Russian ruble', '₽'),
       (DEFAULT, 'Доллар США', 'U.S. dollar', '$');



INSERT INTO shop
(id, status, name, name_en)
values (DEFAULT, true, 'Аптека', 'Pharmacy'),
       (DEFAULT, true, 'Магнит', 'Pharmacy'),
       (DEFAULT, false, 'Азбука вкуса', 'Pharmacy'),
       (DEFAULT, true, 'Ашан', 'Pharmacy'),
       (DEFAULT, false, 'Бристоль', 'Pharmacy'),
       (DEFAULT, false, 'Виктория', 'Pharmacy'),
       (DEFAULT, false, 'Вкусвилл', 'Pharmacy'),
       (DEFAULT, false, 'Красное и белое', 'Pharmacy'),
       (DEFAULT, true, 'Лента', 'Pharmacy'),
       (DEFAULT, false, 'Окей', 'Pharmacy'),
       (DEFAULT, true, 'Перекрёсток', 'Pharmacy'),
       (DEFAULT, true, 'Пятерочка', 'Pharmacy'),
       (DEFAULT, true, 'Продуктовый', 'Pharmacy'),
       (DEFAULT, true, 'Рынок', 'Pharmacy'),
       (DEFAULT, true, 'Самокат', 'Pharmacy'),
       (DEFAULT, false, 'Спортпит', 'Pharmacy'),
       (DEFAULT, true, 'Фикспрайс', 'Pharmacy'),
       (DEFAULT, true, 'Другое', 'Pharmacy');

INSERT INTO category (name, name_en, pic, color, type)
values ('Алкоголь', '', 'pic/category/1', '#b0c3d4', 1),
       ('Готовая еда', '', 'pic/category/1', '#b0c3d4', 1),
       ('Грибы', '', 'pic/category/1', '#b0c3d4', 1),
       ('Зелень', '', 'pic/category/1', '#b0c3d4', 1),
       ('Крупы', '', 'pic/category/1', '#b0c3d4', 1),
       ('Масло', '', 'pic/category/1', '#b0c3d4', 1),
       ('Молочные продукты', '', 'pic/category/1', '#b0c3d4', 1),
       ('Морепродукты', '', 'pic/category/1', '#b0c3d4', 1),
       ('Мясо', '', 'pic/category/1', '#b0c3d4', 1),
       ('Напитки еда', '', 'pic/category/1', '#b0c3d4', 1),
       ('Овощи', '', 'pic/category/1', '#b0c3d4', 1),
       ('Орехи', '', 'pic/category/1', '#b0c3d4', 1),
       ('Полуфабрикаты', '', 'pic/category/1', '#b0c3d4', 1),
       ('Рыба', '', 'pic/category/1', '#b0c3d4', 2),
       ('Сладости', '', 'pic/category/1', '#b0c3d4', 2),
       ('Снеки', '', 'pic/category/1', '#b0c3d4', 2),
       ('Фрукты', '', 'pic/category/1', '#b0c3d4', 2),
       ('Химия', '', 'pic/category/1', '#b0c3d4', 2),
       ('Хлебобулочные изделия', '', 'pic/category/1', '#b0c3d4', 2),
       ('Яичные продукты', '', 'pic/category/1', '#b0c3d4', 2),
       ('Лекарство', '', 'pic/category/1', '#b0c3d4', 2),
       ('Бытовые товары', '', 'pic/category/1', '#b0c3d4', 2),
       ('Быт', '', 'pic/category/1', '#b0c3d4', 2),
       ('Дом', '', 'pic/category/1', '#b0c3d4', 2),
       ('Досуг', '', 'pic/category/1', '#b0c3d4', 2),
       ('Здоровье', '', 'pic/category/1', '#b0c3d4', 3),
       ('Мебель', '', 'pic/category/1', '#b0c3d4', 3),
       ('Одежда', '', 'pic/category/1', '#b0c3d4', 3),
       ('Подарок', '', 'pic/category/1', '#b0c3d4', 3),
       ('Продукты', '', 'pic/category/1', '#b0c3d4', 3),
       ('Путешествие', '', 'pic/category/1', '#b0c3d4', 3),
       ('Спорт', '', 'pic/category/1', '#b0c3d4', 3),
       ('Транспорт', '', 'pic/category/1', '#b0c3d4', 3),
       ('Творчество', '', 'pic/category/1', '#b0c3d4', 3),
       ('Электроника', '', 'pic/category/1', '#b0c3d4', 3),
       ('Другое', '', 'pic/category/1', '#b0c3d4', 3);


INSERT INTO product (name, name_en, fat, protein, carb, calories, weight, id_category, allergy, pic)
values ('Филе куриной грудки', '', 1.9, 23.6, 0.4, 113, 200, 11, true, 'pic/product/1'),
       ('Сливочное масло', '', 72.5, 0.8, 1.3, 661, 180, 11, true, 'pic/product/1'),
       ('Куриное яйцо', '', 11, 13, 1.1, 155, 70, 11, true, 'pic/product/1'),
       ('Пшеничная мука', '', 1, 10, 76, 364, 1000, 11, true, 'pic/product/1'),
       ('Петрушка', '', 0.8, 3, 6, 36, 2, 11, true, 'pic/product/1'),
       ('Панировочные сухари', '', 1.9, 9.7, 77.6, 347, 1000, 11, true, 'pic/product/1'),
       ('Подсолнечное масло', '', 99.9, 0, 0, 899, 900, 11, true, 'pic/product/1');

