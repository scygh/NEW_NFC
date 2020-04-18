package com.lianxi.dingtu.newsnfc.app.utils;

/**
 * @Author 张迁-zhangqian
 * @Data 2018/5/31 上午11:25
 * @Package com.zack.shop.mvp.utils
 **/

public interface AppConstant {


    interface Api {
        String TOKEN = "token";
        String USERID = "userid";
        String BAIDU_TOKEN = "baidu_token";
        String PASSWORD = "password";
        String CLIENT_ID = "client_id";
        String CLIENT_SECRET = "client_secret";

        //        realtimeConfidence
        String REALTIME_CONFINDENCE = "realtime_confidence";
        String LIMIT_MAX_SIZE = "limit_max_size";
        String BLOCK_USER = "block_user";
        String BLOCK_REPORT = "block_report";
        String BLOCK_SETUP = "block_setup";
    }

    interface Receipt {
        String NO = "mac_number";
        String NAME = "receipt_name";
        String ADDRESS = "receipt_address";
        String PHONE = "receipt_phone";
        String isPrint = "print_state";
    }

    interface Print {
        String STUB1 = "stub1";
        String STUB2 = "stub2";
        String COST = "auto_cost";
    }

    interface ActivityIntent {
        String MODEL_DEPOSIT = "deposit";
        String MODEL_EXPENSE = "expense";
        String STEP1 = "step1";
        String STEP2 = "step2";
        String STEP3 = "step3";
    }

    interface NFC {
        String NFC_KEY = "nfc_key";
    }

    String ACTIVITY_FRAGMENT_REPLACE = "ActivityFragmentReplace";
    String SUB_LIB = "15221744";


}
