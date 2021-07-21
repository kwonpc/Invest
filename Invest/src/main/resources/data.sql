INSERT INTO `product` (`product_id`, `title`, `total_amount`, `started_at`, `finished_at`) VALUES
    (1, '개인신용 포트폴리오', 1000000, PARSEDATETIME('2021-07-01 00:00:00','yyyy-MM-dd HH:mm:ss'), PARSEDATETIME('2021-07-30 00:00:00','yyyy-MM-dd HH:mm:ss')),  
    (2, '부동산 포트폴리오', 5000000, PARSEDATETIME('2021-07-02 00:00:00','yyyy-MM-dd HH:mm:ss'), PARSEDATETIME('2021-07-31 00:00:00','yyyy-MM-dd HH:mm:ss'));

INSERT INTO `invest` (`invest_id`, `user_id`, `product_id`, `amount`, `reg_dtm`) VALUES
    (1, 1, 1, 100000, PARSEDATETIME('2021-07-14 00:00:00','yyyy-MM-dd HH:mm:ss'));
