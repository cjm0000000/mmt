package mybatistest;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class MyDataSources {
	private static final String MYBATIS_CONFIG = "mybatistest/mybatis.xml";
	private static MyDataSources instance;
	private static SqlSessionFactory webDB;
	private static SqlSessionFactory reportDB;

	public static MyDataSources getInstance() {
		if (null == instance) {
			instance = new MyDataSources();
			try {
				if (null == webDB || null == reportDB)
					setDataSources();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return instance;
	}

	public SqlSessionFactory getWebDB() {
		return webDB;
	}
	
	public SqlSessionFactory getReportDB() {
		return reportDB;
	}

	private static void setDataSources() throws IOException {
		InputStream inputStream = null;
		try {
			inputStream = Resources.getResourceAsStream(MYBATIS_CONFIG);
			webDB 		= new SqlSessionFactoryBuilder().build(inputStream, "webDB");
			inputStream = Resources.getResourceAsStream(MYBATIS_CONFIG);
			reportDB 	= new SqlSessionFactoryBuilder().build(inputStream, "reportDB");
		} finally {
			if (null != inputStream)
				inputStream.close();
		}
	}
}
