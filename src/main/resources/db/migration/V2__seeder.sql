-- Inserting roles
INSERT INTO role (id, code, name, module, created_at, modified_at, deleted_at)
VALUES
    (1, 'ADMIN', 'Administrator', 'User Management', '2024-10-20 08:00:00', NULL, NULL);

-- Inserting permissions
INSERT INTO permission (code, name, module, created_at, modified_at, deleted_at)
VALUES
    ('view_category', 'View-Category', 'Category', '2024-10-20 09:00:00', NULL, NULL),
    ('create_category', 'Create-Category', 'Category', '2024-10-20 09:05:00', NULL, NULL),
    ('edit_category', 'Edit-Category', 'Category', '2024-10-20 09:10:00', NULL, NULL),
    ('delete_category', 'Delete-Category', 'Category', '2024-10-20 09:15:00', NULL, NULL),
    ('view_file', 'View-File', 'FileMedia', '2024-10-20 09:20:00', NULL, NULL),
    ('upload_file', 'Upload-File', 'FileMedia', '2024-10-20 09:25:00', NULL, NULL),
    ('edit_file', 'Edit-File', 'FileMedia', '2024-10-20 09:30:00', NULL, NULL),
    ('delete_file', 'Delete-File', 'FileMedia', '2024-10-20 09:35:00', NULL, NULL),
    ('view_post', 'View-Post', 'Post', '2024-10-20 09:40:00', NULL, NULL),
    ( 'create_post', 'Create-Post', 'Post', '2024-10-20 09:45:00', NULL, NULL),
    ( 'edit_post', 'Edit-Post', 'Post', '2024-10-20 09:50:00', NULL, NULL),
    ( 'delete_post', 'Delete-Post', 'Post', '2024-10-20 09:55:00', NULL, NULL),
    ( 'view_role', 'View-Role', 'Role', '2024-10-20 10:00:00', NULL, NULL),
    ( 'create_role', 'Create-Role', 'Role', '2024-10-20 10:05:00', NULL, NULL),
    ( 'edit_role', 'Edit-Role', 'Role', '2024-10-20 10:10:00', NULL, NULL),
    ( 'set_permission', 'Set-Permission', 'Role', '2024-10-20 10:10:00', NULL, NULL),
    ( 'view_permission', 'View-Permission', 'Role', '2024-10-20 10:10:00', NULL, NULL),
    ( 'delete_role', 'Delete-Role', 'Role', '2024-10-20 10:15:00', NULL, NULL),
    ( 'view_topic', 'View-Topic', 'Topic', '2024-10-20 10:20:00', NULL, NULL),
    ( 'create_topic', 'Create-Topic', 'Topic', '2024-10-20 10:25:00', NULL, NULL),
    ( 'edit_topic', 'Edit-Topic', 'Topic', '2024-10-20 10:30:00', NULL, NULL),
    ( 'delete_topic', 'Delete-Topic', 'Topic', '2024-10-20 10:35:00', NULL, NULL),
    ('view_user', 'View-User', 'User', '2024-10-20 10:20:00', NULL, NULL),
    ('create_user', 'Create-User', 'User', '2024-10-20 10:25:00', NULL, NULL),
    ('edit_user', 'Edit-User', 'User', '2024-10-20 10:30:00', NULL, NULL),
    ('delete_user', 'Delete-User', 'User', '2024-10-20 10:35:00', NULL, NULL);


-- Inserting users
INSERT INTO user (id, photo, firstname, lastname, username, password, email, phone, address, gender, dob,
                  is_verification, role_id, created_at, modified_at, deleted_at)
VALUES
#     password : Admin@168
    (1, 'https://example.com/images/user_photo_01.jpg', 'admin', 'admin', 'admin', '$2a$10$z1a.ODGFkXKi5m4MhbIA.uKACxWkyy2xMMKn2FzUhTfEZ2bAIv1yq', 'admin@gmail.com', '012345678', '123 Main St, City', 'Male', '1990-05-15', TRUE, 1, '2024-10-20 10:15:00', NULL, NULL);
