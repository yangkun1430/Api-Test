package utils;

import bean.TestData;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.apache.commons.codec.digest.DigestUtils;
import org.testng.Assert;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParamUtil {

    public static void addAccesskey(Map savedMap) {
        String appkey = "d8e8560b4df405f1e7580262b4652008";
        String appsecret = "d9aa5247ff9336c95b7941604d987f6a";
        String timestamp = Long.toString(System.currentTimeMillis());
        String requestId = UUID.randomUUID().toString().trim().replaceAll("-", "");
        String signature = DigestUtils.md5Hex(appsecret + timestamp);
        Map<String, String> accesskeyMap = new HashMap<>();
        accesskeyMap.put("appkey", appkey);
        accesskeyMap.put("timestamp", timestamp);
        accesskeyMap.put("signature", signature);
        accesskeyMap.put("requestId", requestId);
        String accessKey=JSONObject.toJSONString(accesskeyMap);
        savedMap.put("accesskey",accessKey);
    }

    //替换参数
    public static String parse(String s, Map<String, String> map) {
        if (s.contains("${")) {
            Pattern replaceParamPattern = Pattern.compile("\\$\\{(.*?)\\}");
            Matcher m = replaceParamPattern.matcher(s);
            while (m.find()){
                String key = m.group(1);
                s = s.replace("${" + key + "}", map.get(key));
            }
            return s;
        } else {
            return s;
        }
    }

    public static String getStackTraceInfo(Exception e) {
        StringWriter sw = null;
        PrintWriter pw = null;
        try {
            sw = new StringWriter();
            pw = new PrintWriter(sw);
            //将出错的栈信息输出到printWriter中
            e.printStackTrace(pw);
            pw.flush();
            sw.flush();
            return sw.toString();
        } catch (Exception ex) {
            return "发生错误";
        } finally {
            if (sw != null) {
                try {
                    sw.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (pw != null) {
                pw.close();
            }
        }
    }

    public static String getStrFromJson(JSONObject jsonObject, String str) {
        Object obj = jsonObject;
        for (String s : str.split("/")) {
            if (!s.isEmpty()) {
                if (!(s.contains("[") || s.contains("]"))) {
                    obj = ((JSONObject) obj).get(s);
                } else if (s.contains("[") || s.contains("]")) {
                    obj = ((JSONArray) ((JSONObject) obj).get(s.split("\\[")[0]))
                            .get(Integer.parseInt(s.split("\\[")[1].replaceAll("]", "")));
                }
            }
        }
        return obj.toString();
    }

    public static void assertResult(TestData testData,String responseStr){
        JSONObject responseJson = JSON.parseObject(responseStr);
        String baseStr=testData.getVerify();
        String[] baseArr=baseStr.split(",");
        for(String s:baseArr){
            String jPath=s.split("==")[0];
            String expect=s.split("==")[1];
            String actual=getStrFromJson(responseJson,jPath);
            Assert.assertEquals(actual,expect);
        }
    }

    //Json格式化成一行
    public static String formatJsonOneRow(String s){
        JSONObject jsonObject = JSON.parseObject(s);
        String a =  JSON.toJSONString(jsonObject);
        return a;
    }

    //加入时间戳
    public static void addTimeKey(Map savedMap){
        long now=System.currentTimeMillis();
        Long  time = System.currentTimeMillis();  //当前时间的时间戳
        long todayStart = time/(1000*3600*24)*(1000*3600*24) - TimeZone.getDefault().getRawOffset();
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH),23,59,59);
        long todayEnd = calendar.getTime().getTime();
        long yesterday=now-86400000;
        long tomorrow=now+86400000;
        long lastWeek=now-86400000*7;
        savedMap.put("now",now+"");
        savedMap.put("todayStart",todayStart+"");
        savedMap.put("todayEnd",todayEnd+"");
        savedMap.put("yesterday",yesterday+"");
        savedMap.put("tomorrow",tomorrow+"");
        savedMap.put("lastWeek",lastWeek+"");
    }
}
