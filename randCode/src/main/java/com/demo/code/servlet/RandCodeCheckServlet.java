package com.demo.code.servlet;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.demo.code.helper.IdentifyingCodeHelper;

/**
 * @author yueny09
 *
 */
public class RandCodeCheckServlet extends HttpServlet {
	/**
	 *
	 */
	private static final long serialVersionUID = -3082611984083308758L;

	/**
	 * 日志记录器
	 */
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	public RandCodeCheckServlet() {
		super();
	}

	@Override
	public void destroy() {
		super.destroy();
	}

	@Override
	public void doGet(final HttpServletRequest request,
			final HttpServletResponse response) throws ServletException,
			IOException {
		doPost(request, response);
	}

	@Override
	public void doPost(final HttpServletRequest request,
			final HttpServletResponse response) throws ServletException,
			IOException {
		request.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");

		// 设置不缓存图片
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "No-cache");
		response.setDateHeader("Expires", 0);
		// 设置输出类型为jpeg图片
		response.setContentType("image/jpeg");

		final IdentifyingCodeHelper identifyingCodeHelper = new IdentifyingCodeHelper();
		final BufferedImage image = new BufferedImage(
				identifyingCodeHelper.getWidth(),
				identifyingCodeHelper.getHeight(), BufferedImage.TYPE_INT_BGR);
		// 得到画布
		final Graphics2D g = image.createGraphics();
		// 定义字体样式
		g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));

		g.setColor(identifyingCodeHelper.getRandomColor(200, 250));
		// 绘制背景
		g.fillRect(0, 0, identifyingCodeHelper.getWidth(),
				identifyingCodeHelper.getHeight());
		// 产生至多20个噪点
		// for(int i=0,n=random.nextInt(20);i<n;i++){
		// g.fillRect(random.nextInt(width),random.nextInt(height),1,1);
		// }
		g.setColor(identifyingCodeHelper.getRandomColor(180, 200));

		identifyingCodeHelper.drawRandomLines(g, 160);
		final String code = identifyingCodeHelper.drawRandomString(4, g);
		// g.drawString(code,18,20); //画字符
		logger.info("当前生成的验证码:{}.", code);
		// 保存到session里面
		final String timestamp = request.getQueryString();// 我这里就一个参数
		request.getSession().setAttribute("rand" + timestamp, code);
		logger.info("保存到session中的验证码key:{}, value:{}.", "rand" + timestamp,
				request.getSession().getAttribute("rand" + timestamp));

		g.dispose();
		ImageIO.write(image, "JPEG", response.getOutputStream());

		// ServletOutputStream so=response.getOutputStream(); //得到二进制输出流
		// JPEGImageEncoder je=JPEGCodec.createJPEGEncoder(so); //对图片进行编码成jpeg格式
		// je.encode(image);
		// so.flush(); //刷新缓存
	}

	@Override
	public void init() throws ServletException {
		super.init();
	}

}
