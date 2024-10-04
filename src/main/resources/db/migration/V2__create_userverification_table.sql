CREATE TABLE user_verifications
(
    id            BIGINT AUTO_INCREMENT NOT NULL,
    verified_code  VARCHAR(255),
    expiry_time    TIME,
    created_at  datetime NOT NULL,
    modified_at datetime NULL,
    deleted_at  datetime NULL,
    user_id       BIGINT NULL,
    CONSTRAINT pk_user_verifications PRIMARY KEY (id),
    CONSTRAINT FK_USER_VERIFICATION_ON_USER FOREIGN KEY (user_id) REFERENCES user (id)
);