package ru.pet.socialnetwork.constants;

import java.util.List;

public interface SecurityConstants {

    List<String> RESOURCES_WHITE_LIST = List.of(
//            "/resources/**",
//            "/js/**",
//            "/css/**",
//            "/",
            // -- Swagger UI v3 (OpenAPI)
            "/swagger-ui/**",
            "/v3/api-docs/**"
//            "/webjars/bootstrap/5.0.2/**",
//            "/error"
    );

    List<String> USERS_WHITE_LIST = List.of(
            "/users/auth",
            "/users/registration"
    );

    List<String> USERS_PERMISSION_LIST = List.of(
            "/users/create",
            "/users/update",
            "/users/delete"
    );

    List<String> POSTS_WHITE_LIST = List.of(
            "/posts/create",
            "/posts/update",
            "/posts/delete",
            "/posts/getAllByUserId",
            "/posts/getFeedOfPosts",
            "/posts/getImage"
    );

    List<String> POSTS_PERMISSION_LIST = List.of(
    );

    List<String> SUBSCRIPTIONS_WHITE_LIST = List.of(
            "/subscriptions/create",
//            "/subscriptions/update",
            "/subscriptions/delete",
            "/subscriptions/getReceivedRequests",
            "/subscriptions/accept",
            "/subscriptions/reject",
            "/subscriptions/getFriends",
            "/subscriptions/getNotFriends"
    );

    List<String> SUBSCRIPTIONS_PERMISSION_LIST = List.of(
    );

    List<String> MESSAGES_WHITE_LIST = List.of(
            "/messages/create",
            "/messages/update",
            "/messages/delete",
            "/messages/getConversation"
    );

    List<String> MESSAGES_PERMISSION_LIST = List.of(
    );
}
