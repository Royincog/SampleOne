package sampleOne.core.dao;

import java.sql.SQLException;
import java.util.List;

public interface BurgerStopDao {
public List<String> getSizeCategories(String dataSourceName)throws Exception;
}
