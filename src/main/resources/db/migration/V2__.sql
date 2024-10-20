-- Inserting roles
INSERT INTO role (id, code, name, module, created_at, modified_at, deleted_at)
VALUES
    (1, 'ADMIN', 'Administrator', 'User Management', '2024-10-20 08:00:00', NULL, NULL),
    (2, 'MOD', 'Moderator', 'Content Management', '2024-10-20 08:05:00', NULL, NULL),
    (3, 'USER', 'User', 'User Management', '2024-10-20 08:10:00', NULL, NULL),
    (4, 'SUPPORT', 'Support', 'Customer Support', '2024-10-20 08:15:00', NULL, NULL),
    (5, 'DEV', 'Developer', 'Development', '2024-10-20 08:20:00', NULL, NULL);

-- Inserting permissions
INSERT INTO permission (id, code, name, module, created_at, modified_at, deleted_at)
VALUES
    (1, 'CATEGORY_VIEW', 'View Category', 'CategoryService', '2024-10-20 09:00:00', NULL, NULL),
    (2, 'CATEGORY_CREATE', 'Create Category', 'CategoryService', '2024-10-20 09:05:00', NULL, NULL),
    (3, 'CATEGORY_EDIT', 'Edit Category', 'CategoryService', '2024-10-20 09:10:00', NULL, NULL),
    (4, 'CATEGORY_DELETE', 'Delete Category', 'CategoryService', '2024-10-20 09:15:00', NULL, NULL),
    (5, 'FILE_MEDIA_VIEW', 'View File Media', 'FileMediaService', '2024-10-20 09:20:00', NULL, NULL),
    (6, 'FILE_MEDIA_UPLOAD', 'Upload File Media', 'FileMediaService', '2024-10-20 09:25:00', NULL, NULL),
    (7, 'FILE_MEDIA_EDIT', 'Edit File Media', 'FileMediaService', '2024-10-20 09:30:00', NULL, NULL),
    (8, 'FILE_MEDIA_DELETE', 'Delete File Media', 'FileMediaService', '2024-10-20 09:35:00', NULL, NULL),
    (9, 'POST_VIEW', 'View Post', 'PostService', '2024-10-20 09:40:00', NULL, NULL),
    (10, 'POST_CREATE', 'Create Post', 'PostService', '2024-10-20 09:45:00', NULL, NULL),
    (11, 'POST_EDIT', 'Edit Post', 'PostService', '2024-10-20 09:50:00', NULL, NULL),
    (12, 'POST_DELETE', 'Delete Post', 'PostService', '2024-10-20 09:55:00', NULL, NULL),
    (13, 'ROLE_VIEW', 'View Role', 'RoleServices', '2024-10-20 10:00:00', NULL, NULL),
    (14, 'ROLE_CREATE', 'Create Role', 'RoleServices', '2024-10-20 10:05:00', NULL, NULL),
    (15, 'ROLE_EDIT', 'Edit Role', 'RoleServices', '2024-10-20 10:10:00', NULL, NULL),
    (16, 'ROLE_DELETE', 'Delete Role', 'RoleServices', '2024-10-20 10:15:00', NULL, NULL),
    (17, 'TOPIC_VIEW', 'View Topic', 'TopicService', '2024-10-20 10:20:00', NULL, NULL),
    (18, 'TOPIC_CREATE', 'Create Topic', 'TopicService', '2024-10-20 10:25:00', NULL, NULL),
    (19, 'TOPIC_EDIT', 'Edit Topic', 'TopicService', '2024-10-20 10:30:00', NULL, NULL),
    (20, 'TOPIC_DELETE', 'Delete Topic', 'TopicService', '2024-10-20 10:35:00', NULL, NULL);

-- Inserting role permissions
INSERT INTO role_has_permission (permission_id, role_id)
VALUES
-- Administrator permissions
(1, 1),  (2, 1),  (3, 1),  (4, 1),  (5, 1),  (6, 1),  (9, 1),  (10, 1),  (13, 1),  (14, 1),
-- Moderator permissions
(1, 2),  (9, 2),  (10, 2),  (11, 2),  (12, 2),  (17, 2),  (18, 2),
-- User permissions
(1, 3),  (9, 3),  (17, 3),  (19, 3),
-- Support role permissions
(1, 4),  (5, 4),  (9, 4),  (17, 4);

