ALTER TABLE only_admin.category
    DROP COLUMN media_id;

ALTER TABLE only_admin.category
    ADD media_id BIGINT NULL;

ALTER TABLE only_admin.posts
    DROP COLUMN media_id;

ALTER TABLE only_admin.posts
    ADD media_id BIGINT NULL;

ALTER TABLE only_admin.topics
    DROP COLUMN media_id;

ALTER TABLE only_admin.topics
    ADD media_id BIGINT NULL;