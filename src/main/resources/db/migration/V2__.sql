ALTER TABLE only_admin.posts
    DROP FOREIGN KEY FK_POSTS_ON_FILEMEDIA;

ALTER TABLE only_admin.posts
    ADD media_id BIGINT NULL;

ALTER TABLE only_admin.posts
    ADD CONSTRAINT uc_posts_media UNIQUE (media_id);

ALTER TABLE only_admin.posts
    ADD CONSTRAINT FK_POSTS_ON_MEDIA FOREIGN KEY (media_id) REFERENCES only_admin.file_medias (id);

ALTER TABLE only_admin.posts
    DROP COLUMN file_media_id;