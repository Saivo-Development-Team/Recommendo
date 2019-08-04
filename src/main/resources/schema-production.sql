create schema public;

comment on schema public is 'standard public schema';

alter schema public owner to saivo_admin;

create table if not exists activities
(
    id          varchar(255) not null
        constraint activities_pkey
            primary key,
    description varchar(255),
    location    varchar(255),
    title       varchar(255)
);

alter table activities
    owner to saivo_admin;

create table if not exists preferences
(
    id    varchar(255) not null
        constraint preferences_pkey
            primary key,
    likes varchar(255)
);

alter table preferences
    owner to saivo_admin;

create table if not exists ratings
(
    id varchar(255) not null
        constraint ratings_pkey
            primary key
);

alter table ratings
    owner to saivo_admin;

create table if not exists recommendations
(
    id          varchar(255) not null
        constraint recommendations_pkey
            primary key,
    activity_id varchar(255)
        constraint const_recommendation_activities
            references activities
);

alter table recommendations
    owner to saivo_admin;

create table if not exists roles
(
    id   varchar(255) not null
        constraint roles_pkey
            primary key,
    role varchar(255)
);

alter table roles
    owner to saivo_admin;

create table if not exists users
(
    id                  varchar(255) not null
        constraint users_pkey
            primary key,
    password            varchar(255),
    username            varchar(255) not null
        constraint const_user_username
            unique,
    account_expired     boolean      not null,
    account_locked      boolean      not null,
    credentials_expired boolean      not null,
    email               varchar(255),
    enabled             boolean      not null,
    firstname           varchar(255),
    lastname            varchar(255)
);

alter table users
    owner to saivo_admin;

create table if not exists users_preferences
(
    user_id        varchar(255) not null
        constraint const_users_preferences
            references users,
    preferences_id varchar(255) not null
        constraint const_preferences_id
            unique
        constraint con_preferences_preferences
            references preferences,
    constraint users_preferences_pkey
        primary key (user_id, preferences_id)
);

alter table users_preferences
    owner to saivo_admin;

create table if not exists users_recommendations
(
    user_id            varchar(255) not null
        constraint con_user_id
            references users,
    recommendations_id varchar(255) not null
        constraint const_recommendations_id
            unique
        constraint const_users_recommendations
            references recommendations,
    constraint users_recommendations_pkey
        primary key (user_id, recommendations_id)
);

alter table users_recommendations
    owner to saivo_admin;

create table if not exists users_roles
(
    user_id  varchar(255) not null
        constraint const_user_id
            references users,
    roles_id varchar(255) not null
        constraint const_roles_id
            unique
        constraint const_users_roles
            references roles,
    constraint users_roles_pkey
        primary key (user_id, roles_id)
);

alter table users_roles
    owner to saivo_admin;

create table if not exists oauth_client_details
(
    client_id               varchar(256) not null
        constraint oauth_client_details_pkey
            primary key,
    resource_ids            varchar(256),
    client_secret           varchar(256),
    scope                   varchar(256),
    authorized_grant_types  varchar(256),
    web_server_redirect_uri varchar(256),
    authorities             varchar(256),
    access_token_validity   integer,
    refresh_token_validity  integer,
    additional_information  varchar(4096),
    autoapprove             varchar(256)
);

alter table oauth_client_details
    owner to saivo_admin;

create table if not exists oauth_client_token
(
    token_id          varchar(256),
    token             bytea,
    authentication_id varchar(256) not null
        constraint oauth_client_token_pkey
            primary key,
    user_name         varchar(256),
    client_id         varchar(256)
);

alter table oauth_client_token
    owner to saivo_admin;

create table if not exists oauth_access_token
(
    token_id          varchar(256),
    token             bytea,
    authentication_id varchar(256) not null
        constraint oauth_access_token_pkey
            primary key,
    user_name         varchar(256),
    client_id         varchar(256),
    authentication    bytea,
    refresh_token     varchar(256)
);

alter table oauth_access_token
    owner to saivo_admin;

create table if not exists oauth_refresh_token
(
    token_id       varchar(256),
    token          bytea,
    authentication bytea
);

alter table oauth_refresh_token
    owner to saivo_admin;

create table if not exists oauth_code
(
    code           varchar(256),
    authentication bytea
);

alter table oauth_code
    owner to saivo_admin;

create table if not exists oauth_approvals
(
    userid         varchar(256),
    clientid       varchar(256),
    scope          varchar(256),
    status         varchar(10),
    expiresat      timestamp,
    lastmodifiedat timestamp
);

alter table oauth_approvals
    owner to saivo_admin;

