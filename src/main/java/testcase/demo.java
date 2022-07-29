package testcase;

import base.Base;
import io.qameta.allure.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import utils.DataUtil;

import java.util.HashMap;
import java.util.Map;

@Owner("Eden Yang")
public class demo extends Base {
    private static final Logger log = LoggerFactory.getLogger(demo.class);

    Map<String, String> saveMap = new HashMap<>();
    DataUtil database = new DataUtil();

    @BeforeTest
    public void initTestData() throws Exception {
        database.init();
        addAccesskey(saveMap);
    }

    @Test
    public void test1() {
        doTest(database,saveMap,"get_running_instance");
    }

    @Test
    public void test2() {
        doTest(database,saveMap,"get_user_session");
    }

    @Test
    public void test3() {
        doTest(database,saveMap,"initConfig");
    }



    @AfterTest
    public void after() {
        log.info("test finish");
    }

}
