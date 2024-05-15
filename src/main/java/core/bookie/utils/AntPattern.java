package core.bookie.utils;

public class AntPattern {

    public static final String[] UNSECURED_ENDPOINTS = {

            "/api/books/**",

            "/api/auth/**",

            "/api/patrons/**",

            "/swagger-ui/**",
            "/v3/**",

            "/actuator/**",



    };

    public static final String[] ADMIN_ENDPOINTS = {


            "/api/admin/**",



    };



}
