
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
(id, name, name_en)
values (DEFAULT,'Аптека', 'Pharmacy'),
       (DEFAULT,'Магнит', 'Pharmacy'),
       (DEFAULT, 'Азбука вкуса', 'Pharmacy'),
       (DEFAULT,'Ашан', 'Pharmacy'),
       (DEFAULT, 'Бристоль', 'Pharmacy'),
       (DEFAULT, 'Виктория', 'Pharmacy'),
       (DEFAULT, 'Вкусвилл', 'Pharmacy'),
       (DEFAULT, 'Красное и белое', 'Pharmacy'),
       (DEFAULT,'Лента', 'Pharmacy'),
       (DEFAULT, 'Окей', 'Pharmacy'),
       (DEFAULT,'Перекрёсток', 'Pharmacy'),
       (DEFAULT,'Пятерочка', 'Pharmacy'),
       (DEFAULT,'Продуктовый', 'Pharmacy'),
       (DEFAULT,'Рынок', 'Pharmacy'),
       (DEFAULT,'Самокат', 'Pharmacy'),
       (DEFAULT, 'Спортпит', 'Pharmacy'),
       (DEFAULT,'Фикспрайс', 'Pharmacy'),
       (DEFAULT,'Другое', 'Pharmacy');

INSERT INTO category_product (name, name_en, pic)
values ('Алкоголь', '', 'pic/category/1'),
       ('Готовая еда', '', 'pic/category/1'),
       ('Грибы', '', 'pic/category/1'),
       ('Зелень', '', 'pic/category/1'),
       ('Крупы', '', 'pic/category/1'),
       ('Масло', '', 'pic/category/1'),
       ('Молочные продукты', '', 'pic/category/1'),
       ('Морепродукты', '', 'pic/category/1'),
       ('Мясо', '', 'pic/category/1'),
       ('Напитки еда', '', 'pic/category/1'),
       ('Овощи', '', 'pic/category/1'),
       ('Орехи', '', 'pic/category/1'),
       ('Полуфабрикаты', '', 'pic/category/1'),
       ('Рыба', '', 'pic/category/1'),
       ('Сладости', '', 'pic/category/1'),
       ('Снеки', '', 'pic/category/1'),
       ('Фрукты', '', 'pic/category/1'),
       ('Химия', '', 'pic/category/1'),
       ('Хлебобулочные изделия', '', 'pic/category/1'),
       ('Яичные продукты', '', 'pic/category/1'),
       ('Лекарство', '', 'pic/category/1'),
       ('Бытовые товары', '', 'pic/category/1'),
       ('Быт', '', 'pic/category/1'),
       ('Дом', '', 'pic/category/1'),
       ('Досуг', '', 'pic/category/1'),
       ('Здоровье', '', 'pic/category/1'),
       ('Мебель', '', 'pic/category/1'),
       ('Одежда', '', 'pic/category/1'),
       ('Подарок', '', 'pic/category/1'),
       ('Продукты', '', 'pic/category/1'),
       ('Путешествие', '', 'pic/category/1'),
       ('Спорт', '', 'pic/category/1'),
       ('Транспорт', '', 'pic/category/1'),
       ('Творчество', '', 'pic/category/1'),
       ('Электроника', '', 'pic/category/1'),
       ('Другое', '', 'pic/category/1');


INSERT INTO product (name, name_en, fat, protein, carb, calories, weight, id_category_product, allergy, pic,id_metric, quantity_multiplier)
values ('Филе куриной грудки', '', 1.9, 23.6, 0.4, 113, 200, 11, true, 'pic/product/1',2,2),
       ('Сливочное масло', '', 72.5, 0.8, 1.3, 661, 180, 11, true, 'pic/product/1',2,1),
       ('Куриное яйцо', '', 11, 13, 1.1, 155, 70, 11, true, 'pic/product/1',1,3),
       ('Пшеничная мука', '', 1, 10, 76, 364, 1000, 11, true, 'pic/product/1',2,2),
       ('Петрушка', '', 0.8, 3, 6, 36, 2, 11, true, 'pic/product/1',1,3),
       ('Панировочные сухари', '', 1.9, 9.7, 77.6, 347, 1000, 11, true, 'pic/product/1',2,1),
       ('Подсолнечное масло', '', 99.9, 0, 0, 899, 900, 11, true, 'pic/product/1',4,2);
