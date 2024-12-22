-- データ削除
DELETE FROM AUTHOR;
DELETE FROM BOOK;
DELETE FROM AUTHOR_BOOKS;

-- AUTO INCREMENT初期化
ALTER SEQUENCE AUTHOR_ID_SEQ RESTART WITH 1;
ALTER SEQUENCE BOOK_ID_SEQ RESTART WITH 1;

-- テストデータ登録
-- 著者
INSERT INTO AUTHOR (NAME, BIRTHDAY, UPDATED, CREATED) VALUES ('尾田栄一郎', '19750101', '20241210180000', '20241210180000');
INSERT INTO AUTHOR (NAME, BIRTHDAY, UPDATED, CREATED) VALUES ('岸本斉史', '19741108', '20241210180000', '20241210180000');

-- 書籍
INSERT INTO BOOK (TITLE, PRICE, PUBLISH ,UPDATED, CREATED) VALUES ('ワンピース 1巻', 420, '1','20241210180000', '20241210180000');
INSERT INTO BOOK (TITLE, PRICE, PUBLISH ,UPDATED, CREATED) VALUES ('ワンピース 2巻', 425, '1','20241210180000', '20241210180000');
INSERT INTO BOOK (TITLE, PRICE, PUBLISH ,UPDATED, CREATED) VALUES ('ワンピース 3巻', 430, '0','20241210180000', '20241210180000');
INSERT INTO BOOK (TITLE, PRICE, PUBLISH ,UPDATED, CREATED) VALUES ('ナルト 1巻', 420, '1','20241210180000', '20241210180000');

-- 著者_書籍
INSERT INTO AUTHOR_BOOKS (AUTHOR_ID, BOOK_ID) VALUES (1, 1);
INSERT INTO AUTHOR_BOOKS (AUTHOR_ID, BOOK_ID) VALUES (1, 2);
INSERT INTO AUTHOR_BOOKS (AUTHOR_ID, BOOK_ID) VALUES (1, 3);
INSERT INTO AUTHOR_BOOKS (AUTHOR_ID, BOOK_ID) VALUES (2, 4);