-- テーブル作成
-- 著者
CREATE TABLE IF NOT EXISTS AUTHOR (
  ID SERIAL NOT NULL,
  NAME VARCHAR(255) NOT NULL,
  BIRTHDAY CHAR(8) NOT NULL,
  UPDATED CHAR(14) NOT NULL,
  CREATED CHAR(14) NOT NULL
);
-- 書籍
CREATE TABLE IF NOT EXISTS BOOK (
  ID SERIAL NOT NULL,
  TITLE VARCHAR(255) NOT NULL,
  PRICE INTEGER check(PRICE > 0),
  PUBLISH CHAR(1) NOT NULL,
  UPDATED CHAR(14) NOT NULL,
  CREATED CHAR(14) NOT NULL
);
-- 著者書籍
CREATE TABLE IF NOT EXISTS AUTHOR_BOOKS (
  AUTHOR_ID INTEGER NOT NULL,
  BOOK_ID INTEGER NOT NULL
);
