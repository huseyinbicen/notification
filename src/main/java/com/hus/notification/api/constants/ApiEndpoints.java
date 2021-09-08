package com.hus.notification.api.constants;

import org.springframework.http.MediaType;

public class ApiEndpoints {

    public static final String RESPONSE_CONTENTTYPE = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8";

    public static final String API_BASE_URL = "/api";
    public static final String VERSION_1_API_BASE_URL = API_BASE_URL + "/v1";
    public static final String MAIL_API_URL = VERSION_1_API_BASE_URL + "/mail";


    public static final class MailService {
        public static final String NAME = "mail-api";
        public static final String TITLE = "MAilApi";
        public static final String DESC = "Mail Api";
        public static final String PATHS = "/api/v1/mail/**";

        private MailService() {

        }
    }

    private ApiEndpoints() {

    }
}
