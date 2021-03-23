package sampleOne.core.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.day.commons.datasource.poolservice.DataSourcePool;

import sampleOne.core.util.DBUtils;

@Component(immediate = true,service = BurgerStopDao.class)
public class BurgerStopDaoImpl implements BurgerStopDao{
	@Reference
	DBUtils dataBaseUtils;
	
	@Reference
	DataSourcePool source;

	@Override
	public List<String> getSizeCategories(String dataSourceName) throws Exception {
		// TODO Auto-generated method stub
		List<String> categoryListOfSideNav = new ArrayList<String>();
		Connection con = dataBaseUtils.getConnection(dataSourceName);
		Statement statement = con.createStatement();
		ResultSet result = statement.executeQuery("SELECT * FROM category_sizes");
		while(result.next()) {
			categoryListOfSideNav.add(result.getString("category_name"));
			
		}
		return categoryListOfSideNav;
	}


}
