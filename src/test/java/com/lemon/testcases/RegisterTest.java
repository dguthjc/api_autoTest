package com.lemon.testcases;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lemon.base.BaseCase;
import com.lemon.pojo.CaseInfo;
import com.lemon.data.GlobalEnvironment;
import com.lemon.util.GetRandomData;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.given;

public class RegisterTest extends BaseCase {
    List<CaseInfo> caseInfoList;
    @BeforeClass
    public void setup(){
        //读取用例的数据
        caseInfoList =  getCaseDataFromExcel(0);
    }

    @Test(dataProvider = "getRegisterData")
    public void testRegister(CaseInfo caseInfo){
        //随机生成三个没有注册过的手机号码
        if(caseInfo.getCaseId()==1){
            String mobilephone1 = GetRandomData.getRandomPhone();
            GlobalEnvironment.envData.put("mobile_phone1",mobilephone1);
        }else if(caseInfo.getCaseId()==2){
            String mobilephone2 = GetRandomData.getRandomPhone();
            GlobalEnvironment.envData.put("mobile_phone2",mobilephone2);
        }else if(caseInfo.getCaseId()==3){
            String mobilephone3 = GetRandomData.getRandomPhone();
            GlobalEnvironment.envData.put("mobile_phone3",mobilephone3);
        }
        //参数化替换 --对当前的case
        caseInfo=paramsReplaceCaseInfo(caseInfo);
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
        //响应结果断言
        assertExpected(caseInfo,res);
        //数据库断言
        assertDatabase(caseInfo);
        //在登录模块用例执行结束之后将memberId保存到环境变量中
        //注册成功的密码--从用例数据里面
        Object pwd = jsonStr2Map(caseInfo.getInputParams()).get("pwd");
        //1、拿到正常用例返回响应信息里面的memberId
        if(caseInfo.getCaseId()==1){
            //2、保存到环境变量中
            GlobalEnvironment.envData.put("mobile_phone1",res.path("data.mobile_phone"));
            GlobalEnvironment.envData.put("member_id1",res.path("data.id"));
            GlobalEnvironment.envData.put("pwd1",pwd+"");
        }else if(caseInfo.getCaseId()==2){
            GlobalEnvironment.envData.put("mobile_phone2",res.path("data.mobile_phone"));
            GlobalEnvironment.envData.put("member_id2",res.path("data.id"));
            GlobalEnvironment.envData.put("pwd2",pwd+"");
        }else if(caseInfo.getCaseId()==3){
            //2、保存到环境变量中
            GlobalEnvironment.envData.put("mobile_phone3",res.path("data.mobile_phone"));
            GlobalEnvironment.envData.put("member_id3",res.path("data.id"));
            GlobalEnvironment.envData.put("pwd3",pwd+"");
        }
    }
    @DataProvider
    public Object[] getRegisterData(){
        //dataprovider数据提供者返回值类型可以是Object[] 也可以是Object[][]
        return caseInfoList.toArray();
    }
}
