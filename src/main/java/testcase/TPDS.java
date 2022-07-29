package testcase;

import base.Base;
import io.qameta.allure.Owner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import utils.DataUtil;

import java.util.HashMap;
import java.util.Map;

@Owner("Eden Yang")
public class TPDS extends Base {

    private static final Logger log = LoggerFactory.getLogger(TPDS.class);

    Map<String, String> saveMap = new HashMap<>();
    DataUtil database = new DataUtil();

    @BeforeTest
    public void initTestData() throws Exception {
        database.init();
        addTimeKey(saveMap);
    }

    @Test
    public void test1() {
        doTest(database,saveMap,"Tvugs4XController");
    }

    @Test
    public void test2() {
        doTest(database,saveMap,"OnlinePSServlet");
    }
    @Test
    public void test3() {
        doTest(database,saveMap,"TransferServlet");
    }
    @Test
    public void test4() {
        doTest(database,saveMap,"OnlineFfmpegServlet");
    }
    @Test
    public void test5() {
        doTest(database,saveMap,"OnlineRServlet");
    }
    @Test
    public void test6() {
        doTest(database,saveMap,"TPDSService");
    }

    @Test
    public void test7() {
        doTest(database,saveMap,"GPSJson");
    }

    @Test
    public void test8() {
        doTest(database,saveMap,"SmartCaptionJson");
    }

    @Test
    public void test9() {
        doTest(database,saveMap,"ExportActionJSON");
    }

    @Test
    public void test10() {
        doTest(database,saveMap,"TakeActionJSON");
    }

    @Test
    public void test11() {
        doTest(database,saveMap,"ShareLinkActionJSON");
    }

    @Test
    public void test12() {
        doTest(database,saveMap,"VisionJson");
    }

    @Test
    public void test13() {
        doTest(database,saveMap,"MMAMiscActionJSON");
    }

    @Test
    public void test14() {
        doTest(database,saveMap,"ReportDeviceCrash");
    }

    @Test
    public void test15() {
        doTest(database,saveMap,"RActivitiesJson");
    }

    @Test
    public void test16() {
        doTest(database,saveMap,"search_tInfo");
    }

    @Test
    public void test17() {
        doTest(database,saveMap,"search_RController");
    }

    @Test
    public void test18() {
        doTest(database,saveMap,"search_ThumbnailInfo");
    }

    @Test
    public void test19() {
        doTest(database,saveMap,"search_allThumbnailInfo");
    }

    @Test
    public void test20() {
        doTest(database,saveMap,"search_relayInfo");
    }

    @Test
    public void test21() {
        doTest(database,saveMap,"search_onlineRInfo");
    }

    @Test
    public void test22() {
        doTest(database,saveMap,"search_udpRInfo");
    }

    @Test
    public void test23() {
        doTest(database,saveMap,"search_cacheRInfo");
    }

    @Test
    public void test24() {
        doTest(database,saveMap,"search_cacheGrid");
    }
}
