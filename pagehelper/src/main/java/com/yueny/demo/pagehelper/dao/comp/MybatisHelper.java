package com.yueny.demo.pagehelper.dao.comp;

import java.io.IOException;
import java.sql.Connection;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.jdbc.ScriptRunner;
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

	/**
	 * 执行SQL文件, eg: ddl/mysql/jpetstore-mysql-dataload.sql
	 */
	public boolean runnerSqlFile(final String sqlFile) {
		// 执行SQL
		SqlSession session = null;
		try {
			session = getSqlSession();
			final Connection conn = session.getConnection();

			final ScriptRunner runner = new ScriptRunner(conn);
			runner.setLogWriter(null);
			runner.runScript(Resources.getResourceAsReader(sqlFile));

			return true;
		} catch (final IOException e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return false;
	}

	/**
	 * 执行SQL语句
	 *
	 * @return
	 */
	public void runnerSqlScript(final String sql) {
		// 执行SQL
		SqlSession session = null;
		try {
			session = getSqlSession();
			final Connection conn = session.getConnection();

			final ScriptRunner runner = new ScriptRunner(conn);
			runner.setLogWriter(null);
			// runner.run
		} finally {
			if (session != null) {
				session.close();
			}
		}

	}
}
