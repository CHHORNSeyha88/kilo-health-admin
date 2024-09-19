CREATE TABLE category
(
    id            BIGINT AUTO_INCREMENT NOT NULL,
    name          VARCHAR(255)          NULL,
    thumbnail     VARCHAR(255)          NULL,
    created_date  datetime              NOT NULL,
    modified_date datetime              NULL,
    deleted_date  datetime              NULL,
    CONSTRAINT pk_category PRIMARY KEY (id)
);

CREATE TABLE permission
(
    id            BIGINT AUTO_INCREMENT NOT NULL,
    code          VARCHAR(255)          NULL,
    name          VARCHAR(255)          NULL,
    module        VARCHAR(255)          NULL,
    created_date  datetime              NOT NULL,
    modified_date datetime              NULL,
    deleted_date  datetime              NULL,
    CONSTRAINT pk_permission PRIMARY KEY (id)
);

CREATE TABLE post_view
(
    post_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    CONSTRAINT pk_post_view PRIMARY KEY (post_id, user_id)
);

CREATE TABLE posts
(
    id            BIGINT AUTO_INCREMENT NOT NULL,
    title         VARCHAR(255)          NULL,
    post_desc     VARCHAR(255)          NULL,
    thumbnail     VARCHAR(255)          NULL,
    user_id       BIGINT                NULL,
    top_id        BIGINT                NULL,
    category_id   BIGINT                NULL,
    created_date  datetime              NOT NULL,
    modified_date datetime              NULL,
    deleted_date  datetime              NULL,
    CONSTRAINT pk_posts PRIMARY KEY (id)
);

CREATE TABLE `role`
(
    id            BIGINT AUTO_INCREMENT NOT NULL,
    code          VARCHAR(255)          NULL,
    name          VARCHAR(255)          NULL,
    module        VARCHAR(255)          NULL,
    created_date  datetime              NOT NULL,
    modified_date datetime              NULL,
    deleted_date  datetime              NULL,
    CONSTRAINT pk_role PRIMARY KEY (id)
);

CREATE TABLE role_has_permission
(
    permission_id BIGINT NOT NULL,
    role_id       BIGINT NOT NULL
);

CREATE TABLE topics
(
    id            BIGINT AUTO_INCREMENT NOT NULL,
    name          VARCHAR(255)          NULL,
    category_id   BIGINT                NULL,
    created_date  datetime              NOT NULL,
    modified_date datetime              NULL,
    deleted_date  datetime              NULL,
    CONSTRAINT pk_topics PRIMARY KEY (id)
);

CREATE TABLE user
(
    id            BIGINT AUTO_INCREMENT NOT NULL,
    photo         VARCHAR(255)          NULL,
    firstname     VARCHAR(255)          NULL,
    lastname      VARCHAR(255)          NULL,
    username      VARCHAR(255)          NULL,
    password      VARCHAR(255)          NULL,
    email         VARCHAR(255)          NULL,
    phone         VARCHAR(255)          NULL,
    address       VARCHAR(255)          NULL,
    gender        VARCHAR(255)          NULL,
    dob           datetime              NULL,
    role_id       BIGINT                NULL,
    created_date  datetime              NOT NULL,
    modified_date datetime              NULL,
    deleted_date  datetime              NULL,
    CONSTRAINT pk_user PRIMARY KEY (id)
);

ALTER TABLE posts
    ADD CONSTRAINT FK_POSTS_ON_CATEGORY FOREIGN KEY (category_id) REFERENCES category (id);

ALTER TABLE posts
    ADD CONSTRAINT FK_POSTS_ON_TOP FOREIGN KEY (top_id) REFERENCES topics (id);

ALTER TABLE posts
    ADD CONSTRAINT FK_POSTS_ON_USER FOREIGN KEY (user_id) REFERENCES user (id);

ALTER TABLE topics
    ADD CONSTRAINT FK_TOPICS_ON_CATEGORY FOREIGN KEY (category_id) REFERENCES category (id);

ALTER TABLE user
    ADD CONSTRAINT FK_USER_ON_ROLE FOREIGN KEY (role_id) REFERENCES `role` (id);

ALTER TABLE post_view
    ADD CONSTRAINT fk_post_view_on_post_entity FOREIGN KEY (post_id) REFERENCES posts (id);

ALTER TABLE post_view
    ADD CONSTRAINT fk_post_view_on_user_entity FOREIGN KEY (user_id) REFERENCES user (id);

ALTER TABLE role_has_permission
    ADD CONSTRAINT fk_rolhasper_on_permission_entity FOREIGN KEY (permission_id) REFERENCES permission (id);

ALTER TABLE role_has_permission
    ADD CONSTRAINT fk_rolhasper_on_role_entity FOREIGN KEY (role_id) REFERENCES `role` (id);



/** Create file_media Table **/

CREATE TABLE file_medias
(
    id            BIGINT AUTO_INCREMENT NOT NULL,
    file_name     VARCHAR(255) NULL,
    file_type     VARCHAR(255) NOT NULL,
    file_path     VARCHAR(255) NOT NULL,
    post_id       BIGINT NULL,
    file_size     BIGINT NOT NULL,
    upload_date   DATE NOT NULL
    CONSTRAINT PK_POST_ID PRIMARY KEY (id)
);

/** Add constraint into file_media table**/

ALTER TABLE file_medias
    ADD CONSTRAINT FK_FILE_MEDIA_ON_POST FOREIGN KEY (post_id) REFERENCES posts (id);

