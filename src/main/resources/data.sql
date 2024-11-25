CREATE SEQUENCE IF NOT EXISTS user_name_seq START WITH 1 INCREMENT BY 1;


ALTER TABLE group_users
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

ALTER TABLE target
    ADD CONSTRAINT fk_group
        FOREIGN KEY (id_group) REFERENCES group_ (id) ON DELETE CASCADE;


CREATE OR REPLACE FUNCTION recipe_update()
    RETURNS TRIGGER AS
$$
DECLARE
    target record;
    p numeric := 0;
    f numeric := 0;
    c numeric := 0;
    w numeric := 0;
    wCurrent decimal := 0;
BEGIN
    for target in select id_metric, count, id_product from recipe_product where recipe_product.id_recipe = NEW.id_recipe
        loop
            wCurrent :=0;
            IF (target.id_metric = 1) THEN
                wCurrent := (select weight from product where id = target.id_product) * target.count;
                w := w +wCurrent;
            END IF;
            IF (target.id_metric = 2 OR target.id_metric = 4) THEN
                wCurrent :=  target.count * 1000;
                w := w + wCurrent;
            END IF;
            IF (target.id_metric = 3 OR target.id_metric = 5) THEN
                wCurrent := target.count;
                w := w + wCurrent;
            END IF;

            p := p + (select protein from product where id = target.id_product) * (wCurrent/100);
            f := f + (select fat from product where id = target.id_product) * (wCurrent/100);
            c := c + (select carb from product where id = target.id_product) * (wCurrent/100);
        end loop;
    p := (p / (w / 100))::numeric(3,1);
    f := (f / (w / 100))::numeric(3,1);
    c := (c / (w / 100))::numeric(3,1);
    update recipe set protein = p, fat =f, carb = c, calories = p * 4 + f * 9 + c * 4 where id = NEW.id_recipe;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER recipe_update
    AFTER INSERT
    ON recipe_product
    FOR EACH ROW
EXECUTE FUNCTION recipe_update();


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

INSERT INTO recipe (name, name_en, portion, fat, protein, carb, calories, id_category, description, pic)
values ('Котлеты по киевски', '', 4, 0, 0, 0, 0, 34, 'Шаг 1
Разрежьте масло на 4 части и положите в
морозильник.
Шаг 2
Положите куриное филе на рабочую поверхность,
отделите малое филе и отложите.
Шаг 3
Начиная с толстой части, аккуратно подрежьте филе
и раскройте его как книгу.
Шаг 4
Накройте филе пищевой пленкой и отбейте до
равномерной толщины. Посолите и поперчите
со всех сторон.
Шаг 5
Отделите листья петрушки от стеб­лей и очень мелко
нарежьте. Достаньте сливочное масло и обваляйте в
пет­рушке.
Шаг 6
Выложите куски масла в центр филе, сверху накройте
малым филе. Сверните котлету, аккуратно подворачивая
все уголки внутрь, чтобы масло не вытекло
наружу.
Шаг 7
Каждую котлету заверните в пищевую пленку – так
котлета сохранят форму. Уберите в морозильник на 10
мин.
Шаг 8
Насыпьте в тарелки муку и панировочные сухари.
Разбейте яйца в тарелку и слегка взбейте. Обмакните
котлету в муке со всех сторон, стряхните излишки,
потом опустите в яйцо, стряхните, обваляйте в сухарях.
Затем снова окуните в яйцо и сухари. Положите котлеты
на тарелку и уберите в морозильник на 10 мин.
Шаг 9
Разогрейте духовку до 180 °С. Разогрейте в кастрюле
или фритюрнице растительное масло, пока на
поверхности не начнут появляться пузыри. Обжарьте
котлеты со всех сторон до золотистой корочки, 2–3 мин.
Аккуратно переложите на противень и доведите до
готовности в духовке, 10–12 мин.', 'pic/recipe/1');

INSERT INTO recipe_product (id_recipe, id_product, id_metric, count)
values (1, 1, 1, 4),
       (1, 2, 3, 180),
       (1, 3, 1, 3),
       (1, 4, 3, 50),
       (1, 5, 1, 5),
       (1, 6, 3, 20),
       (1, 7, 3, 100);