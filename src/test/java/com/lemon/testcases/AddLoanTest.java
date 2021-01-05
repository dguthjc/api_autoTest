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
public class AddLoanTest extends BaseCase {
    //存储excel用例数据集
    List<CaseInfo> caseInfoList;

    @BeforeClass
    public void setup(){
        //从Excel读取用户信息接口模块所需要的用例数据
        caseInfoList = getCaseDataFromExcel(4);
        //参数化替换
        caseInfoList = paramsReplace(caseInfoList);
    }

    @Test(dataProvider = "getInvestData")
    public void testAddLoan(CaseInfo caseInfo){
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
        /**
         * 由于新增项目响应结果存在bug（但该bug并不影响审核项目测试用例的执行）,
         * 所以先保存环境变量再断言，否则保存失败，影响审核项目测试用例的执行
         */
        //保存新增项目id,用于项目审核
        if (caseInfo.getCaseId()==1) //用于审核通过用例
            GlobalEnvironment.envData.put("loan_id1",res.path("data.id"));
        else if (caseInfo.getCaseId()==2) //用于审核不通过用例
            GlobalEnvironment.envData.put("loan_id2",res.path("data.id"));
        else if (caseInfo.getCaseId()==3) //用于审核项目异常用例测试时确保项目id存在并且处于审核中状态
            GlobalEnvironment.envData.put("loan_id3",res.path("data.id"));
        else if (caseInfo.getCaseId()==4) //用于投资项目异常用例测试时确保项目正常可投
            GlobalEnvironment.envData.put("loan_id4",res.path("data.id"));
        //断言
        assertExpected(caseInfo,res);
    }

    @DataProvider
    public Object[] getInvestData(){
        //dataprovider数据提供者返回值类型可以是Object[] 也可以是Object[][]
        return caseInfoList.toArray();
    }

}