-- Inserting users
INSERT INTO user (id, photo, firstname, lastname, username, password, email, phone, address, gender, dob,
                  is_verification, role_id, created_at, modified_at, deleted_at)
VALUES
    (1, 'https://example.com/images/user_photo_01.jpg', 'John', 'Doe', 'johndoe', 'hashedpassword1', 'john.doe@example.com', '1234567890', '123 Main St, City', 'Male', '1990-05-15', TRUE, 1, '2024-10-20 10:15:00', NULL, NULL),
    (2, 'https://example.com/images/user_photo_02.jpg', 'Jane', 'Smith', 'janesmith', 'hashedpassword2', 'jane.smith@example.com', '1234567891', '456 Maple St, City', 'Female', '1992-07-25', TRUE, 2, '2024-10-20 10:45:00', NULL, NULL),
    (3, 'https://example.com/images/user_photo_03.jpg', 'Emily', 'Johnson', 'emilyj', 'hashedpassword3', 'emily.j@example.com', '1234567892', '789 Oak St, City', 'Female', '1988-12-11', FALSE, 3, '2024-10-20 11:15:00', NULL, NULL),
    (4, 'https://example.com/images/user_photo_04.jpg', 'Michael', 'Brown', 'mikeb', 'hashedpassword4', 'michael.b@example.com', '1234567893', '321 Pine St, City', 'Male', '1985-03-30', TRUE, 2, '2024-10-20 11:45:00', NULL, NULL),
    (5, 'https://example.com/images/user_photo_05.jpg', 'Laura', 'Wilson', 'lauraw', 'hashedpassword5', 'laura.w@example.com', '1234567894', '654 Cedar St, City', 'Female', '1994-09-10', TRUE, 1, '2024-10-20 12:15:00', NULL, NULL);

