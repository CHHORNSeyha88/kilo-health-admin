CREATE TABLE category
(
    id          BIGINT AUTO_INCREMENT NOT NULL,
    name        VARCHAR(255) NULL,
    media_id    BIGINT NULL,
    user_id     BIGINT NULL,
    created_at  datetime NOT NULL,
    modified_at datetime NULL,
    deleted_at  datetime NULL,
    CONSTRAINT pk_category PRIMARY KEY (id)
);

CREATE TABLE file_medias
(
    id          BIGINT AUTO_INCREMENT NOT NULL,
    file_name   VARCHAR(255) NOT NULL,
    file_type   VARCHAR(255) NOT NULL,
    file_path   VARCHAR(255) NOT NULL,
    file_size   BIGINT       NOT NULL,
    created_at  datetime     NOT NULL,
    modified_at datetime NULL,
    deleted_at  datetime NULL,
    CONSTRAINT pk_file_medias PRIMARY KEY (id)
);

CREATE TABLE password_reset_tokens
(
    id          BIGINT AUTO_INCREMENT NOT NULL,
    token       VARCHAR(255) NULL,
    expiry_time time NULL,
    user_id     BIGINT NULL,
    created_at  datetime NOT NULL,
    modified_at datetime NULL,
    deleted_at  datetime NULL,
    CONSTRAINT pk_password_reset_tokens PRIMARY KEY (id)
);

CREATE TABLE permission
(
    id          BIGINT AUTO_INCREMENT NOT NULL,
    code        VARCHAR(255) NULL,
    name        VARCHAR(255) NULL,
    module      VARCHAR(255) NULL,
    created_at  datetime NOT NULL,
    modified_at datetime NULL,
    deleted_at  datetime NULL,
    CONSTRAINT pk_permission PRIMARY KEY (id)
);

CREATE TABLE post_view
(
    id      BIGINT AUTO_INCREMENT NOT NULL,
    user_id BIGINT NULL,
    post_id BIGINT NULL,
    view_at datetime NOT NULL,
    CONSTRAINT pk_post_view PRIMARY KEY (id)
);

CREATE TABLE posts
(
    id            BIGINT AUTO_INCREMENT NOT NULL,
    title         VARCHAR(255) NULL,
    `description` VARCHAR(255) NULL,
    status        BIT(1) NULL,
    media_id      BIGINT NULL,
    public_at     datetime NULL,
    user_id       BIGINT NULL,
    top_id        BIGINT NULL,
    category_id   BIGINT NULL,
    created_at    datetime NOT NULL,
    modified_at   datetime NULL,
    deleted_at    datetime NULL,

    CONSTRAINT pk_posts PRIMARY KEY (id)
);

CREATE TABLE `role`
(
    id          BIGINT AUTO_INCREMENT NOT NULL,
    code        VARCHAR(255) NULL,
    name        VARCHAR(255) NULL,
    module      VARCHAR(255) NULL,
    created_at  datetime NOT NULL,
    modified_at datetime NULL,
    deleted_at  datetime NULL,
    CONSTRAINT pk_role PRIMARY KEY (id)
);

CREATE TABLE role_has_permission
(
    permission_id BIGINT NOT NULL,
    role_id       BIGINT NOT NULL,
    CONSTRAINT pk_role_has_permission PRIMARY KEY (permission_id, role_id)
);

CREATE TABLE topics
(
    id          BIGINT AUTO_INCREMENT NOT NULL,
    name        VARCHAR(255) NULL,
    media_id    BIGINT NULL,
    user_id     BIGINT NULL,
    category_id BIGINT NULL,
    created_at  datetime NOT NULL,
    modified_at datetime NULL,
    deleted_at  datetime NULL,
    CONSTRAINT pk_topics PRIMARY KEY (id)
);

