# コーディングテスト

## 事前準備

### 1. DBコンテナ起動

* $ docker compose up -d --build

### 2. flywayマイグレーション

* $ ./gradlew flywayInfo
    * flywayステータス確認。flywayのステータスがpendingになっていること
* $ ./gradlew processResources
    * リソースをクラス出力先へコピー 
* $ ./gradlew flywayMigrate
    * flywayMigrate実行
* $ ./gradlew flywayInfo
    * flywayステータス確認。flywayのステータスがsuccessになっていること

### 3. jooqコード生成

* $ ./gradlew :jooqCodegen
    * jooqのソースコード生成。(jooqのソースコードはgit管理外)
    * /codingtest/src/main/kotlin/com.example.codingtest.domain.jooqに生成される

### 4.アプリケーションを起動
    
## API実行方法

### API実行に必要な著者ID・書籍IDの取得

* ※本機能は課題の対象外ですが、更新や取得に著者ID・書籍IDが必要なため実装しました。

```
curl -H "Content-Type: application/json" \
  http://localhost:8080/api/author-books-ids
```

### 著者に紐づく本を取得

```
curl -H "Content-Type: application/json" \
  http://localhost:8080/api/author/${著者ID}
```

### 著者の情報を登録

```
curl -X POST -H "Content-Type: application/json" \
  http://localhost:8080/api/author -d '{"name":"${著者名}","birthday":"${誕生日(yyyyMMdd形式)}"}'
```

### 著者の情報を更新

```
curl -X PATCH -H "Content-Type: application/json" \
  http://localhost:8080/api/author -d '{"authorId": ${著者ID},"name":"${著者名}","birthday":"${誕生日(yyyyMMdd形式)}"}'
```

### 書籍の情報を登録

```
curl -X POST -H "Content-Type: application/json" \
  http://localhost:8080/api/book  -d '{"title": "${書籍タイトル}", "price": ${価格}, "publish": "${出版状況("0"(未出版) or "1"(出版済))}", "authorIds": ${著者IDの配列}}'
```

### 書籍の情報を更新

```
curl -X PATCH -H "Content-Type: application/json" \
  http://localhost:8080/api/book -d '{"bookId": ${書籍ID},"title":"${書籍タイトル}","price": ${価格}, "publish": "${出版状況("0"(未出版) or "1"(出版済))}", "authorIds": ${著者IDの配列}}'
```

## テストコード実行

```
./gradlew test
```