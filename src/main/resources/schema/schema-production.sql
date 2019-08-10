create schema public;

comment on schema public is 'standard public schema';

alter schema public owner to saivo_admin;

create table if not exists activities
(
    id varchar(255) not null
        constraint activities_pkey
            primary key,
    description varchar(255),
    location varchar(255),
    title varchar(255),
    category varchar(255)
);

alter table activities owner to saivo_admin;

create table if not exists preferences
(
    id varchar(255) not null
        constraint preferences_pkey
            primary key,
    likes varchar(255)
);

alter table preferences owner to saivo_admin;

create table if not exists recommendations
(
    id varchar(255) not null
        constraint recommendations_pkey
            primary key,
    activity_id varchar(255)
        constraint fkas1wb53u1fhdf8w6127aflipy
            references activities
);

alter table recommendations owner to saivo_admin;

create table if not exists roles
(
    id varchar(255) not null
        constraint roles_pkey
            primary key,
    role varchar(255)
);

alter table roles owner to saivo_admin;

create table if not exists users
(
    id varchar(255) not null
        constraint users_pkey
            primary key,
    account_expired boolean not null,
    account_locked boolean not null,
    credentials_expired boolean not null,
    email varchar(255),
    enabled boolean not null,
    firstname varchar(255),
    lastname varchar(255),
    password varchar(255) not null,
    username varchar(255) not null
        constraint uk_r43af9ap4edm43mmtq01oddj6
            unique
);

alter table users owner to saivo_admin;

create table if not exists ratings
(
    id varchar(255) not null
        constraint ratings_pkey
            primary key,
    comment varchar(255),
    stars integer not null,
    writen timestamp,
    user_id varchar(255)
        constraint fkb3354ee2xxvdrbyq9f42jdayd
            references users
);

alter table ratings owner to saivo_admin;

create table if not exists users_preferences
(
    user_id varchar(255) not null
        constraint fkc4iohl2foogcehrk8jof3bomm
            references users,
    preferences_id varchar(255) not null
        constraint uk_81ix5s1c8h5dojrgr4rfx2c44
            unique
        constraint fk9i0ds6j30chja7jc9x9np6yfk
            references preferences,
    constraint users_preferences_pkey
        primary key (user_id, preferences_id)
);

alter table users_preferences owner to saivo_admin;

create table if not exists users_recommendations
(
    user_id varchar(255) not null
        constraint fkkkomc5i49vxlaqsj464ffk16
            references users,
    recommendations_id varchar(255) not null
        constraint uk_edmj676g3feuj2trlcdmodg0w
            unique
        constraint fk46qot8kwpe50gftlf09i2d31w
            references recommendations,
    constraint users_recommendations_pkey
        primary key (user_id, recommendations_id)
);

alter table users_recommendations owner to saivo_admin;

create table if not exists users_roles
(
    user_id varchar(255) not null
        constraint fk2o0jvgh89lemvvo17cbqvdxaa
            references users,
    roles_id varchar(255) not null
        constraint uk_60loxav507l5mreo05v0im1lq
            unique
        constraint fka62j07k5mhgifpp955h37ponj
            references roles,
    constraint users_roles_pkey
        primary key (user_id, roles_id)
);

alter table users_roles owner to saivo_admin;

create table if not exists oauth_client_details
(
    client_id varchar(256) not null
        constraint oauth_client_details_pkey
            primary key,
    resource_ids varchar(256),
    client_secret varchar(256),
    scope varchar(256),
    authorized_grant_types varchar(256),
    web_server_redirect_uri varchar(256),
    authorities varchar(256),
    access_token_validity integer,
    refresh_token_validity integer,
    additional_information varchar(4096),
    autoapprove varchar(256)
);

alter table oauth_client_details owner to saivo_admin;

create table if not exists oauth_client_token
(
    token_id varchar(256),
    token bytea,
    authentication_id varchar(256) not null
        constraint oauth_client_token_pkey
            primary key,
    user_name varchar(256),
    client_id varchar(256)
);

alter table oauth_client_token owner to saivo_admin;

create table if not exists oauth_access_token
(
    token_id varchar(256),
    token bytea,
    authentication_id varchar(256) not null
        constraint oauth_access_token_pkey
            primary key,
    user_name varchar(256),
    client_id varchar(256),
    authentication bytea,
    refresh_token varchar(256)
);

alter table oauth_access_token owner to saivo_admin;

create table if not exists oauth_refresh_token
(
    token_id varchar(256),
    token bytea,
    authentication bytea
);

alter table oauth_refresh_token owner to saivo_admin;

create table if not exists oauth_code
(
    code varchar(256),
    authentication bytea
);

alter table oauth_code owner to saivo_admin;

create table if not exists oauth_approvals
(
    userid varchar(256),
    clientid varchar(256),
    scope varchar(256),
    status varchar(10),
    expiresat timestamp,
    lastmodifiedat timestamp
);

alter table oauth_approvals owner to saivo_admin;

create table if not exists activities_ratings
(
    activity_id varchar(255) not null
        constraint fki7k21ma0ma2w0dmcdh7n6w5ip
            references activities,
    ratings_id varchar(255) not null
        constraint uk_ekwqqs3o49jvxopxxp3culxhx
            unique
        constraint fknfwapp4x73wy98eou29p2vrlx
            references ratings,
    constraint activities_ratings_pkey
        primary key (activity_id, ratings_id)
);

alter table activities_ratings owner to saivo_admin;

