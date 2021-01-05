package com.lemon.testcases;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lemon.base.BaseCase;
import com.lemon.pojo.CaseInfo;
import com.lemon.data.GlobalEnvironment;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import java.util.List;
import static io.restassured.RestAssured.given;

public class LoginTest extends BaseCase {

    List<CaseInfo> caseInfoList;

    @BeforeClass
    public void setup(){
        //从Excel读取登录接口模块所需要的用例数据
        caseInfoList = getCaseDataFromExcel(1);
        //参数化替换
        caseInfoList = paramsReplace(caseInfoList);

    }

    @Test(dataProvider = "getLoginDatas")
    public void testLogin(CaseInfo caseInfo){
        //String jsonStr = "{\"mobile_phone\":\"13323231011\",\"pwd\":\"12345678\"}";
        //字符串请求头转换成Map --
        //实现思路：原始的字符串转换会比较麻烦，把原始的字符串通过json数据类型保存，通过ObjectMapper来去转换为Map
        //jackson json字符串-->Map
        String logPath = addLogToFile(caseInfo.getInterfaceName(), caseInfo.getCaseId());
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
        //在登录模块用例执行结束之后将memberId保存到环境变量中
        //1、拿到正常用例返回响应信息里面的memberId
        if(caseInfo.getCaseId()==1){
            //拿到正常用例返回信息里面的token
            GlobalEnvironment.envData.put("token1",res.path("data.token_info.token"));
        }else if(caseInfo.getCaseId()==2){
            //拿到正常用例返回信息里面的token
            GlobalEnvironment.envData.put("token2",res.path("data.token_info.token"));
        }else if(caseInfo.getCaseId()==3){
            //拿到正常用例返回信息里面的token
            GlobalEnvironment.envData.put("token3",res.path("data.token_info.token"));
        }


    }

    @DataProvider
    public Object[] getLoginDatas(){
        //dataprovider数据提供者返回值类型可以是Object[] 也可以是Object[][]
        System.out.println("1111111");
        return caseInfoList.toArray();
    }
}
