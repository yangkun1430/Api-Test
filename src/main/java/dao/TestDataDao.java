package dao;

import bean.TestData;
import org.apache.ibatis.annotations.Select;

public interface TestDataDao {

    @Select("select * from api_data where description=#{description}")
    TestData testData(String description);
}
