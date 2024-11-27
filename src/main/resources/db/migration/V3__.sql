ALTER TABLE posts DROP FOREIGN KEY fk_posts_on_media;
ALTER TABLE posts DROP INDEX uc_posts_media;
ALTER TABLE posts
    ADD CONSTRAINT fk_posts_media
        FOREIGN KEY (media_id)
            REFERENCES file_medias(id)
            ON DELETE CASCADE; -- Optional: adjust based on your needs