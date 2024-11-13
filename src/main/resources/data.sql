CREATE SEQUENCE IF NOT EXISTS user_name_seq START WITH 1 INCREMENT BY 1;


ALTER TABLE group_users
    ADD CONSTRAINT fk_group
        FOREIGN KEY (id_group) REFERENCES group_(id)
            ON DELETE CASCADE;


ALTER TABLE recipe_product
    ADD CONSTRAINT fk_product
        FOREIGN KEY (id_product) REFERENCES product(id);
ALTER TABLE recipe_product
    ADD CONSTRAINT fk_recipe
        FOREIGN KEY (id_recipe) REFERENCES recipe(id);
ALTER TABLE recipe_product
    ADD CONSTRAINT fk_metric
        FOREIGN KEY (id_metric) REFERENCES metric(id);


ALTER TABLE check_
    ADD CONSTRAINT fk_group
        FOREIGN KEY (id_group) REFERENCES group_(id) ON DELETE CASCADE;

ALTER TABLE target
    ADD CONSTRAINT fk_group
        FOREIGN KEY (id_group) REFERENCES group_(id) ON DELETE CASCADE;


INSERT INTO category_types (id,name) VALUES (1,'Продукты'),(2,'Цели'),(3,'Рецепты');

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

INSERT INTO category (name, name_en, type)
values ('Алкоголь', '', 1),
       ('Готовая еда', '', 1),
       ('Грибы', '', 1),
       ('Зелень', '', 1),
       ('Крупы', '', 1),
       ('Масло', '', 1),
       ('Молочные продукты', '', 1),
       ('Морепродукты', '', 1),
       ('Мясо', '', 1),
       ('Напитки еда', '', 1),
       ('Овощи', '', 1),
       ('Орехи', '', 1),
       ('Полуфабрикаты', '', 1),
       ('Рыба', '', 1),
       ('Сладости', '', 1),
       ('Снеки', '', 1),
       ('Фрукты', '', 1),
       ('Химия', '', 1),
       ('Хлебобулочные изделия', '', 1),
       ('Яичные продукты', '', 1),
       ('Лекарство', '', 1),
       ('Бытовые товары', '', 1),
       ('Быт', '', 2),
       ('Дом', '', 2),
       ('Досуг', '', 2),
       ('Здоровье', '', 2),
       ('Мебель', '', 2),
       ('Одежда', '', 2),
       ('Подарок', '', 2),
       ('Продукты', '', 2),
       ('Путешествие', '', 2),
       ('Спорт', '', 2),
       ('Транспорт', '', 2),
       ('Творчество', '', 2),
       ('Электроника', '', 2),
       ('Другое', '', 2);


INSERT INTO product (name, name_en, fat, protein, carb, calorie, id_category, allergy)
values ('гречка','',3.3,12.6,57.1,308.0,77,true),
       ('овсянка','',6.1,12.3,59.5,68.0,77,true),
       ('рис','',2.6,7.5,62.3,303.0,77,true),
       ('кукурузная крупа','',1.2,8.3,71.0,328.0,77,true),
       ('пшеничная крупа','',2.0,11.2,65.7,342.0,77,true),
       ('манная крупа','',1.0,10.3,70.6,333.0,77,true),
       ('булгур','',1.5,15.0,14.1,85.0,77,true);