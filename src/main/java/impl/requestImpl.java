package impl;

import bean.TestData;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import utils.DataUtil;

import java.io.IOException;
import java.util.Map;

public interface requestImpl {

    HttpResponse sendRequest(TestData testData, Map<String, String> savedMap) throws IOException;

    String getResponseStr(HttpResponse response) throws IOException;

    JSONObject getResponseJson(HttpResponse response) throws IOException;

    String setParamStr(TestData testData, Map<String, String> savedMap);

    Map<String, String> setHeaderMap(TestData testData, Map<String, String> savedMap);

    String setUrl(TestData testData, Map<String, String> savedMap);

    String replace(String s, Map<String, String> savedMap);

    void saveKey(TestData testData, Map<String, String> savedMap, String responseStr);

    void assertValue(TestData testData, String responseStr);

    String getStackTraceInfo(Exception e);

    void addAccesskey(Map savedMap);

    void addTimeKey(Map savedMap);

    void doTest(DataUtil database, Map<String, String> saveMap, String description);

    String formatJson(String s);
}