CREATE TABLE user
(
    id              BIGINT AUTO_INCREMENT NOT NULL,
    photo           VARCHAR(255) NULL,
    firstname       VARCHAR(255) NULL,
    lastname        VARCHAR(255) NULL,
    username        VARCHAR(255) NULL,
    password        VARCHAR(255) NULL,
    email           VARCHAR(255) NULL,
    phone           VARCHAR(255) NULL,
    address         VARCHAR(255) NULL,
    gender          VARCHAR(255) NULL,
    dob             date NULL,
    is_verification BIT(1) NULL,
    role_id         BIGINT NULL,
    created_at      datetime NOT NULL,
    modified_at     datetime NULL,
    deleted_at      datetime NULL,
    CONSTRAINT pk_user PRIMARY KEY (id)
);

CREATE TABLE user_verifications
(
    id            BIGINT AUTO_INCREMENT NOT NULL,
    verified_code VARCHAR(255) NULL,
    expiry_time   time NULL,
    user_id       BIGINT NULL,
    created_at    datetime NOT NULL,
    modified_at   datetime NULL,
    deleted_at    datetime NULL,
    CONSTRAINT pk_user_verifications PRIMARY KEY (id)
);

ALTER TABLE category
    ADD CONSTRAINT uc_category_media UNIQUE (media_id);

ALTER TABLE posts
    ADD CONSTRAINT uc_posts_media UNIQUE (media_id);

ALTER TABLE topics
    ADD CONSTRAINT uc_topics_media UNIQUE (media_id);

ALTER TABLE category
    ADD CONSTRAINT FK_CATEGORY_ON_MEDIA FOREIGN KEY (media_id) REFERENCES file_medias (id);

ALTER TABLE category
    ADD CONSTRAINT FK_CATEGORY_ON_USER FOREIGN KEY (user_id) REFERENCES user (id);

ALTER TABLE password_reset_tokens
    ADD CONSTRAINT FK_PASSWORD_RESET_TOKENS_ON_USER FOREIGN KEY (user_id) REFERENCES user (id);

ALTER TABLE posts
    ADD CONSTRAINT FK_POSTS_ON_CATEGORY FOREIGN KEY (category_id) REFERENCES category (id);

ALTER TABLE posts
    ADD CONSTRAINT FK_POSTS_ON_MEDIA FOREIGN KEY (media_id) REFERENCES file_medias (id);

ALTER TABLE posts
    ADD CONSTRAINT FK_POSTS_ON_TOP FOREIGN KEY (top_id) REFERENCES topics (id);

ALTER TABLE posts
    ADD CONSTRAINT FK_POSTS_ON_USER FOREIGN KEY (user_id) REFERENCES user (id);

ALTER TABLE post_view
    ADD CONSTRAINT FK_POST_VIEW_ON_POST FOREIGN KEY (post_id) REFERENCES posts (id);

ALTER TABLE post_view
    ADD CONSTRAINT FK_POST_VIEW_ON_USER FOREIGN KEY (user_id) REFERENCES user (id);

ALTER TABLE topics
    ADD CONSTRAINT FK_TOPICS_ON_CATEGORY FOREIGN KEY (category_id) REFERENCES category (id);

ALTER TABLE topics
    ADD CONSTRAINT FK_TOPICS_ON_MEDIA FOREIGN KEY (media_id) REFERENCES file_medias (id);

ALTER TABLE topics
    ADD CONSTRAINT FK_TOPICS_ON_USER FOREIGN KEY (user_id) REFERENCES user (id);

ALTER TABLE user
    ADD CONSTRAINT FK_USER_ON_ROLE FOREIGN KEY (role_id) REFERENCES `role` (id);

ALTER TABLE user_verifications
    ADD CONSTRAINT FK_USER_VERIFICATIONS_ON_USER FOREIGN KEY (user_id) REFERENCES user (id);

ALTER TABLE role_has_permission
    ADD CONSTRAINT fk_rolhasper_on_permission_entity FOREIGN KEY (permission_id) REFERENCES permission (id);

ALTER TABLE role_has_permission
    ADD CONSTRAINT fk_rolhasper_on_role_entity FOREIGN KEY (role_id) REFERENCES `role` (id);