-- Inserting file medias
INSERT INTO file_medias (id, file_name, file_type, file_url, file_size, created_at, modified_at, deleted_at)
VALUES
    (1, 'nutrition_image_01.jpg', 'image/jpeg', 'https://example.com/media/nutrition_image_01.jpg', 500, '2024-10-20 12:00:00', NULL, NULL),
    (2, 'fitness_image_01.jpg', 'image/jpeg', 'https://example.com/media/fitness_image_01.jpg', 400, '2024-10-20 12:05:00', NULL, NULL),
    (3, 'mental_health_image_01.jpg', 'image/jpeg', 'https://example.com/media/mental_health_image_01.jpg', 450, '2024-10-20 12:10:00', NULL, NULL),
    (4, 'wellness_image_01.jpg', 'image/jpeg', 'https://example.com/media/wellness_image_01.jpg', 550, '2024-10-20 12:15:00', NULL, NULL),
    (5, 'medical_advice_image_01.jpg', 'image/jpeg', 'https://example.com/media/medical_advice_image_01.jpg', 300, '2024-10-20 12:20:00', NULL, NULL),
    (6, 'nutrition_image_02.jpg', 'image/jpeg', 'https://example.com/media/nutrition_image_02.jpg', 500, '2024-10-20 12:25:00', NULL, NULL),
    (7, 'fitness_image_02.jpg', 'image/jpeg', 'https://example.com/media/fitness_image_02.jpg', 400, '2024-10-20 12:30:00', NULL, NULL),
    (8, 'mental_health_image_02.jpg', 'image/jpeg', 'https://example.com/media/mental_health_image_02.jpg', 450, '2024-10-20 12:35:00', NULL, NULL),
    (9, 'wellness_image_02.jpg', 'image/jpeg', 'https://example.com/media/wellness_image_02.jpg', 550, '2024-10-20 12:40:00', NULL, NULL),
    (10, 'medical_advice_image_02.jpg', 'image/jpeg', 'https://example.com/media/medical_advice_image_02.jpg', 300, '2024-10-20 12:45:00', NULL, NULL),
    (11, 'nutrition_image_03.jpg', 'image/jpeg', 'https://example.com/media/nutrition_image_03.jpg', 520, '2024-10-20 12:50:00', NULL, NULL),
    (12, 'fitness_image_03.jpg', 'image/jpeg', 'https://example.com/media/fitness_image_03.jpg', 450, '2024-10-20 12:55:00', NULL, NULL),
    (13, 'mental_health_image_03.jpg', 'image/jpeg', 'https://example.com/media/mental_health_image_03.jpg', 470, '2024-10-20 13:00:00', NULL, NULL),
    (14, 'wellness_image_03.jpg', 'image/jpeg', 'https://example.com/media/wellness_image_03.jpg', 600, '2024-10-20 13:05:00', NULL, NULL),
    (15, 'medical_advice_image_03.jpg', 'image/jpeg', 'https://example.com/media/medical_advice_image_03.jpg', 620, '2024-10-20 13:10:00', NULL, NULL),
    (16, 'nutrition_image_04.jpg', 'image/jpeg', 'https://example.com/media/nutrition_image_04.jpg', 500, '2024-10-20 13:15:00', NULL, NULL),
    (17, 'fitness_image_04.jpg', 'image/jpeg', 'https://example.com/media/fitness_image_04.jpg', 400, '2024-10-20 13:20:00', NULL, NULL),
    (18, 'mental_health_image_04.jpg', 'image/jpeg', 'https://example.com/media/mental_health_image_04.jpg', 450, '2024-10-20 13:25:00', NULL, NULL),
    (19, 'wellness_image_04.jpg', 'image/jpeg', 'https://example.com/media/wellness_image_04.jpg', 550, '2024-10-20 13:30:00', NULL, NULL),
    (20, 'medical_advice_image_04.jpg', 'image/jpeg', 'https://example.com/media/medical_advice_image_04.jpg', 300, '2024-10-20 13:35:00', NULL, NULL),
    (21, 'nutrition_image_05.jpg', 'image/jpeg', 'https://example.com/media/nutrition_image_05.jpg', 520, '2024-10-20 13:40:00', NULL, NULL),
    (22, 'fitness_image_05.jpg', 'image/jpeg', 'https://example.com/media/fitness_image_05.jpg', 600, '2024-10-20 13:45:00', NULL, NULL),
    (23, 'mental_health_image_05.jpg', 'image/jpeg', 'https://example.com/media/mental_health_image_05.jpg', 550, '2024-10-20 13:50:00', NULL, NULL),
    (24, 'wellness_image_05.jpg', 'image/jpeg', 'https://example.com/media/wellness_image_05.jpg', 700, '2024-10-20 13:55:00', NULL, NULL),
    (25, 'medical_advice_image_05.jpg', 'image/jpeg', 'https://example.com/media/medical_advice_image_05.jpg', 620, '2024-10-20 14:00:00', NULL, NULL),
    (26, 'image_26.jpg', 'image/jpeg', 'https://example.com/media/image_26.jpg', 450, '2024-10-20 14:10:00', NULL, NULL),
    (27, 'image_27.jpg', 'image/jpeg', 'https://example.com/media/image_27.jpg', 500, '2024-10-20 14:15:00', NULL, NULL),
    (28, 'image_28.jpg', 'image/jpeg', 'https://example.com/media/image_28.jpg', 480, '2024-10-20 14:20:00', NULL, NULL),
    (29, 'image_29.jpg', 'image/jpeg', 'https://example.com/media/image_29.jpg', 510, '2024-10-20 14:25:00', NULL, NULL),
    (30, 'image_30.jpg', 'image/jpeg', 'https://example.com/media/image_30.jpg', 470, '2024-10-20 14:30:00', NULL, NULL),
    (31, 'image_31.jpg', 'image/jpeg', 'https://example.com/media/image_31.jpg', 530, '2024-10-20 14:35:00', NULL, NULL),
    (32, 'image_32.jpg', 'image/jpeg', 'https://example.com/media/image_32.jpg', 490, '2024-10-20 14:40:00', NULL, NULL),
    (33, 'image_33.jpg', 'image/jpeg', 'https://example.com/media/image_33.jpg', 520, '2024-10-20 14:45:00', NULL, NULL),
    (34, 'image_34.jpg', 'image/jpeg', 'https://example.com/media/image_34.jpg', 500, '2024-10-20 14:50:00', NULL, NULL),
    (35, 'image_35.jpg', 'image/jpeg', 'https://example.com/media/image_35.jpg', 600, '2024-10-20 14:55:00', NULL, NULL),
    (36, 'image_36.jpg', 'image/jpeg', 'https://example.com/media/image_36.jpg', 480, '2024-10-20 15:00:00', NULL, NULL),
    (37, 'image_37.jpg', 'image/jpeg', 'https://example.com/media/image_37.jpg', 530, '2024-10-20 15:05:00', NULL, NULL),
    (38, 'image_38.jpg', 'image/jpeg', 'https://example.com/media/image_38.jpg', 550, '2024-10-20 15:10:00', NULL, NULL),
    (39, 'image_39.jpg', 'image/jpeg', 'https://example.com/media/image_39.jpg', 600, '2024-10-20 15:15:00', NULL, NULL),
    (40, 'image_40.jpg', 'image/jpeg', 'https://example.com/media/image_40.jpg', 620, '2024-10-20 15:20:00', NULL, NULL),
    (41, 'image_41.jpg', 'image/jpeg', 'https://example.com/media/image_41.jpg', 400, '2024-10-20 15:25:00', NULL, NULL),
    (42, 'image_42.jpg', 'image/jpeg', 'https://example.com/media/image_42.jpg', 450, '2024-10-20 15:30:00', NULL, NULL),
    (43, 'image_43.jpg', 'image/jpeg', 'https://example.com/media/image_43.jpg', 550, '2024-10-20 15:35:00', NULL, NULL),
    (44, 'image_44.jpg', 'image/jpeg', 'https://example.com/media/image_44.jpg', 300, '2024-10-20 15:40:00', NULL, NULL),
    (45, 'image_45.jpg', 'image/jpeg', 'https://example.com/media/image_45.jpg', 520, '2024-10-20 15:45:00', NULL, NULL),
    (46, 'image_46.jpg', 'image/jpeg', 'https://example.com/media/image_46.jpg', 600, '2024-10-20 15:50:00', NULL, NULL),
    (47, 'image_47.jpg', 'image/jpeg', 'https://example.com/media/image_47.jpg', 550, '2024-10-20 15:55:00', NULL, NULL),
    (48, 'image_48.jpg', 'image/jpeg', 'https://example.com/media/image_48.jpg', 700, '2024-10-20 16:00:00', NULL, NULL),
    (49, 'image_49.jpg', 'image/jpeg', 'https://example.com/media/image_49.jpg', 620, '2024-10-20 16:05:00', NULL, NULL),
    (50, 'image_50.jpg', 'image/jpeg', 'https://example.com/media/image_50.jpg', 450, '2024-10-20 16:10:00', NULL, NULL);

