ALTER TABLE only_admin.posts
    ADD `description` VARCHAR(255) NULL;

ALTER TABLE only_admin.posts
    DROP COLUMN post_desc;

ALTER TABLE only_admin.category
    DROP COLUMN thumbnail;