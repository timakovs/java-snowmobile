INSERT INTO snowmobiles (vendors, model_year, price,special_price,premium_price, qty_of_day, qty_of_snowmobiles, colors, track_parameters,
                         horse_power)
VALUES
       ('SKI-DOO SUMMIT X', 2021, 25000,23000,21000, 3, 3, '{"black(черный)","blue(синий)"}', '{154,16,3}', 165),
       ('SKI-DOO SUMMIT EXPERT', 2020, 23000,21000,19000, 3, 5, '{"white(белый)","red(красный)"}', '{165,16,3}', 165),
       ('SKI-DOO FREERIDE', 2021, 20000,18000,16000, 3, 3, '{"red(красный)","gray(серый)", "black(черный)"}', '{154,16,3}',
        165),
       ('POLARIS AXIS', 2019, 23000,21000,19000, 3, 4, '{"black(черный)","gray(серый)"}', '{155,17,3}', 154),
       ('POLARIS PRO RMK', 2021, 25000,23000,21000, 3, 4, '{"black(черный)","yellow(желтый)"}', '{174,16,3}', 155),
       ('LYNX BOONDOCKER', 2021, 20000,18000,16000, 3, 3, '{"black(черный)","blue(синий)","pale blue(голубой)"}', DEFAULT,
        150)
;

INSERT INTO rents (id_snowmobile,vendors, qty_of_snowmobiles_for_rent, qty_of_day_for_rent, client_full_name, phone_number, total_price)
VALUES
       (1,'SKI-DOO SUMMIT X', 3, 5, 'Иванов Иван Иванович', 89175555555,23000*3*5),
       (2, 'SKI-DOO SUMMIT EXPERT',2, 3, 'Петров Петр Петрович', 89174444444,23000*2*3),
       (4,'SKI-DOO FREERIDE' ,3, 5, 'Васильев Василий Васильевич', 89173333333,1800*3*5),
       (6,'POLARIS AXIS' ,3, 5, 'Александров Александр Александрович', 89172222222,21000*3*5)
    ;