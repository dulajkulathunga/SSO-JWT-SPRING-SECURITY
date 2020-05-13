package com.sso.jwt.constant;

public enum PropertiesEnum {

	HEADER_STRING_ID_TOKEN("Authorization"), HEADER_STRING_ACCESS_TOKEN("Access_token"),
	HEADER_STRING_REFRESH_TOKEN("Refresh_token"), JWT_SECRET_TO_ENCRYPT_DECRYPT("20200512"),
	EXPIRATION_TIME_ID_TOKEN("1000000000"), EXPIRATION_TIME_ACCESS_TOKEN("1000000000"),
	EXPIRATION_TIME_REFRESH_TOKEN("1000000000"), TOKEN_PREFIX("Bearer ");

	private String string;

	private PropertiesEnum(String string) {
		this.string = string;
	}

	public String toString() {
		return string;
	}
}
