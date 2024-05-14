package core.bookie.utils;

public class AntPattern {

    public static final String[] UNSECURED_ENDPOINTS = {

            "/books/**",
            "/patrons/**"
                                            };

    public static final String[] ADMIN_ENDPOINTS = {

                "/admin/**",
    };



}
