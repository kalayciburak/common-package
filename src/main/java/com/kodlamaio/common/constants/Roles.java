package com.kodlamaio.common.constants;

public class Roles {
    public static final String SuperAdmin = "superadmin";
    public static final String Admin = "admin";
    public static final String Developer = "developer";
    public static final String Moderator = "moderator";
    public static final String User = "user";
    public static final String Guest = "guest";

    public static final String SuperAdminOrAdmin = "hasRole('superadmin') or hasRole('admin')";
    public static final String SuperAdminOrAdminOrDeveloper = "hasRole('superadmin') or hasRole('admin') or hasRole('developer')";
    public static final String AdminOrDeveloperOrModerator = "hasRole('admin') or hasRole('developer') or hasRole('moderator')";
    public static final String AdminOrDeveloper = "hasRole('admin') or hasRole('developer')";
    public static final String AdminOrModerator = "hasRole('admin') or hasRole('moderator')";
    public static final String DeveloperOrModerator = "hasRole('developer') or hasRole('moderator')";
    public static final String ModeratorOrCustomer = "hasRole('moderator') or hasRole('customer')";
    public static final String ModeratorOrCustomerOrUser = "hasRole('moderator') or hasRole('customer') or hasRole('user')";
    public static final String CustomerOrUser = "hasRole('customer') or hasRole('user')";
    public static final String CustomerOrUserOrGuest = "hasRole('customer') or hasRole('user') or hasRole('guest')";
    public static final String UserOrGuest = "hasRole('user') or hasRole('guest')";
    public static final String SuperAdminAndAdmin = "hasRole('superadmin') and hasRole('admin')";
    public static final String SuperAdminAndAdminAndDeveloper = "hasRole('superadmin') and hasRole('admin') and hasRole('developer')";
    public static final String AdminAndDeveloperAndModerator = "hasRole('admin') and hasRole('developer') and hasRole('moderator')";
    public static final String AdminAndDeveloper = "hasRole('admin') and hasRole('developer')";
    public static final String AdminAndModerator = "hasRole('admin') and hasRole('moderator')";
    public static final String DeveloperAndModerator = "hasRole('developer') and hasRole('moderator')";

    public static final String AllRoles = "hasRole('superadmin') and hasRole('admin') and hasRole('developer') and hasRole('moderator') and hasRole('customer') and hasRole('user') and hasRole('guest')";
    public static final String OneOfAllRoles = "hasRole('superadmin') or hasRole('admin') or hasRole('developer') or hasRole('moderator') or hasRole('customer') or hasRole('user') or hasRole('guest')";

    public static class HasRole {
        public static final String SuperAdmin = "hasRole('superadmin')";
        public static final String Admin = "hasRole('admin')";
        public static final String Developer = "hasRole('developer')";
        public static final String Moderator = "hasRole('moderator')";
        public static final String Customer = "hasRole('customer')";
        public static final String User = "hasRole('user')";
        public static final String Guest = "hasRole('guest')";
    }
}