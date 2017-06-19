/**
 *
 */
package com.yueny.demo.job.scheduler.config.bind;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.Collections;

import javax.xml.bind.JAXB;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 任务配置加载服务
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2017年6月19日 下午6:33:09
 *
 */
@Service
public final class JobsConfigurationLoader {
	private static String FILE = "/config/bind/job-bind-config.xml";
	/**
	 * logger
	 */
	private static final Logger logger = LoggerFactory.getLogger(JobsConfigurationLoader.class);

	/** 配置 */
	private static final JobsConfigData sourceConfig;
	static {
		sourceConfig = new JobsConfigData();

		try {
			loadXML();
		} catch (final Exception e) {
			logger.error("异常!", e);
		}
	}

	public static Collection<JobBean> getJobs() {
		return Collections.unmodifiableCollection(sourceConfig.getJobs());
	}

	/**
	 * XML配置加载
	 */
	private static void loadXML() {
		// load configuration
		InputStreamReader reader = null;
		try {
			// final DirectoryStream<Path> stream =
			// Files.newDirectoryStream(Paths.get("/config/bind"),
			// path -> path.toString().endsWith(".xml"));
			// for (final Path entry : stream) {
			// System.out.println(entry.toFile().getName());
			//
			// reader = new
			// InputStreamReader(JobsConfigData.class.getResourceAsStream(entry.toFile().getName()),
			// "UTF-8");
			// }
			// or
			reader = new InputStreamReader(JobsConfigData.class.getResourceAsStream(FILE), "UTF-8");

			final JobsConfigData sc = JAXB.unmarshal(reader, JobsConfigData.class);
			sourceConfig.plusData(sc.getJobs());

			logger.info("任务配置加载: " + sourceConfig);
		} catch (final Exception e) {
			logger.error("异常！", e);
		} finally {
			try {
				if (reader != null) {
					reader.close();
				}
			} catch (final IOException ignore) {
				// ignore
			}
		}
	}

}
