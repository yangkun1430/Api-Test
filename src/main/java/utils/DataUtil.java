package utils;

import bean.TestData;
import dao.TestDataDao;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.File;
import java.io.InputStream;

public class DataUtil {
    public InputStream in;
    public SqlSessionFactory factory;
    public SqlSession session;
    public TestDataDao testDataDao;

    public void init() throws Exception{
        in = Resources.getResourceAsStream("SqlMapConfig.xml");
        factory = new SqlSessionFactoryBuilder().build(in);
        session = factory.openSession();
        testDataDao = session.getMapper(TestDataDao.class);

        File jsonFile =new File("allure-results");
        jsonFile.listFiles();
        for(File file:jsonFile.listFiles()){
            file.delete();
        }
    }

    public TestData findTestDataByDescription(String name){
        return testDataDao.testData(name);
    }
}
