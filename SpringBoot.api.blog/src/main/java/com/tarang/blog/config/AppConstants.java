package com.tarang.blog.config;

public class AppConstants {
//IN THIS CLASS WE WILL MAKE THE CONSTANTS THAT WE ARE USING IN OUR PROJECT 
//INSTEAD OF USING HARDCODED NUMBERS ANYWHERE WE WILL USE CALL THESE CONSTANTS WHERE EVER NECESSARY
	public static final String PAGE_NUMBER  = "0";
	public static final String PAGE_SIZE  = "5";
	public static final String SORT_BY  = "postId";
	public static final String SORT_DIR = "asc";
	public static final Integer NORMAL_USER = 502;
	public static final Integer ADMIN_USER = 501;
	public static final String ROLE_NORMAL = "ROLE_NORMAL";
    public static final String ROLE_ADMIN = "ROLE_ADMIN";
	
	
    public static boolean isValidRole(Integer id, String name) {
        if (id != null) {
            return id.equals(NORMAL_USER) || id.equals(ADMIN_USER);
        }
        if (name != null) {
            return name.equals(ROLE_NORMAL) || name.equals(ROLE_ADMIN);
        }
        return false;
    }
	
}
