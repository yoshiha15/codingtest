package com.example.codingtest.common.exception

/**
 * InvalidPublishUpdatedException
 * 書籍.出版状況を出版済み("1")から未出版("0")に更新しようとした場合に発生させる
 */
class InvalidPublishUpdatedException: RuntimeException() {}