package com.lemon.data;

/**
 * 常量类
 */
public class Constants {
    //项目根路径
    public static final String BASE_URL = "http://api.lemonban.com/futureloan";
    //excel用例文件路径
    public static final String EXCEL_PATH = "src\\test\\resources\\api_testcases_futureloan_v4.xls";
    //是否调试模式
    public static final boolean IS_DEBUG = false;
    //正则表达式匹配规则
    public static String REG_PATTERN = "\\{\\{(.*?)\\}\\}";
    //数据库配置
    public static String DATABASE_URL = "jdbc:mysql://8.129.91.152:3306/futureloan?useUnicode=true&characterEncoding=utf-8";
    public static String DATABASE_USER = "future";
    public static String DATABASE_PWD = "123456";

}
