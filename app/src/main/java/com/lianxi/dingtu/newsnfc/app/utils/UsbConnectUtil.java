package com.lianxi.dingtu.newsnfc.app.utils;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbEndpoint;
import android.hardware.usb.UsbInterface;
import android.hardware.usb.UsbManager;
import android.hardware.usb.UsbRequest;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.lianxi.dingtu.newsnfc.app.utils.card.BytesUtils2;
import com.lianxi.dingtu.newsnfc.mvp.model.entity.CardInfoBean;

import java.lang.reflect.Array;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.HashMap;

import static android.hardware.usb.UsbManager.ACTION_USB_DEVICE_ATTACHED;
import static android.hardware.usb.UsbManager.ACTION_USB_DEVICE_DETACHED;

public class UsbConnectUtil {
    private PendingIntent mPermissionIntent;
    private Context context;
    private static final String ACTION_USB_PERMISSION = "com.lianxi.dingtu.newsnfc.USB_PERMISSION";
    private UsbManager usbManager; // USB管理器
    private static UsbDeviceConnection mDeviceConnection;
    private static byte[] Sendbytes; // 发送信息字节
    private static byte[] Receiveytes; // 接收信息字节
    private static int ret = -100;
    private static UsbEndpoint epOut;
    private static UsbEndpoint epIn;

