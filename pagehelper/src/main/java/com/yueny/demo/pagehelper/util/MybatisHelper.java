package com.yueny.demo.pagehelper.util;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MybatisHelper implements InitializingBean {
	@Autowired(required = false)
	private SqlSessionFactory sqlSessionFactory;

	{
		// 此时 sqlSessionFactory is null
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		if (sqlSessionFactory != null) {
			return;
		}

		// try {
		// // 如何加载
		// // 使用MyBatis提供的Resources类加载mybatis的配置文件
		// final Reader reader =
		// Resources.getResourceAsReader("config/mybatis-test.cfg.xml");
		// // 构建sqlSession的工厂
		// sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
		//
		// reader.close();
		// // if (false) {
		// // // 执行SQL, 创建数据库
		// // SqlSession session = null;
		// // try {
		// // session = sqlSessionFactory.openSession();
		// // final Connection conn = session.getConnection();
		// // reader = Resources.getResourceAsReader("init.sql");
		// // final ScriptRunner runner = new ScriptRunner(conn);
		// // runner.setLogWriter(null);
		// // runner.runScript(reader);
		// // reader.close();
		// // } finally {
		// // if (session != null) {
		// // session.close();
		// // }
		// // }
		// // }
		// } catch (final IOException e) {
		// e.printStackTrace();
		// }
	}

	/**
	 * 获取创建能执行映射文件中sql的sqlSession
	 *
	 * @return
	 */
	public SqlSession getSqlSession() {
		return sqlSessionFactory.openSession();
	}
}
