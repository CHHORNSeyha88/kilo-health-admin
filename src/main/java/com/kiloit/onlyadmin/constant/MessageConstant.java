package com.kiloit.onlyadmin.constant;

/**
 * @author Sombath
 * create at 10/9/23 8:53 PM
 */
public class MessageConstant {

    public static final String SUCCESSFULLY = "Successfully";
    public static final String ALL = "ALL";

    public static class ROLE {
        public final static String ADMIN = "ADMIN";
        public final static String USER = "USER";
        public final static Long USER_ROLE_ID = 2L; // user

        public static final String ROLE_CREATED_SUCCESSFULLY = "Role has been created";
        public static final String ROLE_NOT_FOUND = "Role could not be found";
        public static final String ROLE_UPDATED_SUCCESSFULLY = "Successfully";
        public static final String ROLE_DELETED_SUCCESSFULLY = "Role has been deleted";
        public static final String ROLE_HAS_USER = "Role has user delete user first";
    }

    public static class CATEGORY {
        public final static String NAME_IS_NULL = "Name is null";
        public final static String CATEGORY_ID_IS_NULL = "Category id is null";
        public final static String USER_ID_IS_NULL = "UserID is null";
        public final static String MEDIA_ID_IS_NULL = "MediaID is null";
        public static final String CATEGORY_HAS_BEEN_CREATED = "Category has been created";
        public static final String CATEGORY_HAS_BEEN_UPDATED = "Category has been updated";
        public static final String CATEGORY_COULD_NOT_BE_FOUND = "Category could not be found";
        public static final String CATEGORY_HAS_BEEN_DELETED = "Category has been deleted";
    }

    public static class POST{
        public static final String POST_HAS_BEEN_CREATED = "Post has been created";
        public static final String POST_COULD_NOT_BE_FOUND = "Post could nt be found";
        public static final String POST_HAS_BEEN_DELETED = "Post has been deleted";
        public static final String POST_TITLE_IS_NULL = "Post title is null";
        public static final String POST_DESCRIPTION_IS_NULL = "Post description is null";
        public static final String POST_PUBLIC_AT_IS_NULL = "Post public at is null";
        public static final String POST_STATUS_IS_NULL = "Post status is null";
        public static final String POST_ID_NOT_FOUND = "Post id not found";
    }
    public static class USER{
        public static final String USER_ID_IS_NULL = "User id is null";
        public static final String USER_HAS_BEEN_CREATED = "User has been created";
        public static final String USER_HAS_BEEN_UPDATED = "User has been updated";
        public static final String USER_HAS_BEEN_DELETED = "User has been deleted";
        public static final String USER_NOT_FOUND = "User could not be found";
    }
    public static class FILEMEDIA{
        public static final String FILE_MEDIA_NOT_FOUNT = "file media not fount";
        public static final String FILE_MEDIA_ID_IS_NULL = "file media id is null";
        public static final String FILE_MEDIA_NAME_IS_NULL = "file media name is null";
        public static final String FILE_MEDIA_TYPE_IS_NULL = "file media type is null";
        public static final String FILE_MEDIA_SIZE_IS_NULL = "file media size is null";
        public static final String FILE_MEDIA_URL_IS_NULL = "file media url is null";
        public static final String FILE_MEDIA_HAS_BEEN_CREATED = "file media has been created";
        public static final String FILE_MEDIA_HAS_BEEN_DELETE = "file media has been deleted";
    }
    public static class TOPIC{
        public static final String TOPIC_ID_IS_NULL = "Topic id is null";
        public static final String TOPIC_NOT_FOUND = "Topic not found";
        public static final String TOPIC_HAS_BEEN_CREATE = "Topic has been create";
        public static final String TOPIC_HAVE_BEEN_DELETED = "Topic has been delete";
    }

}