INSERT INTO category (id, name, media_id, user_id, created_at, modified_at, deleted_at) VALUES
    (1, 'Physical Health', 1, 1, '2024-10-20 10:00:00', NULL, NULL),
    (2, 'Mental Wellbeing', 2, 2, '2024-10-20 10:05:00', NULL, NULL),
    (3, 'Nutrition and Diet', 3, 1, '2024-10-20 10:10:00', NULL, NULL),
    (4, 'Fitness and Exercise', 4, 1, '2024-10-20 10:15:00', NULL, NULL),
    (5, 'Preventive Health', 5, 1, '2024-10-20 10:20:00', NULL, NULL);

INSERT INTO topics (id, name, media_id, user_id, category_id, created_at, modified_at, deleted_at) VALUES
    (1, 'Benefits of Regular Checkups', 6, 1, 1, '2024-10-20 10:30:00', NULL, NULL),
    (2, 'How to Manage Chronic Pain', 7, 1, 1, '2024-10-20 10:35:00', NULL, NULL),
    (3, 'Understanding Mental Health', 8, 1, 1, '2024-10-20 10:40:00', NULL, NULL),

    (4, 'Mindfulness Techniques', 9, 1, 2, '2024-10-20 10:45:00', NULL, NULL),
    (5, 'Coping with Anxiety', 10, 1, 2, '2024-10-20 10:50:00', NULL, NULL),
    (6, 'The Importance of Therapy', 11, 1, 2, '2024-10-20 10:55:00', NULL, NULL),

    (7, 'Healthy Meal Prep Ideas', 12, 1, 3, '2024-10-20 11:00:00', NULL, NULL),
    (8, 'Understanding Nutritional Labels', 13, 1, 3, '2024-10-20 11:05:00', NULL, NULL),
    (9, 'How to Balance Macronutrients', 14, 1, 3, '2024-10-20 11:10:00', NULL, NULL),

    (10, 'Home Workout Routines', 15, 1, 4, '2024-10-20 11:15:00', NULL, NULL),
    (11, 'The Role of Cardio in Fitness', 16, 1, 4, '2024-10-20 11:20:00', NULL, NULL),
    (12, 'Yoga for Beginners', 17, 1, 4, '2024-10-20 11:25:00', NULL, NULL),

    (13, 'Importance of Vaccinations', 18, 1, 5, '2024-10-20 11:30:00', NULL, NULL),
    (14, 'Regular Health Screenings', 19, 1, 5, '2024-10-20 11:35:00', NULL, NULL),
    (15, 'Staying Safe During Illness', 20, 1, 5, '2024-10-20 11:40:00', NULL, NULL);

