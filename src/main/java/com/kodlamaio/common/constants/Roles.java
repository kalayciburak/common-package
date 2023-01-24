package com.kodlamaio.common.constants;

import org.springframework.stereotype.Component;

@Component("R")
public class Roles {
    public static final String SuperAdmin = "superadmin";
    public static final String Admin = "admin";
    public static final String Developer = "developer";
    public static final String Moderator = "moderator";
    public static final String Customer = "customer";
    public static final String User = "user";
    public static final String Guest = "guest";
}