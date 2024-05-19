DROP TABLE unit;

CREATE TABLE IF NOT EXISTS  unit (
                       uuid UUID NOT NULL DEFAULT gen_random_uuid(),
                       email STRING NOT NULL,
                       CONSTRAINT "primary" PRIMARY KEY (email ASC, uuid ASC),
                       UNIQUE (email)
);
