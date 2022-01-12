/*CREATE TABLE snowmobiles
(
    id           BIGSERIAL PRIMARY KEY,
    vendors      TEXT        NOT NULL,
    model_year   INT         NOT NULL                           DEFAULT 2020,
    price        INT         NOT NULL CHECK ( price >= 0 ),
    qty          INT         NOT NULL CHECK ( qty >= 0 )        DEFAULT 0,
    colors       TEXT[]      NOT NULL                           DEFAULT '{"black(черный)"}',
    track_length INT[]       NOT NULL                           DEFAULT '{154}',
    horse_power  INT         NOT NULL CHECK ( horse_power > 0 ) DEFAULT 165,
    removed      BOOL        NOT NULL                           DEFAULT FALSE,
    created      timestamptz NOT NULL                           DEFAULT CURRENT_TIMESTAMP
);*/

CREATE TABLE snowmobiles
(
    id                 BIGSERIAL PRIMARY KEY,
    vendors            TEXT        NOT NULL,
    model_year         INT         NOT NULL CHECK ( model_year >= 2019 ),
    price              INT         NOT NULL CHECK ( price > 0 ),
    qty_of_day         INT         NOT NULL CHECK (qty_of_day > 0 )         DEFAULT 1,
    qty_of_snowmobiles INT         NOT NULL CHECK (qty_of_snowmobiles > 0 ) DEFAULT 1,
    colors             TEXT[]      NOT NULL                                 DEFAULT '{"black(черный)"}',
    track_parameters   INT[]       NOT NULL                                 DEFAULT '{154,16,3}',
    horse_power        INT         NOT NULL CHECK ( horse_power > 0 )       DEFAULT 165,
    special_price      INT         NOT NULL CHECK ( special_price > 0 )     DEFAULT 17000,
    premium_price      INT         NOT NULL CHECK ( premium_price > 0 )     DEFAULT 15000,
    removed            BOOL        NOT NULL                                 DEFAULT FALSE,
    created            timestamptz NOT NULL                                 DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE rents
(
    id                          BIGSERIAL PRIMARY KEY,
    id_snowmobile               BIGINT REFERENCES snowmobiles,
    vendors TEXT NOT NULL ,
    qty_of_snowmobiles_for_rent INT  NOT NULL CHECK ( qty_of_snowmobiles_for_rent > 0 ) DEFAULT 1,
    qty_of_day_for_rent         INT  NOT NULL CHECK ( qty_of_day_for_rent > 0 )         DEFAULT 1,
    client_full_name            TEXT NOT NULL,
    phone_number                BIGINT  NOT NULL,
    total_price INT NOT NULL
);
