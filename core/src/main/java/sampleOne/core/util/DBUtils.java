package sampleOne.core.util;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.commons.datasource.poolservice.DataSourceNotFoundException;
import com.day.commons.datasource.poolservice.DataSourcePool;
@Component(immediate = true, service = DBUtils.class)
public class DBUtils {
    private Logger logger = LoggerFactory.getLogger(DBUtils.class);
    @Reference
    DataSourcePool source;
    /**
     *
     * @param dataSourceName	name of dataSource to connect to DataBase
     * @return					connection object
     * @throws DataSourceNotFoundException
     * @throws SQLException
     */
    public Connection getConnection(String dataSourceName) throws DataSourceNotFoundException, SQLException {
        DataSource dataSource = null;
        Connection connection = null;
        logger.error("Inside Connection , Source Is {}", source);
        dataSource = (DataSource) source.getDataSource(dataSourceName);
        connection = dataSource.getConnection();
        return connection;
    }

    public void closeResource(Connection con) {
        try {
            con.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void closeResource(PreparedStatement ps) {
        try {
            if(ps!=null) {
                ps.close();
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void closeResource(Statement st) {
        try {
            if(st!=null) {
                st.close();
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void closeResource(ResultSet res) {
        try {
            if(res!=null) {
                res.close();
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}