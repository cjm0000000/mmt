package com.github.cjm0000000.mmt.web.test;

/**
 * 负责即将到来的任务
 * @author lemon
 * @version 1.1
 *
 */
public abstract class IncomingTasks {
	//TODO [2013-11-22] Using JSR 330 Standard Annotations
	// [2013-11-23] Spring Test加入
	
	/****************************************************
	 * 													*	
	 * 			  new features for WeiXin				*
	 * 													*
	 * **************************************************/
	// [2013-11-23] 1-1 微信新功能开发: 上传多媒体文件，见 http://mp.weixin.qq.com/wiki/index.php?title=%E4%B8%8A%E4%BC%A0%E4%B8%8B%E8%BD%BD%E5%A4%9A%E5%AA%92%E4%BD%93%E6%96%87%E4%BB%B6
	//TODO [2013-11-23] 1-2 微信新功能开发: 下载多媒体文件，见 http://mp.weixin.qq.com/wiki/index.php?title=%E4%B8%8A%E4%BC%A0%E4%B8%8B%E8%BD%BD%E5%A4%9A%E5%AA%92%E4%BD%93%E6%96%87%E4%BB%B6
	//TODO [2013-11-23] 2-1 微信新功能开发: 扫描带参数二维码事件
	//TODO [2013-11-23] 2-2 微信新功能开发: 上报地理位置事件
	//TODO [2013-11-23] 2-2 微信新功能开发: 接收语音识别结果  见 http://mp.weixin.qq.com/wiki/index.php?title=%E6%8E%A5%E6%94%B6%E8%AF%AD%E9%9F%B3%E8%AF%86%E5%88%AB%E7%BB%93%E6%9E%9C
	//TODO [2013-11-23] 3 主动发送消息	见http://mp.weixin.qq.com/wiki/index.php?title=%E5%8F%91%E9%80%81%E5%AE%A2%E6%9C%8D%E6%B6%88%E6%81%AF
	//					3-1 发送文本消息
	//					3-2 发送图片消息
	//					3-3 发送语音消息
	//					3-4 发送视频消息
	//					3-5 发送音乐消息
	//					3-5 发送图文消息
	//TODO [2013-11-23] 4 用户管理 	见http://mp.weixin.qq.com/wiki/index.php?title=%E5%88%86%E7%BB%84%E7%AE%A1%E7%90%86%E6%8E%A5%E5%8F%A3
	//					4-1 查询分组
	//					4-2 创建分组
	//					4-3 修改分组名
	//					4-4 移动用户分组
	//TODO [2013-11-23] 5 获取用户基本信息	见http://mp.weixin.qq.com/wiki/index.php?title=%E8%8E%B7%E5%8F%96%E7%94%A8%E6%88%B7%E5%9F%BA%E6%9C%AC%E4%BF%A1%E6%81%AF
	//TODO [2013-11-23] 6 获取关注者列表 	
	//TODO [2013-11-23] 7 获取用户地理位置	见http://mp.weixin.qq.com/wiki/index.php?title=%E8%8E%B7%E5%8F%96%E7%94%A8%E6%88%B7%E5%9C%B0%E7%90%86%E4%BD%8D%E7%BD%AE
	//TODO [2013-11-23] 8 网页授权获取用户基本信息 	见http://mp.weixin.qq.com/wiki/index.php?title=%E7%BD%91%E9%A1%B5%E6%8E%88%E6%9D%83%E8%8E%B7%E5%8F%96%E7%94%A8%E6%88%B7%E5%9F%BA%E6%9C%AC%E4%BF%A1%E6%81%AF
	//					8-1 第一步：用户同意授权，获取code
	//					8-2 第二步：通过code换取网页授权access_token
	//					8-3 第三步：刷新access_token（如果需要）
	//					8-4 第四步：拉取用户信息(需scope为 snsapi_userinfo)
	//TODO [2013-11-23] 9 生成带参数的二维码 	见 http://mp.weixin.qq.com/wiki/index.php?title=%E7%94%9F%E6%88%90%E5%B8%A6%E5%8F%82%E6%95%B0%E7%9A%84%E4%BA%8C%E7%BB%B4%E7%A0%81
	
	//=======================WeiXin JS 接口=============================
	//TODO [2013-11-23] 1 网页获取用户网络状态	见http://mp.weixin.qq.com/wiki/index.php?title=%E7%BD%91%E9%A1%B5%E8%8E%B7%E5%8F%96%E7%94%A8%E6%88%B7%E7%BD%91%E7%BB%9C%E7%8A%B6%E6%80%81
	//TODO [2013-11-23] 2 隐藏微信中网页右上角按钮		见 http://mp.weixin.qq.com/wiki/index.php?title=%E9%9A%90%E8%97%8F%E5%BE%AE%E4%BF%A1%E4%B8%AD%E7%BD%91%E9%A1%B5%E5%8F%B3%E4%B8%8A%E8%A7%92%E6%8C%89%E9%92%AE
	//TODO [2013-11-23] 3 隐藏微信中网页底部导航栏		见http://mp.weixin.qq.com/wiki/index.php?title=%E9%9A%90%E8%97%8F%E5%BE%AE%E4%BF%A1%E4%B8%AD%E7%BD%91%E9%A1%B5%E5%BA%95%E9%83%A8%E5%AF%BC%E8%88%AA%E6%A0%8F
}
