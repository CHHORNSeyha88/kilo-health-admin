CREATE TABLE file_tests
(
    id          BIGINT AUTO_INCREMENT NOT NULL,
    file_name   VARCHAR(255)          NOT NULL,
    file_type   VARCHAR(255)          NOT NULL,
    created_at  datetime              NOT NULL,
    modified_at datetime              NULL,
    deleted_at  datetime              NULL,
    CONSTRAINT pk_file_medias PRIMARY KEY (id)
);