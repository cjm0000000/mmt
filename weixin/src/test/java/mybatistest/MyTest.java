package mybatistest;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class MyTest {

	@Test
	public void testWebDB(){
		SqlSession session = null;
		try{
			session = MyDataSources.getInstance().getWebDB().openSession();
			assertNotNull(session);
			String expected = "lemon";
			User u = session.selectOne("mybatistest.UserMapper.get", expected);
			assertEquals(expected, u.getName());
			System.out.println(u);
		}finally{
			if(null != session)
				session.close();
		}
	}
	
	@Test
	public void testReportDB(){
		SqlSession session = null;
		try{
			session = MyDataSources.getInstance().getReportDB().openSession();
			assertNotNull(session);
			String expected = "kenny";
			User u = session.selectOne("mybatistest.UserMapper.get", expected);
			assertEquals(expected, u.getName());
			System.out.println(u);
		}finally{
			if(null != session)
				session.close();
		}
	}
}
