-- тут пишется только нативный (оригинальный) SQL
INSERT INTO tb_products (article, name, type, version, width, length, height, weight, cost, image_patch) VALUES
 ('123401', 'Альфа', 'ARMCHAIR', 'YELLOW', 0.8, 0.9, 1.0, 45, 19290.00, 'alfa.jpg'),
 ('123402', 'Джейн', 'BED', 'BLACK', 0.8, 1.6, 0.15, 40, 13440.00, ''),
 ('123403', 'Европа', 'SOFA', 'YELLOW', 1.9, 0.6, 0.8, 30, 12590.00, ''),
 ('123404', 'Нильс', 'ARMCHAIR', 'YELLOW', 0.7, 0.6, 1.2, 37, 23560.30, ''),
 ('123405', 'Комфорт', 'SOFA', 'RED', 2.0, 0.9, 1.1, 90, 16990.50, '');

-- INSERt INTO tb_roles(id, role_type) VALUES (1, 'ROLE_ADMIN');
-- UPDATE tb_users SET role_id= 1 WHERE id = 1;

INSERt INTO tb_roles(id, role_type) VALUES (2, 'ROLE_MANAGER');
INSERt INTO tb_roles(id, role_type) VALUES (3, 'ROLE_BUYER');




-- INSERT INTO tb_users (bucket_id, email, name, password, role)
-- VALUES (null, '9619938@yandex.ru', 'Дмитрий', '1234', 'BUYER');
--
-- INSERT INTO tb_prices (created_at) VALUES ('01-01-2024');
--
-- INSERT INTO tb_prices_products (price_id, product_article) VALUES (1, (SELECT article FROM tb_products));




-- INSERT INTO tb_genres (created_at, in_archive, name, description, url)
-- VALUES ('01-01-2022', false, 'Натюрморт', 'Описание', 'stilllife');
--
-- INSERT INTO tb_picturs (created_at, in_archive, description, genre_url, image_path, name)
-- VALUES ('01-01-2023', false, 'Описание...', 'stilllife', 'picture01.png', 'Картина №1');
--
-- INSERT INTO tb_picturs (created_at, in_archive, description, genre_url, image_path, name)
-- VALUES ('01-04-2023', false, 'Описание...', 'stilllife', 'picture02.png', 'Картина №2');



-- schema.sql - принято писать запросы на создание объектов БД (например, таблиц)
-- data.sql - запросы связанные с вставкой, удалением (НО НЕ SELECT запросы!!!)