INSERT INTO posts (id, title, description, status, time_read, media_id, public_at, user_id, top_id, category_id,
                   created_at, modified_at, deleted_at) VALUES
-- Posts for Topic 1: 'Benefits of Regular Checkups'
(1, 'The Importance of Regular Health Checkups', 'This post discusses the crucial role of regular health checkups in maintaining physical health.', true, 5, 21, '2024-10-20 12:00:00', 1, 1, 1, '2024-10-20 12:00:00', NULL, NULL),
(2, 'How Often Should You Get a Checkup?', 'Learn how frequently you should schedule health checkups based on your age and health status.', true, 4, 22, '2024-10-20 12:05:00', 1, 1, 1, '2024-10-20 12:05:00', NULL, NULL),

-- Posts for Topic 2: 'How to Manage Chronic Pain'
(3, 'Chronic Pain Management Techniques', 'Explore effective techniques for managing chronic pain in daily life.', true, 6, 23, '2024-10-20 12:10:00', 2, 2, 1, '2024-10-20 12:10:00', NULL, NULL),
(4, 'Understanding Your Pain: A Guide', 'A comprehensive guide to understanding and managing your chronic pain.', true, 7, 24, '2024-10-20 12:15:00', 1, 2, 1, '2024-10-20 12:15:00', NULL, NULL),

-- Posts for Topic 3: 'Understanding Mental Health'
(5, 'What is Mental Health?', 'An overview of mental health, its importance, and common misconceptions.', true, 5, 25, '2024-10-20 12:20:00', 3, 3, 1, '2024-10-20 12:20:00', NULL, NULL),
(6, 'Signs of Mental Health Issues', 'Learn the signs that may indicate a mental health issue and when to seek help.', true, 4, 26, '2024-10-20 12:25:00', 1, 3, 1, '2024-10-20 12:25:00', NULL, NULL),

-- Posts for Topic 4: 'Mindfulness Techniques'
(7, 'Benefits of Mindfulness Meditation', 'Discover the benefits of practicing mindfulness meditation for mental wellbeing.', true, 5, 27, '2024-10-20 12:30:00', 2, 4, 2, '2024-10-20 12:30:00', NULL, NULL),
(8, 'Mindfulness Exercises for Beginners', 'A beginner’s guide to simple mindfulness exercises to reduce stress.', true, 6, 28, '2024-10-20 12:35:00', 3, 4, 2, '2024-10-20 12:35:00', NULL, NULL),

-- Posts for Topic 5: 'Coping with Anxiety'
(9, 'Coping Strategies for Anxiety', 'Effective strategies to help cope with anxiety and stress.', true, 7, 29, '2024-10-20 12:40:00', 4, 5, 2, '2024-10-20 12:40:00', NULL, NULL),
(10, 'Understanding Your Anxiety Triggers', 'Identifying and managing anxiety triggers in your daily life.', true, 5, 30, '2024-10-20 12:45:00', 1, 5, 2, '2024-10-20 12:45:00', NULL, NULL),

-- Posts for Topic 6: 'The Importance of Therapy'
(11, 'Why Therapy Can Help', 'Explore how therapy can be beneficial for mental health improvement.', true, 6, 31, '2024-10-20 12:50:00', 2, 6, 2, '2024-10-20 12:50:00', NULL, NULL),
(12, 'Finding the Right Therapist', 'Tips on how to find the right therapist for your needs.', true, 5, 32, '2024-10-20 12:55:00', 1, 6, 2, '2024-10-20 12:55:00', NULL, NULL),

