package com.mix.sale.next.util;

public class Constants {

    public static final String INITIALS_NAME_PROJECT = "BSTR";

    // messageKey
    public static final String MESSAGE_KEY_ENG = "successfully!!";
    public static final String MESSAGE_KEY_TH = "ทำรายการสำเร็จ!!";

    public static final String MESSAGE_KEYS_SAVE_ENG = "Saved. successfully!!";
    public static final String MESSAGE_KEYS_SAVE_TH = "บันทึกข้อมูลสำเร็จ!!";

    public static final String STATUS_NORMAL = "1";

    public final class IMPORT_PRODUCT {

        public static final String NAME_IMPORT_PROJECT = "BSIMP";
        public static final String PENDING_STATUS = "1";
        public static final String UPDATE_STATUS = "2";
        public static final String APPROVE_STATUS = "3";
        public static final String CANCEL_STATUS = "0";
    }

    public final class EXPORT_PRODUCT {

        public static final String NAME_IMPORT_PROJECT = "BSEP";
    }

    public final class STOCK {

        public static final String STATUS_CURRENT = "CURRENT";
        public static final String STATUS_WEEK = "WEEK";
        public static final String STATUS_MONTH = "MONTH";

        public static final String STOCK_ACTION_IN = "IN";
        public static final String STOCK_ACTION_OUT = "OUT";

        public static final Integer DATE_OF_CURRENT = 0;
        public static final Integer DATE_OF_WEEK = -7;
        public static final Integer DATE_OF_MONTH = -30;
    }

    public final class REPORT {

        public static final String REPORT_NAME_STOCK = "สต๊อกสินค้า";

        public static final String TYPE_STOCK_ALL = "ALL";
        public static final String TYPE_STOCK_IN = "IN";
        public static final String TYPE_STOCK_OUT = "OUT";

        public static final String PATH_FOLDER_FRONT = "FRONT";
        public static final String PATH_FOLDER_REPORT_PDF = "REPORT/PDF";
        public static final String PATH_FOLDER_REPORT_EXCEL = "REPORT/EXCEL";
    }

    public final class USER {

        public static final String USER_PREFIX = "BSEMP-";

        public static final Long ROLE_ADMIN = 1L;
        public static final Long ROLE_CUSTOMER = 2L;

    }

    public final class PATH_IMAGES {

        public static final String PATH_FOLDER_UPLOAD = "images";
        public static final String PATH_FOLDER_INPUT = "input";
        public static final String PATH_FOLDER_OUTPUT = "output";

        // Module
        public static final String PATH_FOLDER_PRODUCT = "product";
        public static final String PATH_FOLDER_PROFILE = "profile";
        public static final String PATH_FOLDER_PROBLEM = "problem";
    }

    public final class PROBLEM {

        public static final String PROBLEM_PREFIX = "BSISS";
        public static final String PENDING_STATUS = "1";
        public static final String UPDATE_STATUS = "2";
        public static final String APPROVE_STATUS = "3";
        public static final String CANCEL_STATUS = "0";
    }
}
