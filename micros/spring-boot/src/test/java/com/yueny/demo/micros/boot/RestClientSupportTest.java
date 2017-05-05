package com.yueny.demo.micros.boot;

import java.net.URI;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.springframework.core.ParameterizedTypeReference;

import com.yueny.demo.micros.boot.api.request.JobForMatcherRequest;
import com.yueny.rapid.data.resp.pojo.response.NormalResponse;
import com.yueny.superclub.service.rest.http.client.RestClientSupport;

public class RestClientSupportTest {
	public class OwenerRestClientSupport extends RestClientSupport {
		@Override
		public <T, P> T postForObject(final String serviceUrl, final P param,
				final ParameterizedTypeReference<T> responseType, final Object... uriVariables) {
			return super.postForObject(serviceUrl, param, responseType, uriVariables);
		}

		/**
		 * 提供基础HTTP调用获取返回对象的方法，封装了exchange方法
		 */
		@Override
		protected void exchangeForObjectExtends(final URI uri) {
			System.out.println("请求失败了....");
		}
	}

	private static final String serviceUrl = "http://localhost:8090";

	private OwenerRestClientSupport client;

	@Before
	public void before() {
		client = new OwenerRestClientSupport();
		client.setServiceProvider(serviceUrl);
		client.setConnectTimeout(30000);// 30s
		client.init();
	}

	@Test
	public void send() {
		// final JobForMatcherRequest req =
		// JobForMatcherRequest.builder().borrowRound(20).delay(5L).lendRound(20).build();
		final JobForMatcherRequest req = new JobForMatcherRequest();
		req.setBorrowRound(20);
		req.setDelay(5L);
		req.setLendRound(20);
		req.setMask("测试啊");
		req.setTimer(new Date());

		try {
			// final NormalResponse<JobForMatcherRequest> resp =
			// client.postForObject("/post/submit", req,
			// new
			// ParameterizedTypeReference<NormalResponse<JobForMatcherRequest>>()
			// {
			// });
			final NormalResponse<JobForMatcherRequest> resp = client.postForObject("/post/submit", req,
					new ParameterizedTypeReference<NormalResponse<JobForMatcherRequest>>() {
					});

			System.out.println(resp);
		} catch (final Exception e) {
			e.printStackTrace();
		}

	}

	@Test
	public void testData() {
		final JobForMatcherRequest req = new JobForMatcherRequest();
		req.setBorrowRound(20);
		req.setDelay(5L);
		req.setLendRound(20);
		req.setMask("测试啊");
		req.setTimer(new Date());

		// req
		final String r = HttpUtils.doPost(serviceUrl + "/post/data", req);
		System.out.println(r);
	}
}