-- Posts for Topic 7: 'Healthy Meal Prep Ideas'
(13, 'Quick and Healthy Meal Prep Recipes', 'Discover quick and healthy recipes for your weekly meal prep.', true, 5, 33, '2024-10-20 13:00:00', 3, 7, 3, '2024-10-20 13:00:00', NULL, NULL),
(14, 'Meal Prep for Beginners', 'A guide to getting started with meal prep for a healthier lifestyle.', true, 6, 34, '2024-10-20 13:05:00', 1, 7, 3, '2024-10-20 13:05:00', NULL, NULL),

-- Posts for Topic 8: 'Understanding Nutritional Labels'
(15, 'How to Read Nutritional Labels', 'Learn how to properly read and understand nutritional labels.', true, 5, 35, '2024-10-20 13:10:00', 1, 8, 3, '2024-10-20 13:10:00', NULL, NULL),
(16, 'Common Misconceptions About Nutrition', 'Debunking common misconceptions surrounding nutrition.', true, 6, 36, '2024-10-20 13:15:00', 4, 8, 3, '2024-10-20 13:15:00', NULL, NULL),

-- Posts for Topic 9: 'How to Balance Macronutrients'
(17, 'Understanding Macronutrients', 'A comprehensive guide to understanding macronutrients and their role in nutrition.', true, 5, 37, '2024-10-20 13:20:00', 2, 9, 3, '2024-10-20 13:20:00', NULL, NULL),
(18, 'Creating a Balanced Diet', 'Tips for creating a balanced diet that includes all macronutrients.', true, 6, 38, '2024-10-20 13:25:00', 1, 9, 3, '2024-10-20 13:25:00', NULL, NULL),

-- Posts for Topic 10: 'Home Workout Routines'
(19, 'The Best Home Workout Routines', 'A list of effective workout routines you can do at home.', true, 5, 39, '2024-10-20 13:30:00', 1, 10, 4, '2024-10-20 13:30:00', NULL, NULL),
(20, 'Staying Motivated to Work Out at Home', 'Tips on how to stay motivated to exercise at home.', true, 6, 40, '2024-10-20 13:35:00', 3, 10, 4, '2024-10-20 13:35:00', NULL, NULL),

-- Posts for Topic 11: 'The Role of Cardio in Fitness'
(21, 'Benefits of Cardio Exercises', 'Exploring the numerous benefits of cardio exercises for overall health.', true, 5, 41, '2024-10-20 13:40:00', 1, 11, 4, '2024-10-20 13:40:00', NULL, NULL),
(22, 'Cardio vs. Strength Training', 'Understanding the differences between cardio and strength training.', true, 6, 42, '2024-10-20 13:45:00', 1, 11, 4, '2024-10-20 13:45:00', NULL, NULL),

-- Posts for Topic 12: 'Yoga for Beginners'
(23, 'Getting Started with Yoga', 'A beginner’s guide to starting yoga practice at home.', true, 5, 43, '2024-10-20 13:50:00', 1, 12, 4, '2024-10-20 13:50:00', NULL, NULL),
(24, 'Benefits of Yoga for Mind and Body', 'Discover how yoga can benefit both your mind and body.', true, 6, 44, '2024-10-20 13:55:00', 1, 12, 4, '2024-10-20 13:55:00', NULL, NULL),

-- Posts for Topic 13: 'Healthy Sleep Habits'
(25, 'Tips for Better Sleep', 'Practical tips to improve your sleep quality and habits.', true, 5, 45, '2024-10-20 14:00:00', 2, 13, 5, '2024-10-20 14:00:00', NULL, NULL),
(26, 'The Science of Sleep', 'An overview of the science behind sleep and its importance.', true, 6, 46, '2024-10-20 14:05:00', 1, 13, 5, '2024-10-20 14:05:00', NULL, NULL),

-- Posts for Topic 14: 'Mindful Eating Practices'
(27, 'What is Mindful Eating?', 'Explore the concept of mindful eating and its benefits.', true, 5, 47, '2024-10-20 14:10:00', 1, 14, 5, '2024-10-20 14:10:00', NULL, NULL),
(28, 'Mindful Eating: Tips and Techniques', 'Tips and techniques for practicing mindful eating in daily life.', true, 6, 48, '2024-10-20 14:15:00', 1, 14, 5, '2024-10-20 14:15:00', NULL, NULL);

