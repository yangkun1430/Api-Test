package base;

import bean.TestData;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import impl.requestImpl;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.testng.Assert;
import utils.AllureUtil;
import utils.DataUtil;
import utils.HttpUtil;
import utils.ParamUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Base implements requestImpl {

    @Override
    public HttpResponse sendRequest(TestData testData,Map<String,String> savedMap) throws IOException {
        HttpResponse response = null;
        
        String method=testData.getMethod();
        String o_head=testData.getHeader();
        
        String url=setUrl(testData,savedMap);
        Map<String,String> head = null;
        String param=setParamStr(testData,savedMap);
        if(o_head!=null&&!o_head.equals("")){
            head=setHeaderMap(testData,savedMap);
        }
        if(o_head==null||o_head.equals("")){
            response=HttpUtil.get(url);
        }else if(method.equals("get")){
            response=HttpUtil.get(url,head);
        }else if(method.equals("post")){
            response=HttpUtil.post(url,param,head);
        }
        return response;
    }

    @Override
    public String getResponseStr(HttpResponse response) throws IOException {
        String res= HttpUtil.getResponseString(response);
        return res;
    }

    @Override
    public JSONObject getResponseJson(HttpResponse response) throws IOException {
        JSONObject resJson=HttpUtil.getResponseJson(response);
        return resJson;
    }

    @Override
    public String setParamStr(TestData testData,Map<String,String> savedMap) {
        String baseParam=testData.getParam();
        if(baseParam==null||baseParam.equals("")){
            return baseParam;
        }else {
            String param=replace(baseParam,savedMap);
            if(testData.getHeader().contains("json")){
                return formatJson(param);
            }else {
                return param;
            }

        }
    }

    @Override
    public Map<String, String> setHeaderMap(TestData testData,Map<String,String> savedMap) {
        Map<String,String> resMap=new HashMap<>();
        String headStr=testData.getHeader();
        if (headStr != null && !headStr.equals("")) {
            String[] headArr = headStr.split(",");
            for (String h : headArr) {
                String k = h.split("=")[0];
                String v = h.split("=")[1];
                v = replace(v, savedMap);
                resMap.put(k, v);
            }
        }
        return resMap;
    }

    @Override
    public String setUrl(TestData testData, Map<String, String> savedMap) {
        String base_url=testData.getUrl();
        if(base_url.contains("${")){
            return replace(base_url,savedMap);
        }else {
            return base_url;
        }
    }

    @Override
    public String replace(String s,Map<String,String> savedMap) {
        return ParamUtil.parse(s,savedMap);
    }

    @Override
    public void saveKey(TestData testData, Map<String, String> savedMap,String responseStr) {
        String base=testData.getSave_key();
        if(base!=null&&!base.equals("")){
            JSONObject responseJson = JSON.parseObject(responseStr);
            String[] baseSaveStrArr=testData.getSave_key().split(",");
            for(String baseKV:baseSaveStrArr){
                String k=baseKV.split("==")[0];
                String saveK=baseKV.split("==")[1];
                savedMap.put(saveK,ParamUtil.getStrFromJson(responseJson,k));
            }
        }
    }

    @Override
    public void assertValue(TestData testData, String responseStr) {
        String base=testData.getVerify();
        if(base!=null&&!base.equals("")){
            ParamUtil.assertResult(testData,responseStr);
        }
    }

    @Override
    public String getStackTraceInfo(Exception e) {
        return ParamUtil.getStackTraceInfo(e);
    }

    @Override
    public void addAccesskey(Map savedMap) {
        ParamUtil.addAccesskey(savedMap);
    }

    @Override
    public void addTimeKey(Map savedMap) {
        ParamUtil.addTimeKey(savedMap);
    }

    @Override
    public void doTest(DataUtil database,Map<String,String> saveMap,String description) {
        CloseableHttpResponse response=null;
        String detailText = "";
        String exceptionText = "";
        TestData testData = database.findTestDataByDescription(description);
        try {
             response= (CloseableHttpResponse) sendRequest(testData, saveMap);
            detailText = getResponseStr(response);
            saveKey(testData, saveMap, detailText);
        } catch (Exception e) {
            e.printStackTrace();
            exceptionText = getStackTraceInfo(e);
        }
        AllureUtil.setReport(testData, "Response:\n" + detailText +"\n"+ exceptionText + "\n\nSaved keys:\n" + saveMap, saveMap);
        Assert.assertEquals(response.getStatusLine().getStatusCode(),200);
        assertValue(testData, detailText);
    }

    @Override
    public String formatJson(String s) {
        return ParamUtil.formatJsonOneRow(s);
    }


}
