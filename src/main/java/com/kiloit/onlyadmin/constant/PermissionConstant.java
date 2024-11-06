package com.kiloit.onlyadmin.constant;

public class PermissionConstant {
    public static final String ROLE_ADMIN = "SCOPE_ADMIN";
    public static final class ROLE{
        public static final String VIEW = "SCOPE_view_role";
        public static final String EDIT = "SCOPE_edit_role";
        public static final String CREATE = "SCOPE_create_role";
        public static final String DELETE = "SCOPE_delete_role";
        public static final String SET = "SCOPE_set_permission";
        public static final String VIEW_PERMISSION = "SCOPE_view_permission";
    }
    public static final class USER{
        public static final String VIEW = "SCOPE_view_user";
        public static final String EDIT = "SCOPE_edit_user";
        public static final String CREATE = "SCOPE_create_user";
        public static final String DELETE = "SCOPE_delete_user";
    }
    public static final class TOPIC{
        public static final String VIEW = "SCOPE_view_topic";
        public static final String EDIT = "SCOPE_edit_topic";
        public static final String CREATE = "SCOPE_create_topic";
        public static final String DELETE = "SCOPE_delete_topic";
    }
    public static final class CATEGORY{
        public static final String VIEW = "SCOPE_view_category";
        public static final String EDIT = "SCOPE_edit_category";
        public static final String CREATE = "SCOPE_create_category";
        public static final String DELETE = "SCOPE_delete_category";
    }
    public static final class FILE{
        public static final String VIEW = "SCOPE_view_file";
        public static final String UPLOAD = "SCOPE_upload_file";
        public static final String DELETE = "SCOPE_delete_file";
    }
    public static final class POST{
        public static final String VIEW = "SCOPE_view_post";
        public static final String EDIT = "SCOPE_edit_post";
        public static final String CREATE = "SCOPE_create_post";
        public static final String DELETE = "SCOPE_delete_post";
    }
}