    private UsbConnectUtil(Context context) {
        this.context = context;
        mPermissionIntent = PendingIntent.getBroadcast(context, 0, new Intent(ACTION_USB_PERMISSION), 0);
        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_USB_PERMISSION);
        filter.addAction(ACTION_USB_DEVICE_ATTACHED);
        filter.addAction(ACTION_USB_DEVICE_DETACHED);
        context.registerReceiver(mUsbReceiver, filter);
        usbPrint();
    }

    private static volatile UsbConnectUtil instance;

    /**
     * @param context
     * @return
     */
    public static UsbConnectUtil getIstance(Context context) {
        if (instance == null) {
            synchronized (UsbConnectUtil.class) {
                if (instance == null) {
                    instance = new UsbConnectUtil(context);
                }
            }
        }
        return instance;
    }

    UsbInterface mInterface;
    private BroadcastReceiver mUsbReceiver = new BroadcastReceiver() {

        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (ACTION_USB_PERMISSION.equals(action)) {
                synchronized (this) {
                    UsbDevice device = intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);

                    if (intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false)) {
                        if (device != null) {
                            //call method to set up device communication
                            // 设备接口, 注意设备不同接口不同
                            for (int i = 0; i < device.getInterfaceCount(); ) {
                                /**
                                 * 获取设备接口，一般都是一个接口，你可以打印getInterfaceCount()方法查看接
                                 * 口的个数，在这个接口上有两个端点，OUT 和 IN
                                 */
                                UsbInterface usbInterface = device.getInterface(i);
                                mInterface = usbInterface;
                                break;
                            }
                            // 打开设备建立连接
                            mDeviceConnection = usbManager.openDevice(device);
                            if (mDeviceConnection.claimInterface(mInterface, true)) {
                                // 分配端点, 注意设备不同端点不同
                                epOut = mInterface.getEndpoint(1);
                                epIn = mInterface.getEndpoint(0);
                            } else {
                                mDeviceConnection.close();
                            }
//                            hacking();
                        }
                    }
                }
            }
            //当打印机连接上时自动申请权限
            else if (ACTION_USB_DEVICE_ATTACHED.equals(action)) {
                UsbDevice device = intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
                if (device != null) {
                    Toast.makeText(context, "连接USB", Toast.LENGTH_LONG).show();
                    getIstance(context);
                }
            } else if (ACTION_USB_DEVICE_DETACHED.equals(action)) {
                UsbDevice device = intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
                if (device != null) {
                    Toast.makeText(context, "断开USB", Toast.LENGTH_LONG).show();
                    colose();
                }
            }
        }
    };

    private void usbPrint() {
        // 取连接到设备上的USB设备集合
        usbManager = (UsbManager) context.getSystemService(Context.USB_SERVICE);
        HashMap<String, UsbDevice> map = usbManager.getDeviceList();
        // 没有连接设备
        if (map.isEmpty()) {
            Toast.makeText(context, "未找到任何连接设备", Toast.LENGTH_LONG).show();
            return;
        }
        // 遍历集合取指定的USB设备
        UsbDevice usbDevice = null;
        for (UsbDevice device : map.values()) {
            //不同厂商ID不同 不知道的不要乱填
            int VendorID = device.getVendorId();
            int ProductID = device.getProductId();
            Log.e("usbUtils: ", "VendorID" + VendorID + "==ProductID" + ProductID);
            if (VendorID == 1796 && ProductID == 8213) {
                usbDevice = device;
                break;
            }
        }
        // 没有找到设备
        if (usbDevice == null) {
            Toast.makeText(context, "未找到指定读卡设备", Toast.LENGTH_LONG).show();
            return;
        }
        Log.e("usbPrint: ", JSON.toJSONString(usbDevice));
        // 程序是否有操作设备的权限
        if (!usbManager.hasPermission(usbDevice)) {
            usbManager.requestPermission(usbDevice, mPermissionIntent);
            return;
        }
        // 设备接口, 注意设备不同接口不同
        for (int i = 0; i < usbDevice.getInterfaceCount(); ) {
            /**
             * 获取设备接口，一般都是一个接口，你可以打印getInterfaceCount()方法查看接
             * 口的个数，在这个接口上有两个端点，OUT 和 IN
             */
            UsbInterface usbInterface = usbDevice.getInterface(i);
            mInterface = usbInterface;
            break;
        }
        // 打开设备建立连接
        mDeviceConnection = usbManager.openDevice(usbDevice);
        if (mDeviceConnection.claimInterface(mInterface, true)) {
            // 分配端点, 注意设备不同端点不同
            epOut = mInterface.getEndpoint(1);
            epIn = mInterface.getEndpoint(0);
        } else {
            mDeviceConnection.close();
        }
//        hacking();
    }


    /**
     * 关闭连接
     */
    public void colose() {
        instance = null;
        if (mDeviceConnection != null) {
            mDeviceConnection.close();
        }
    }

    public void destroy() {
        context.unregisterReceiver(mUsbReceiver);
        repo();
        colose();
    }

    /**
     * 蜂鸣指令
     *
     * @return
     */

    public static byte[] send() {
        byte[] bt = new byte[]{
                (byte) 0x66, (byte) 0x99, (byte) 0x02, (byte) 0x00,
                (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                (byte) 0x00, (byte) 0x02
        };
        Sendbytes = Arrays.copyOf(bt, bt.length);
        ret = mDeviceConnection.bulkTransfer(epOut, Sendbytes,
                Sendbytes.length, 1000);
        Receiveytes = new byte[64];
        ret = mDeviceConnection.bulkTransfer(epIn, Receiveytes,
                Receiveytes.length, 1000);

        return Receiveytes;
    }

    /**
     * 读卡
     *
     * @return
     */

    public static CardInfoBean read(String str) {
        if (mDeviceConnection == null) return null;
        CardInfoBean cardInfoBean = new CardInfoBean();

        byte[] content = hexStringToBytes(str);
        byte[] totle = concatAll(new byte[]{(byte) 0x03, (byte) 0x00, (byte) 0x01}, content,
                new byte[]{
                        (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                        (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00
                });
        byte[] bt = concatAll(new byte[]{(byte) 0x66, (byte) 0x99}, totle, SumCheck(totle, 1));
        Sendbytes = Arrays.copyOf(bt, bt.length);
        ret = mDeviceConnection.bulkTransfer(epOut, Sendbytes,
                Sendbytes.length, 1000);
        Receiveytes = new byte[64];
        ret = mDeviceConnection.bulkTransfer(epIn, Receiveytes,
                Receiveytes.length, 1000);


        byte[] bt4 = new byte[]{
                (byte) 0x66, (byte) 0x99, (byte) 0x04, (byte) 0x00,
                (byte) 0x04, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                (byte) 0x00, (byte) 0x08
        };
        Sendbytes = Arrays.copyOf(bt4, bt4.length);
        if (null != mDeviceConnection)
            ret = mDeviceConnection.bulkTransfer(epOut, Sendbytes,
                    Sendbytes.length, 1000);
        if(ret==-1){

            Log.e("CaptureListening", "通讯失败");
        }
        Receiveytes = new byte[64];
        ret = mDeviceConnection.bulkTransfer(epIn, Receiveytes,
                Receiveytes.length, 1000);
        Log.e("CaptureListening", "返回数据: " + bytesToHexString(Receiveytes));
        if (ret == 64) {
            String num = bytesToHexString(Receiveytes).substring(8, 14);
            cardInfoBean.setNum(Integer.parseInt(num, 16));
            cardInfoBean.setName(BytesUtils2.getString(hexStringToBytes(bytesToHexString(Receiveytes).substring(16, 34))));
            String code = bytesToHexString(Receiveytes).substring(34, 38);
            cardInfoBean.setCode(code);
            String level_type = bytesToHexString(Receiveytes).substring(38, 40);
            cardInfoBean.setType(Integer.parseInt(level_type.substring(1), 16));
            cardInfoBean.setLevel(Integer.parseInt(level_type.substring(0, 1), 16));

        } else {
            Log.e("CaptureListening", "x04读取失败");
            mDeviceConnection.close();
        }

//        byte[] bt5 = new byte[]{
//                (byte) 0x66, (byte) 0x99, (byte) 0x04, (byte) 0x00,
//                (byte) 0x05, (byte) 0x00, (byte) 0x00, (byte) 0x00,
//                (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
//                (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
//                (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
//                (byte) 0x00, (byte) 0x09
//        };
//        Sendbytes = Arrays.copyOf(bt5, bt5.length);
//        ret = mDeviceConnection.bulkTransfer(epOut, Sendbytes,
//                Sendbytes.length, 1000);
//        Receiveytes = new byte[64];
//        ret = mDeviceConnection.bulkTransfer(epIn, Receiveytes,
//                Receiveytes.length, 1000);
//        if (ret == 64) {
//            String cash_account = bytesToHexString(Receiveytes).substring(8, 14);
//            int int_cash_account = Integer.parseInt(cash_account, 16);
//            Double double_cash_account = (double) int_cash_account / 100;
//            cardInfoBean.setCash_account(double_cash_account);
//
//            String allowance_account = bytesToHexString(Receiveytes).substring(16, 22);
//            int int_allowance_account = Integer.parseInt(allowance_account, 16);
//            Double double_allowance_account = (double) int_allowance_account / 100;
//            cardInfoBean.setAllowance_account(double_allowance_account);
//
//            String meal_times = bytesToHexString(Receiveytes).substring(30, 32);
//            cardInfoBean.setMeal_times(Integer.parseInt(meal_times, 16));
//
//            String consumption_num = bytesToHexString(Receiveytes).substring(34, 38);
//            cardInfoBean.setConsumption_num(Integer.parseInt(consumption_num, 16));
//
//            String spending_time = bytesToHexString(Receiveytes).substring(24, 30);
//            spending_time = "20" + spending_time;
//            cardInfoBean.setSpending_time(spending_time);
//
//        }else {
//            Log.e("CaptureListening", "x05读取失败");
//        }
//
//
//        byte[] bt6 = new byte[]{
//                (byte) 0x66, (byte) 0x99, (byte) 0x04, (byte) 0x00,
//                (byte) 0x06, (byte) 0x00, (byte) 0x00, (byte) 0x00,
//                (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
//                (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
//                (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
//                (byte) 0x00, (byte) 0x0a
//        };
//        Sendbytes = Arrays.copyOf(bt6, bt6.length);
//        ret = mDeviceConnection.bulkTransfer(epOut, Sendbytes,
//                Sendbytes.length, 1000);
//        Receiveytes = new byte[64];
//        ret = mDeviceConnection.bulkTransfer(epIn, Receiveytes,
//                Receiveytes.length, 1000);
//        if (ret == 64) {
//            String spending_limit = bytesToHexString(Receiveytes).substring(8, 14);
//            cardInfoBean.setSpending_limit(Integer.parseInt(spending_limit, 16) / 100);
//            String guaranteed_amount = bytesToHexString(Receiveytes).substring(14, 18);
//            cardInfoBean.setGuaranteed_amount(Integer.parseInt(guaranteed_amount, 16));
//            String card_validity = bytesToHexString(Receiveytes).substring(18, 24);
//            card_validity = "20" + card_validity;
//            cardInfoBean.setCard_validity(card_validity);
//            String subsidies_time = bytesToHexString(Receiveytes).substring(24, 28);
//            subsidies_time = "20" + subsidies_time;
//            cardInfoBean.setSubsidies_time(subsidies_time);
//            String isdiscount = bytesToHexString(Receiveytes).substring(28, 30);
//            byte a = (byte) Integer.parseInt(isdiscount, 16);
//            boolean b;
//            b = (a == 0x00) ? false : true;
//            cardInfoBean.setIsDiscount(b);
//            String discount = bytesToHexString(Receiveytes).substring(30, 32);
//            cardInfoBean.setDiscount(Integer.parseInt(discount, 16));
//
//        }else {
//            Log.e("CaptureListening", "x06读取失败");
//        }

        return cardInfoBean;
    }


    /**
     * 写卡
     *
     * @param cardInfoBean
     * @return
     */
    public static String write(CardInfoBean cardInfoBean) {

        StringBuilder response = new StringBuilder();

        /**
         * 块四
         */
        byte[] head = intToByte3(cardInfoBean.getNum());
        byte[] head_check = SumCheck(intToByte3(cardInfoBean.getNum()), 1);

        byte[] b_gbk = BytesUtils2.getBytes(cardInfoBean.getName().trim());
        byte[] b_9_gbk = (byte[]) arrayAddLength(b_gbk, 9 - b_gbk.length);
        byte[] body = hexStringToBytes(cardInfoBean.getCode());

        String s_level = Integer.toHexString(cardInfoBean.getLevel());
        String s_type = Integer.toHexString(cardInfoBean.getType());
        byte[] bottom = hexStringToBytes(s_level + s_type);
        byte[] totle1 = concatAll(new byte[]{(byte) 0x05, (byte) 0x00, (byte) 0x04}, head, head_check, b_9_gbk, body, bottom);
        byte[] bt4 = concatAll(new byte[]{(byte) 0x66, (byte) 0x99}, totle1, SumCheck(totle1, 1));

        Sendbytes = Arrays.copyOf(bt4, bt4.length);
        ret = mDeviceConnection.bulkTransfer(epOut, Sendbytes,
                Sendbytes.length, 1000);
        Receiveytes = new byte[64];
        ret = mDeviceConnection.bulkTransfer(epIn, Receiveytes,
                Receiveytes.length, 1000);
        if (ret != 64) {
            response.append("\n块四接收失败" +
                    "\n块四发送字节长度:" + bt4.length +
                    "\n块四接收返回值:" + String.valueOf(ret) +
                    "\nhead:" + head.length +
                    "\nhead_check:" + head_check.length +
                    "\nb_9_gbk:" + b_9_gbk.length +
                    "\nbody:" + body.length +
                    "\nbottom:" + bottom.length);

        } else {
            response.append("\n块四成功接收返回值\n" + bytesToHexString(Receiveytes));
        }

        /**
         *块五
         */
        int a = (int) cardInfoBean.getCash_account() * 100;
        byte[] byte_a = intToByte3(a);
        byte[] head_check_a = SumCheck(byte_a, 1);
        int b = (int) cardInfoBean.getAllowance_account() * 100;
        byte[] byte_b = intToByte3(b);
        byte[] head_check_b = SumCheck(byte_b, 1);

        String s = cardInfoBean.getSpending_time();
        s = s.replace("-", "");
        byte[] byte_c = str2Bcd(s.substring(2));
        byte[] byte_d = ubyteToBytes(cardInfoBean.getMeal_times());
        byte[] byte_xfcs = unsignedShortToByte2(cardInfoBean.getConsumption_num());
        byte[] byte_Xor = new byte[]{getXor(byte_xfcs)};

        byte[] totle2 = concatAll(new byte[]{(byte) 0x05, (byte) 0x00, (byte) 0x05}, byte_a, head_check_a, byte_b, head_check_b, byte_c, byte_d, new byte[]{(byte) 0x00}, byte_xfcs, byte_Xor);
        byte[] bt5 = concatAll(new byte[]{(byte) 0x66, (byte) 0x99}, totle2, SumCheck(totle2, 1));

        Sendbytes = Arrays.copyOf(bt5, bt5.length);
        ret = mDeviceConnection.bulkTransfer(epOut, Sendbytes,
                Sendbytes.length, 1000);
        Receiveytes = new byte[64];
        ret = mDeviceConnection.bulkTransfer(epIn, Receiveytes,
                Receiveytes.length, 1000);
        if (ret != 64) {
            response.append("\n块五接收失败" +
                    "\n块五发送长度:" + bt5.length +
                    "\n块五接收返回值:" + String.valueOf(ret) +
                    "\nbyte_a:" + byte_a.length +
                    "\nhead_check_a:" + head_check_a.length +
                    "\nbyte_b:" + byte_b.length +
                    "\nhead_check_b:" + head_check_b.length +
                    "\nbyte_c:" + byte_c.length +
                    "\nbyte_d:" + byte_d.length +
                    "\nbyte_xfcs:" + byte_xfcs.length +
                    "\nbyte_Xor:" + byte_Xor.length);

        } else {
            response.append("\n块五成功接收返回值\n" + bytesToHexString(Receiveytes));
        }

        /**
         * 块六
         */

        byte[] xfxe = intToByte3(cardInfoBean.getSpending_limit() * 100);
        byte[] bded = unsignedShortToByte2(cardInfoBean.getGuaranteed_amount());
        String cv = cardInfoBean.getCard_validity();
        cv = cv.replace("-", "");
        byte[] byte_cv = str2Bcd(cv.substring(2));
        String st = cardInfoBean.getSubsidies_time();
        st = st.replace("-", "");
        byte[] byte_st = str2Bcd(st.substring(2));

        byte X;
        boolean Y = false;
        X = (byte) (Y ? 0x01 : 0x00);
        byte[] bx = new byte[]{X};

        byte[] d = ubyteToBytes(cardInfoBean.getDiscount());

        byte[] totle3 = concatAll(new byte[]{(byte) 0x05, (byte) 0x00, (byte) 0x06}, xfxe, bded, byte_cv, byte_st, bx, d, new byte[]{(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00});
        byte[] bt6 = concatAll(new byte[]{(byte) 0x66, (byte) 0x99}, totle3, SumCheck(totle3, 1));

        Sendbytes = Arrays.copyOf(bt6, bt6.length);
        ret = mDeviceConnection.bulkTransfer(epOut, Sendbytes,
                Sendbytes.length, 1000);
        Receiveytes = new byte[64];
        ret = mDeviceConnection.bulkTransfer(epIn, Receiveytes,
                Receiveytes.length, 1000);
        if (ret != 64) {
            response.append("\n块六接收失败" +
                    "\n块六发送字节长度" + bt6.length +
                    "\n块六接收返回值:" + String.valueOf(ret) +
                    "\nxfxe:" + xfxe.length +
                    "\nbded:" + bded.length +
                    "\nbyte_cv:" + byte_cv.length +
                    "\nbyte_st:" + byte_st.length +
                    "\nbx:" + bx.length +
                    "\nd:" + d.length);

        } else {
            response.append("\n块六成功接收返回值\n" + bytesToHexString(Receiveytes));
        }

        return response.toString();
    }


    /**
     * ---------------------------------------------------------------------------------------------
     *
     * @param src
     * @return
     */
    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    public static byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }

    public static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

    /**
     * 校验和
     *
     * @param msg    需要计算校验和的byte数组
     * @param length 校验和位数
     * @return 计算出的校验和数组
     */
    public static byte[] SumCheck(byte[] msg, int length) {
        long mSum = 0;
        byte[] mByte = new byte[length];

        /** 逐Byte添加位数和 */
        for (byte byteMsg : msg) {
            long mNum = ((long) byteMsg >= 0) ? (long) byteMsg : ((long) byteMsg + 256);
            mSum += mNum;
        } /** end of for (byte byteMsg : msg) */

        /** 位数和转化为Byte数组 */
        for (int liv_Count = 0; liv_Count < length; liv_Count++) {
            mByte[length - liv_Count - 1] = (byte) (mSum >> (liv_Count * 8) & 0xff);
        } /** end of for (int liv_Count = 0; liv_Count < length; liv_Count++) */

        return mByte;
    }

    /**
     * int整数转换为3字节的byte数组
     *
     * @param i 整数
     * @return byte数组
     */
    public static byte[] intToByte3(int i) {
        byte[] targets = new byte[3];
        targets[2] = (byte) (i & 0xFF);
        targets[1] = (byte) (i >> 8 & 0xFF);
        targets[0] = (byte) (i >> 16 & 0xFF);
        return targets;
    }

    /**
     * short整数转换为2字节的byte数组
     *
     * @param s short整数
     * @return byte数组
     */
    public static byte[] unsignedShortToByte2(int s) {
        byte[] targets = new byte[2];
        targets[0] = (byte) (s >> 8 & 0xFF);
        targets[1] = (byte) (s & 0xFF);
        return targets;
    }

    /**
     * int整数转换为1字节的byte数组
     *
     * @param -b整数
     * @return byte数组
     */
    public static byte[] ubyteToBytes(int n) {
        byte[] b = new byte[1];
        b[0] = (byte) (n & 0xff);
        return b;
    }

    /**
     * Java异或运算、校验
     * 将字节数组两两异或，返回最后异或值
     * 如果是校验则看异或结果是否为0即可
     */
    public static byte getXor(byte[] datas) {

        byte temp = datas[0];

        for (int i = 1; i < datas.length; i++) {
            temp ^= datas[i];
        }

        return temp;
    }

    /**
     * Description: Array add length
     *
     * @param oldArray
     * @param addLength
     * @return Object
     */
    public static Object arrayAddLength(Object oldArray, int addLength) {
        Class c = oldArray.getClass();
        if (!c.isArray()) return null;
        Class componentType = c.getComponentType();
        int length = Array.getLength(oldArray);
        int newLength = length + addLength;
        Object newArray = Array.newInstance(componentType, newLength);
        System.arraycopy(oldArray, 0, newArray, 0, length);
        return newArray;
    }

    public static byte[] subBytes(byte[] src, int begin, int count) {
        byte[] bs = new byte[count];
        System.arraycopy(src, begin, bs, 0, count);
        return bs;
    }

    public static byte[] concatAll(byte[] first, byte[]... rest) {
        int totalLength = first.length;
        for (byte[] array : rest) {
            totalLength += array.length;
        }
        byte[] result = Arrays.copyOf(first, totalLength);
        int offset = first.length;
        for (byte[] array : rest) {
            System.arraycopy(array, 0, result, offset, array.length);
            offset += array.length;
        }
        return result;
    }

    /**
     * @功能: BCD码转为10进制串(阿拉伯数据)
     * @参数: BCD码
     * @结果: 10进制串
     */
    public static String bcd2Str(byte[] bytes) {
        StringBuffer temp = new StringBuffer(bytes.length * 2);
        for (int i = 0; i < bytes.length; i++) {
            temp.append((byte) ((bytes[i] & 0xf0) >>> 4));
            temp.append((byte) (bytes[i] & 0x0f));
        }
        return temp.toString().substring(0, 1).equalsIgnoreCase("0") ? temp
                .toString().substring(1) : temp.toString();
    }

    /**
     * @功能: 10进制串转为BCD码
     * @参数: 10进制串
     * @结果: BCD码
     */
    public static byte[] str2Bcd(String asc) {
        int len = asc.length();
        int mod = len % 2;
        if (mod != 0) {
            asc = "0" + asc;
            len = asc.length();
        }
        byte abt[] = new byte[len];
        if (len >= 2) {
            len = len / 2;
        }
        byte bbt[] = new byte[len];
        abt = asc.getBytes();
        int j, k;
        for (int p = 0; p < asc.length() / 2; p++) {
            if ((abt[2 * p] >= '0') && (abt[2 * p] <= '9')) {
                j = abt[2 * p] - '0';
            } else if ((abt[2 * p] >= 'a') && (abt[2 * p] <= 'z')) {
                j = abt[2 * p] - 'a' + 0x0a;
            } else {
                j = abt[2 * p] - 'A' + 0x0a;
            }
            if ((abt[2 * p + 1] >= '0') && (abt[2 * p + 1] <= '9')) {
                k = abt[2 * p + 1] - '0';
            } else if ((abt[2 * p + 1] >= 'a') && (abt[2 * p + 1] <= 'z')) {
                k = abt[2 * p + 1] - 'a' + 0x0a;
            } else {
                k = abt[2 * p + 1] - 'A' + 0x0a;
            }
            int a = (j << 4) + k;
            byte b = (byte) a;
            bbt[p] = b;
        }
        return bbt;
    }

    private Handler mHandler;

    public void hacking() {
        String nfc_key = (String) SpUtils.get(context, AppConstant.NFC.NFC_KEY, "");
        byte[] content = hexStringToBytes(nfc_key);
        byte[] totle = concatAll(new byte[]{(byte) 0x03, (byte) 0x00, (byte) 0x01}, content,
                new byte[]{
                        (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                        (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00
                });
        byte[] bt0 = concatAll(new byte[]{(byte) 0x66, (byte) 0x99}, totle, SumCheck(totle, 1));
        Sendbytes = Arrays.copyOf(bt0, bt0.length);
        if (null != mDeviceConnection)
            ret = mDeviceConnection.bulkTransfer(epOut, Sendbytes,
                    Sendbytes.length, 1000);
        byte[] bt = new byte[]{
                (byte) 0x66, (byte) 0x99, (byte) 0x04, (byte) 0x00,
                (byte) 0x04, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                (byte) 0x00, (byte) 0x08
        };
        Sendbytes = Arrays.copyOf(bt, bt.length);
        if (null != mDeviceConnection)
            ret = mDeviceConnection.bulkTransfer(epOut, Sendbytes,
                    Sendbytes.length, 1000);
        HandlerThread thread = new HandlerThread("MyHandlerThread");
        thread.start();
        mHandler = new Handler(thread.getLooper());
        mHandler.postDelayed(runnable, 2000);


    }


    Runnable runnable = new Runnable() {

        @Override
        public void run() {
            // TODO Auto-generated method stub
            while (true) {
                synchronized (this) {
                    UsbRequest request = new UsbRequest();
                    if (mDeviceConnection == null) {
                        return;
                    }
                    request.initialize(mDeviceConnection, epIn);
                    ByteBuffer buffer = ByteBuffer.allocate(64);
                    if (request == null) {
                        return;
                    }
                    request.queue(buffer, 64);
                    if (request.equals(mDeviceConnection.requestWait())) {
                        byte[] data = buffer.array();
                        mDeviceConnection.bulkTransfer(epIn, data, 64, 1000);
                        mOnCaptureListening.CaptureListening(bytesToHexString(data));
                        Log.e("CaptureListening", "定时询问结果==" + bytesToHexString(data));
                    }

                }

            }

        }
    };

    public void repo() {
        if (mHandler == null) {
            return;
        }
        mHandler.removeCallbacks(runnable);
    }

    public interface OnCaptureListening {
        void CaptureListening(String str);
    }

    OnCaptureListening mOnCaptureListening = null;

    public void setOnCaptureListening(OnCaptureListening e) {
        mOnCaptureListening = e;
    }


}
