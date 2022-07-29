package utils;

import bean.TestData;
import io.qameta.allure.Allure;

import java.util.Map;

public class AllureUtil {

    public static void setReport(TestData testData, String detail, Map<String,String> savedMap){

        String link=testData.getLink();
        String header=testData.getHeader();
        String param=testData.getParam();
        String description=testData.getDescription();
        String url=testData.getUrl();
        if(link==null||link.equals("")){
        }else {
            Allure.issue("link",link);
        }
        if(detail==null||detail.equals("")){
        }else {
            Allure.addAttachment(description+" ---> response details",detail);
        }
        if(header==null||header.equals("")){
        }else {
            Allure.parameter("Headers",ParamUtil.parse(header,savedMap));
        }
        if(param==null||param.equals("")){
        }else {
            Allure.parameter("Request Payload", ParamUtil.parse(param,savedMap));
        }


        //设置报告
        Allure.suite(testData.getSuite());
        Allure.epic(testData.getEpic());
        Allure.feature(testData.getFeature());
        Allure.story(testData.getStory());
        Allure.getLifecycle().updateTestCase(testResult -> testResult.setName(testData.getDescription()));
        Allure.description(description);
        Allure.step(testData.getStep());
        Allure.parameter("Request URL",ParamUtil.parse(url,savedMap));
        Allure.parameter("Request Method",testData.getMethod());
    }
}
