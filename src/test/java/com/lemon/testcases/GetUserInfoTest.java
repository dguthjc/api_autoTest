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

public class GetUserInfoTest extends BaseCase {
    List<CaseInfo> caseInfoList;

    @BeforeClass
    public void setup(){
        //从Excel读取用户信息接口模块所需要的用例数据
        caseInfoList = getCaseDataFromExcel(2);
        //参数化替换
        caseInfoList = paramsReplace(caseInfoList);
    }

    @Test(dataProvider = "getUserInfoDatas")
    public void testGetUserInfo(CaseInfo caseInfo){
        //发起接口请求
        String logPath = addLogToFile(caseInfo.getInterfaceName(), caseInfo.getCaseId());
        Response res =
            given().log().all().
                    headers(jsonStr2Map(caseInfo.getRequestHeader())).
            when().
                    get(caseInfo.getUrl()).
            then().log().all().
                    extract().response();
        addLogToAllure(logPath);
        //断言
        assertExpected(caseInfo,res);
    }

    @DataProvider
    public Object[] getUserInfoDatas(){
        //dataprovider数据提供者返回值类型可以是Object[] 也可以是Object[][]
        return caseInfoList.toArray();
    }
}
