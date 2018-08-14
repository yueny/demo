package com.yueny.demo.sharding.example.common;

import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.Maps;

@Component
public class MybatisHelper implements InitializingBean {
	public enum SqlType {
		ALTER, BEGIN, CALL, COMMENT, CREATE, DECLARE, DELETE, DROP, INSERT, MERGE, SELECT, TRUNCATE, UPDATE;
	}

	private static Pattern FIRST_WORD = Pattern.compile("\\b(\\w+)\\b");

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
	 * 执行SQL文件， 但无执行的返回结果, eg: ddl/mysql/jpetstore-mysql-dataload.sql
	 */
	public boolean runnerSqlFile(final String sqlFile) {
		// 执行SQL
		SqlSession session = null;
		try {
			final Reader reader = Resources.getResourceAsReader(sqlFile);

			session = getSqlSession();
			final Connection conn = session.getConnection();

			final ScriptRunner runner = new ScriptRunner(conn);
			runner.setLogWriter(null);
			runner.runScript(reader);

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
	@Deprecated
	public void runnerSqlScript(final String sql) {
		// 执行SQL
		SqlSession session = null;
		try {
			session = getSqlSession();
			final Connection conn = session.getConnection();
			conn.setAutoCommit(false);

			final PreparedStatement ps = conn.prepareStatement(sql);
			if (parseSqlType(sql) == SqlType.SELECT) {
				final ResultSet rs = ps.executeQuery();
				selectList(rs);
				System.out.println(rs);
			} else {
				final boolean execute = ps.execute();
				System.out.println(execute);
			}

			ps.close();
			conn.close();
		} catch (final SQLException e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}

	}

	/**
	 * 根据SQl语句解析SQL类型
	 *
	 * @param rawSql
	 *            sql语句
	 * @return
	 */
	private SqlType parseSqlType(final String rawSql) {
		final Matcher matcher = FIRST_WORD.matcher(rawSql);
		matcher.find();
		try {
			final String firstWord = matcher.group(1).toUpperCase();
			return SqlType.valueOf(firstWord);
		} catch (final IllegalStateException ex) {
			throw ex;
		}
	}

	private Map<Object, Object> selectList(final ResultSet rs) throws SQLException {
		final Map<Object, Object> result = Maps.newHashMap();

		final ResultSetMetaData md = rs.getMetaData();
		for (int rownum = 1; rs.next() && rownum <= md.getColumnCount(); ++rownum) {
			// TODO
			result.put(md.getColumnName(rownum), rownum);
		}
		return result;
	}
}
