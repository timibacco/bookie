package core.bookie.utils;

public class AntPattern {

    public static final String[] UNSECURED_ENDPOINTS = {

            "/api/books/**",
            "/api/patrons/**",

            "/swagger-ui/**",
            "/v3/**"

    };

    public static final String[] ADMIN_ENDPOINTS = {

                "/admin/**",
    };



}
