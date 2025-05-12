-- liquibase formatted sql
-- changeset pavel-fomchenkov:1
CREATE TABLE IF NOT EXISTS public.users
(
     id bigint PRIMARY KEY,
     username character varying(255) NOT NULL
);
CREATE SEQUENCE user_id_seq
    AS BIGINT
    START WITH 1;

CREATE TABLE IF NOT EXISTS public.subscriptions
(
     id bigint PRIMARY KEY,
     title character varying(255) NOT NULL,
     description character varying(255) NOT NULL
);
CREATE SEQUENCE subscription_id_seq
    AS BIGINT
    START WITH 1;

CREATE TABLE user_subscriptions (
    user_id BIGINT NOT NULL,
    subscription_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, subscription_id),
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (subscription_id) REFERENCES subscriptions(id)
);
