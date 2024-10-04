CREATE TABLE password_reset_tokens
(
    id            BIGINT AUTO_INCREMENT NOT NULL,
    token  VARCHAR(255),
    expiry_time    TIME,
    created_at  datetime NOT NULL,
    modified_at datetime NULL,
    deleted_at  datetime NULL,
    user_id       BIGINT NULL,
    CONSTRAINT pk_password_reset_tokens PRIMARY KEY (id),
    CONSTRAINT FK_USER_PASSWORD_RESET_TOKENS FOREIGN KEY (user_id) REFERENCES user (id)
);