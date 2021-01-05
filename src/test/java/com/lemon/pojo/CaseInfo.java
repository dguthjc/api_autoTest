package com.lemon.pojo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

/**
 * easyPOI映射实体类，类中的属性需要和Excel表头保持一致
 */
@Data
public class CaseInfo {
    @Excel(name="序号(caseId)")
    private int caseId;

    @Excel(name="接口模块(interface)")
    private String interfaceName;

    @Excel(name = "用例标题(title)")
    private String title;

    @Excel(name = "请求头(requestHeader)")
    private String requestHeader;

    @Excel(name = "请求方式(method)")
    private String method;

    @Excel(name="接口地址(url)")
    private String url;

    @Excel(name="参数输入(inputParams)")
    private String inputParams;

    @Excel(name="期望返回结果(expected)")
    private String expected;

    @Excel(name = "数据库校验(checkSql)")
    private String checkSql;
}
