package com.lemon.util;

public class GetRandomData {
    /**
     * 随机生成一个手机号码
     */
    public static String getPhone(){
        //给予真实的初始号段，号段是在百度上面查找的真实号段
        String[] start = {"133", "153", "180", "181", "189", "199", "130", "131", "132",
                "155", "156",  "185", "186", "134", "135", "136", "137", "138", "139",
                "150", "151", "152", "157", "158", "159", "182", "183", "184", "187", "188", "198"
        };
        //随机出真实号段,使用数组的length属性，获得数组长度，
        //通过Math.random（）*数组长度获得数组下标，从而随机出前三位的号段
        String phoneFirstNum = start[(int) (Math.random() * start.length)];
        //随机出剩下的8位数
        String phoneLastNum = "";
        //定义尾号，尾号是8位
        final int LENPHONE = 8;
        //循环剩下的位数
        for (int i = 0; i < LENPHONE; i++) {
            //每次循环都从0~9挑选一个随机数
            phoneLastNum += (int) (Math.random() * 10);
        }
        //最终将号段和尾数连接起来
        String phoneNum = phoneFirstNum + phoneLastNum;
        return phoneNum;
    }

    /**
     * 获取一个数据库当中没有注册过的手机号码
     * @return 生成的手机号码
     */
    public static String getRandomPhone(){
        while(true) {
            String phone = getPhone();
            Object result = JDBCUtils.querySingle("select count(*) from member where mobile_phone=" + phone);
            if ((Long) result == 1) {
                System.out.println("手机号码注册过");
            } else {
                return phone;
            }
        }
    }
}
