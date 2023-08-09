package com.example.motobikestore.constants;

public class PathConstants {

    public static final String API_V1 = "/api/v1";
    public static final String ORDER = "/order";
    public static final String ORDERS = "/orders";
    public static final String PERFUMES = "/perfumes";
    public static final String USER = "/user";
    public static final String GRAPHQL = "/graphql";

    public static final String CATEGORIES = "/categories";
    public static final String MANUFACTURER = "/manufacturer";
    public static final String TAG = "/tag";
    public static final String API_V1_ADMIN = API_V1 + "/admin";
    public static final String API_V1_AUTH = API_V1 + "/auth";
    public static final String API_V1_ORDER = API_V1 + ORDER;
    public static final String API_V1_CATEGORIES = API_V1 + CATEGORIES;
    public static final String API_V1_CART = API_V1 + "/carts";
    public static final String API_V1_MANUFACTURER = API_V1 + MANUFACTURER;
    public static final String API_V1_TAG = API_V1 + TAG;
    public static final String API_V1_PERFUMES = API_V1 + PERFUMES;
    public static final String API_V1_REGISTRATION = API_V1 + "/registration";
    public static final String API_V1_REVIEW = API_V1 + "/review";
    public static final String API_V1_USERS = API_V1 + "/users";

    public static final String API_V1_PRODUCT = API_V1 + "/products";
    public static final String IMAGEURL = "localhost:5000//image/";
    public static final String SAVEIMAGEPATH = "D:/images/";
    public static final String ADD = "/add";
    public static final String DELETE = "/delete";
    public static final String EDIT = "/edit";
    public static final String EDIT_BY_ID = "/edit/{id}";
    public static final String UPDATE_STATE_BY_ID = "/updateState/{id}";


    public static final String GET = "/get";

    public static final String GET_BY_ID = "/get/{id}";
    public static final String GET_ALL = "/getall";

    public static final String DELETE_BY_PERFUME_ID = "/delete/{perfumeId}";
    public static final String ORDER_BY_EMAIL = ORDER + "/{userEmail}";
    public static final String ORDER_DELETE = ORDER + "/delete/{orderId}";
    public static final String USER_BY_ID = USER + "/{userId}";
    public static final String USER_ALL = USER + "/all";
    public static final String GRAPHQL_USER = GRAPHQL + USER;
    public static final String GRAPHQL_USER_ALL = GRAPHQL + USER + "/all";
    public static final String GRAPHQL_ORDERS = GRAPHQL + ORDERS;
    public static final String GRAPHQL_ORDER = GRAPHQL + ORDER;

    public static final String LOGIN = "/login";
    public static final String FORGOT_EMAIL = "/forgot/{email}";
    public static final String RESET = "/reset";
    public static final String CODE = "/{code}";
    public static final String RESET_CODE = RESET + CODE;
    public static final String ACTIVATE_CODE = "/activate";
    public static final String EDIT_PASSWORD = "/edit/password";

    public static final String ORDER_ID = "/{orderId}";
    public static final String ORDER_ID_ITEMS = ORDER_ID + "/items";

    public static final String PERFUME_ID = "/{perfumeId}";
    public static final String IDS = "/ids";
    public static final String SEARCH = "/search";
    public static final String SEARCH_GENDER = SEARCH + "/gender";
    public static final String SEARCH_PERFUMER = SEARCH + "/perfumer";
    public static final String SEARCH_TEXT = SEARCH + "/text";
    public static final String GRAPHQL_IDS = GRAPHQL + IDS;
    public static final String GRAPHQL_PERFUMES = GRAPHQL + PERFUMES;
    public static final String GRAPHQL_PERFUME = GRAPHQL + "/perfume";
}
