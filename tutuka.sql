#database creation schema
create schema tutuka;
use tutuka;
create table audit_trail
(
    id           varchar(255) not null
        primary key,
    created      datetime     null,
    deleted      datetime     null,
    file_name_1  varchar(255) null,
    file_name_2  varchar(255) null,
    file_count_1 int          null,
    file_count_2 int          null,
    similarity   int          not null,
    updated      datetime     null
);

create table transaction
(
    id                      varchar(255) not null
        primary key,
    created                 datetime     null,
    deleted                 datetime     null,
    file_name               varchar(255) null,
    profile_name            varchar(255) null,
    transaction_amount      int          null,
    transaction_date        datetime     null,
    transaction_description varchar(255) null,
    transaction_id          varchar(255) null,
    transaction_narrative   varchar(255) null,
    transaction_type        int          null,
    updated                 datetime     null,
    wallet_reference        varchar(255) null,
    audit_trail_id          varchar(255) null
);

create index FK9pjffjkwnxjvvdfnd4nbs0ant
    on transaction (audit_trail_id);

