package com.yueny.demo.dynamic.scheduler.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yueny.demo.dynamic.scheduler.controller.resp.JobsListResponse;
import com.yueny.demo.dynamic.scheduler.entry.JobInfo;
import com.yueny.demo.dynamic.scheduler.service.ScheduleService;

/**
 * @author 袁洋 2015年8月25日 上午10:18:37
 *
 */
// @Controller
@Deprecated
public class PmsJobController extends BaseController {
	private final JobInfo jobInfo = new JobInfo();

	private final Map jsonMap = new HashMap();
	@Autowired
	private ScheduleService scheduleService;

	/**
	 * 查询作业信息
	 */
	@RequestMapping(value = "/queryAll.html", method = { RequestMethod.GET })
	@ResponseBody
	public JobsListResponse queryAll() {
		setModelAttribute("ACTION", "queryAll");

		final JobsListResponse resp = new JobsListResponse();

		final List<JobInfo> list = scheduleService.queryInfo(null);
		if (CollectionUtils.isEmpty(list)) {
			resp.setData(Collections.emptyList());
			resp.setTotal(0);
			return resp;
		}

		resp.setData(list);
		resp.setTotal(list.size());

		jsonMap.put("rows", list);
		jsonMap.put("total", list.size());
		return resp;
	}

	// public String getArticleInfoPage(@PathVariable final String
	// articleBlogId, final HttpServletResponse response) {
	// setModelAttribute(WebAttributes.ACTION, "articleInfo");
	//
	// try {
	// // 从session中获取uid
	// // final String uid = "yuanyang";
	// final ArticleBlogViewBo articleBlogView =
	// articleQueryService.getArticleInfo(articleBlogId);
	// // 当前博文
	// final ArticleBlogBo articleBlog = articleBlogView.getArticleBlog();
	//
	// setModelAttribute("title", articleBlog.getArticleTitle());
	// setModelAttribute("articleBlog", articleBlog);
	// // 文章的个人分类信息
	// setModelAttribute("owenerTags", articleBlogView.getOwenerTags());
	// // 该博文所归属的全站文章分类
	// setModelAttribute("categoriesTagList",
	// articleBlogView.getCategoriesTagList());
	// // 上一篇博文
	// setModelAttribute("previousSimpleBlog",
	// articleBlogView.getPreviousSimpleBlog());
	// // 下一篇博文
	// setModelAttribute("nextSimpleBlog", articleBlogView.getNextSimpleBlog());
	//
	// // 文章标签articleTagIds
	//
	// } catch (final DataVerifyAnomalyException e) {
	// logger.error("【查看文章详细】出现错误!", e);
	// // 文章出错,回首页
	// return redirectAction("/");
	// }
	//
	// return "article/article_blog_detail";
	// }
	//
	// /**
	// * 写/编辑文章页面
	// *
	// * @param articleBlogId
	// * 文章扩展ID
	// */
	// @RequestMapping(value = "/article/published.html", method = {
	// RequestMethod.GET })
	// public String getPublishedPage(@RequestParam(value = "articleBlogId",
	// defaultValue = "") final String articleBlogId,
	// final HttpServletResponse response) {
	// setModelAttribute(WebAttributes.ACTION, "articleWrite");
	//
	// /* 存在文章则获取文章数据 */
	// // mode模式: 0新增, 1修改
	// setModelAttribute("title", "创建我的新文章");
	// setModelAttribute("mode", 0);
	// if (StringUtil.isNotEmpty(articleBlogId)) {
	// try {
	// // 获取文章的基本信息
	// final ArticleBlogBo articleBlog =
	// articleBlogService.findByBlogId(articleBlogId);
	// if (articleBlog != null) {
	// setModelAttribute("mode", 1);
	// setModelAttribute("title", "编辑文章:" + articleBlog.getArticleTitle());
	//
	// setModelAttribute("articleBlog", articleBlog);
	// } else {
	// // 文章不存在,回首页
	// return redirectAction("/");
	// }
	// } catch (final Exception e) {
	// logger.error("【发布文章】出现错误!", e);
	// // 回首页
	// return redirectAction("/");
	// }
	// }
	//
	// /* 获取固定数据字典 */
	// // 获取 '文章标题类型' 列表
	// final List<ArticleSelType> articleSelTypes =
	// Lists.newArrayList(ArticleSelType.values());
	// setModelAttribute("articleSelTypes", articleSelTypes);
	//
	// // 获取'文章分类' 列表 delete
	// final List<CategoriesTagBo> categoryTags =
	// categoriesTagService.findArticleCategoriesTree();
	// setModelAttribute("categoryTags", categoryTags);
	//
	// return "user/article_write";
	// }
	//
	// /**
	// * 写/编辑文章时根据文章标题和文章内容动态获取'推荐标签'
	// */
	// @RequestMapping(value = "/article/published.html", method = {
	// RequestMethod.POST })
	// @ResponseBody
	// public ListResponse<String> getPublishedTag(final String gettag, final
	// HttpServletResponse response) {
	// setModelAttribute(WebAttributes.ACTION, "getPublishedTag");
	//
	// final ListResponse<String> resp = new ListResponse<>();
	// if (StringUtil.isEmpty(gettag)) {
	// resp.setData(Collections.emptyList());
	// return resp;
	// }
	//
	// try {
	// // '推荐标签'直接返回推荐的标签名称,不尽兴name的封装
	// resp.setData(Lists.newArrayList("Server"));
	// } catch (final Exception e) {
	// resp.setCode(BaseErrorType.SYSTEM_BUSY.getCode());
	// resp.setMessage(BaseErrorType.SYSTEM_BUSY.getMessage());
	// }
	//
	// return resp;
	// }
	//
	// /**
	// * 发布文章操作
	// *
	// * @param articlePublishedRequest
	// * 文章发布请求实体
	// * @param response
	// * HttpServletResponse
	// * @return url
	// */
	// @RequestMapping(value = { "/article/postedit/" }, method = {
	// RequestMethod.POST }, produces = MediaType.APPLICATION_JSON_VALUE)
	// @ResponseBody
	// public NormalResponse<String> publishedArticle(@Valid final
	// ArticlePublishedCondition condition,
	// final HttpServletResponse httpResponse) {
	// logger.info("【发布文章】请求参数:{}.", condition);
	//
	// final NormalResponse<String> response = new NormalResponse<>();
	// try {
	// final UserAgentResource agent = getCurrentUserAgent();
	//
	// // 从session中获取uid
	// final String uid = "yuanyang";
	//
	// String articleBlogId;
	// if (StringUtil.isNotEmpty(condition.getArticleBlogId())) {
	// // 修改
	// articleBlogId = articleManageService.editBlog(uid, condition, agent);
	// } else {
	// // 新增
	// articleBlogId = articleManageService.addBlog(uid, condition, agent);
	// }
	//
	// response.setData(articleBlogId);
	// } catch (final DataVerifyAnomalyException e) {
	// logger.error("【发布文章】出现错误!", e);
	// response.setCode(e.getErrorCode());
	// response.setMessage(e.getErrorMsg());
	// }
	//
	// return response;
	// }

}
