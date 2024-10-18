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
        public static final String CATEGORY_HAS_BEEN_CREATED = "Category has been created";
        public static final String CATEGORY_HAS_BEEN_UPDATED = "Category has been updated";
        public static final String CATEGORY_COULD_NOT_BE_FOUND = "Category could not be found";
        public static final String CATEGORY_HAS_BEEN_FALSE = "Category has been false";
        public static final String CATEGORY_HAS_BEEN_DELETED = "Category has been deleted";
        public static final String CATEGORY_ID_HAS_BEEN_DO_AN_ACTION = "Category %1s has been do an action: %2s";
        public static final String CATEGORY_STATUS_HAS_BEEN_UPDATE_TO_TRUE = "Category status has been updated to true";
        public static final String CATEGORY_NAME_ALREADY_EXISTS = "Category name already exists";
        public static final String CATEGORY_HAS_BEEN_ACTIVATED = "Category has been active";
        public static final String CATEGORY_HAS_BEEN_INACTIVATED = "Category has been in active";
        public static final int ACTIVE = 1;
        public static final int INACTIVE = 2;
        public static final int DELETE = 0;
        public static final String CATEGORY_UNKNOWN_STATUS = "unknown status";
    }

    public static class POST{
        public static final String POST_HAS_BEEN_CREATED = "Post has been created";
        public static final String POST_COULD_NOT_BE_FOUND = "Post could nt be found";
        public static final String POST_HAS_BEEN_DELETED = "Post has been deleted";
    }

    public static class PRODUCT{
        public static final String PRODUCT_HAS_BEEN_CREATED = "Product has been created";
        public static final String PRODUCT_HAS_BEEN_UPDATED = "Product has been updated";
        public static final String PRODUCT_HAS_BEEN_DELETED = "Product has been deleted";
        public static final String PRODUCT_NOT_FOUNT = "Product could not be found";
    }
    public static class VARIATION{
        public static final String VARIATION_HAS_BEEN_CREATED = "Variation has been created";
        public static final String VARIATION_HAS_BEEN_UPDATED = "Variation has been updated";
        public static final String VARIATION_HAS_BEEN_DELETED = "Variation has been deleted";
        public static final String VARIATION_NOT_FOUND = "Variation could not be found";
    }
    public static class VARIATION_OPTION{
        public static final String VARIATION_OPTION_HAS_BEEN_CREATED = "Variation option has been created";
        public static final String VARIATION_OPTION_HAS_BEEN_UPDATED = "Variation option has been updated";
        public static final String VARIATION_OPTION_HAS_BEEN_DELETED = "Variation option has been deleted";
        public static final String VARIATION_OPTION_NOT_FOUND = "Variation option could not be found";
    }
    public static class PRODUCT_ITEM{
        public static final String PRODUCT_ITEM_HAS_BEEN_CREATED = "Product item has been created";
        public static final String PRODUCT_ITEM_HAS_BEEN_UPDATED = "Product item has been updated";
        public static final String PRODUCT_ITEM_HAS_BEEN_DELETED = "Product item has been deleted";
        public static final String PRODUCT_ITEM_NOT_FOUND = "Product item could not be found";
        public static final String PRODUCT_ITEM_NOT_HAVE_STOCK = "Product item not have stock";
        public static final String PRODUCT_ITEM_HAS_ALREADY = "Product item has already";
    }
    public static class USER{
        public static final String USER_HAS_BEEN_CREATED = "User has been created";
        public static final String USER_HAS_BEEN_UPDATED = "User has been updated";
        public static final String USER_HAS_BEEN_DELETED = "User has been deleted";
        public static final String USER_NOT_FOUND = "User could not be found";
    }

    public static class CART{
        public static final String CART_HAS_BEEN_CREATED = "Cart has been created";
        public static final String CART_NOT_FOUND = "Cart could not be found";
    }
    public static class CART_ITEM{
        public static final String CART_ITEM_HAS_BEEN_CREATED = "Cart item has been created";
        public static final String CART_ITEM_HAS_BEEN_UPDATED = "Cart item has been updated";
    }

    public static class WishList{
        public static final String WISHLIST_HAS_BEEN_CREATED = "Wishlist has been created";
        public static final String WISHLIST_HAS_BEEN_ADD_ALREADY = "Wishlist have been add already";
        public static final String WISHLIST_HAS_BEEN_NOT_FOUND_PRODUCT = "wishlist has been not found product";
        public static final String WISHLIST_HAS_BEEN_REMOVE_PRODUCT = "wishlist has been remove product";
    }
    public static class SHIPPING_ADDRESS{
        public static final String SHIPPING_ADDRESS_HAS_BEEN_CREATED = "Shipping address has been created";
        public static final String SHIPPING_ADDRESS_HAS_NOT_FOUND = "Shipping address not found";
        public static final String SHIPPING_ADDRESS_HAS_BEEN_UPDATE = "Shipping address has been update";
    }

    public static class PAYMENT_TYPE{
        public static final String PAYMENT_TYPE_HAS_BEEN_CREATED = "Payment type has been created";
        public static final String PAYMENT_TYPE_NOT_FOUND = "Payment type could not be found";

    }

    public static class PAYMENT_METHOD{
        public static final String PAYMENT_METHOD_HAS_BEEN_CREATED = "Payment method has been created";
        public static final String PAYMENT_METHOD_NOT_FOUND = "Payment method could not be found";
    }

    public static class SHIPPING_METHOD{
        public static final String SHIPPING_METHOD_HAS_BEEN_CREATED = "Shipping method has been created";
        public static final String SHIPPING_METHOD_NOT_FOUND = "Shipping method not be found";
    }

    public static class ORDER{
        public static final String ORDER_HAS_BEEN_CREATED = "order has been created";
        public static final String ORDER_NOT_FOUND = "order could not be found";
        public static final String ORDER_HAS_BEEN_UPDATE = "order has been update";
    }
    public static class FILEMEDIA{
        public static final String FILE_MEDIA_NOT_FOUNT = "file media not fount";
    }
    public static class TOPIC{
        public static final String TOPIC_NOT_FOUND = "Topic not found";
        public static final String TOPIC_HAS_BEEN_CREATE = "Topic has been create";
    }

}
