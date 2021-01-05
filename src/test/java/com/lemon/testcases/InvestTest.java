package com.lemon.testcases;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lemon.base.BaseCase;
import com.lemon.data.GlobalEnvironment;
import com.lemon.pojo.CaseInfo;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.given;

/**
 * 新增项目接口模块自动化实现
 */
public class InvestTest extends BaseCase {
    //存储excel用例数据集
    List<CaseInfo> caseInfoList;

    @BeforeClass
    public void setup(){
        //从Excel读取用户信息接口模块所需要的用例数据
        caseInfoList = getCaseDataFromExcel(6);
        //参数化替换
        caseInfoList = paramsReplace(caseInfoList);
    }

    @Test(dataProvider = "getAddLoanData")
    public void testInvest(CaseInfo caseInfo){
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
    public Object[] getAddLoanData(){
        //dataprovider数据提供者返回值类型可以是Object[] 也可以是Object[][]
        return caseInfoList.toArray();
    }

}
