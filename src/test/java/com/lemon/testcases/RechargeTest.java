package com.lemon.testcases;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lemon.base.BaseCase;
import com.lemon.pojo.CaseInfo;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import java.util.List;
import static io.restassured.RestAssured.given;

public class RechargeTest extends BaseCase {

    List<CaseInfo> caseInfoList;

    @BeforeClass
    public void setup(){
        //从Excel读取用户信息接口模块所需要的用例数据
        caseInfoList = getCaseDataFromExcel(3);
        //参数化替换
        caseInfoList = paramsReplace(caseInfoList);
    }

    @Test(dataProvider = "getRechargeData")
    public void testRecharge(CaseInfo caseInfo){
        String logPath = addLogToFile(caseInfo.getInterfaceName(), caseInfo.getCaseId());
        //发起接口请求
        Response res =
                given().log().all().
                        headers(jsonStr2Map(caseInfo.getRequestHeader())).
                        body(caseInfo.getInputParams()).
                when().
                        post(caseInfo.getUrl()).
                then().log().all().
                        extract().response();
        addLogToAllure(logPath);
        //断言
        assertExpected(caseInfo,res);
    }

    @DataProvider
    public Object[] getRechargeData(){
        //dataprovider数据提供者返回值类型可以是Object[] 也可以是Object[][]
        return caseInfoList.toArray();
    }
}
