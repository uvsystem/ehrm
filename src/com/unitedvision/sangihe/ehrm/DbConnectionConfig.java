package com.unitedvision.sangihe.ehrm;

public class DbConnectionConfig {
	public static final String HOST = "localhost";
	public static final String PROPERTY_NAME_DATABASE_DRIVER = "com.mysql.jdbc.Driver";
    public static final String PROPERTY_NAME_DATABASE_URL = String.format("jdbc:mysql://%s:3306/sangihe_db", HOST);
    public static final String PROPERTY_NAME_DATABASE_USERNAME = "sangihe_hrm";
    public static final String PROPERTY_NAME_DATABASE_PASSWORD = "somahekaikehage";
    public static final String PROPERTY_NAME_ENTITYMANAGER_PACKAGES_TO_SCAN = "com.unitedvision.sangihe.hrm";
}
