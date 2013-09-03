package lemon.weixin.dao;

import static org.junit.Assert.*;
import lemon.weixin.bean.message.Article;
import lemon.weixin.bean.message.MusicMessage;
import lemon.weixin.bean.message.NewsMessage;
import lemon.weixin.bean.message.TextMessage;
import lemon.weixin.biz.parser.MusicMsgParser;
import lemon.weixin.biz.parser.NewsMsgParser;
import lemon.weixin.biz.parser.TextMsgParser;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@RunWith(JUnit4.class)
public class SendMsgDetailTest {
	private WXSendMsgDetailMapper msgMapper;
	private ApplicationContext acx;
	@Before
	public void init() {
		String[] resource = { "classpath:spring-db.xml",
				"classpath:spring-dao.xml", "classpath:spring-service.xml" };
		acx = new ClassPathXmlApplicationContext(resource);
		msgMapper = acx.getBean(WXSendMsgDetailMapper.class);
		assertNotNull(msgMapper);
	}
	@Test
	public void testSaveTextMsg(){
		String msg = "<xml><ToUserName><![CDATA[gh_de370ad657cf]]></ToUserName><FromUserName><![CDATA[ot9x4jpm4x_rBrqacQ8hzikL9D-M]]></FromUserName><CreateTime>1378050293</CreateTime><MsgType><![CDATA[text]]></MsgType><Content><![CDATA[ss]]></Content><MsgId>5918680940678217795</MsgId></xml>";
		TextMessage msgObj = acx.getBean(TextMsgParser.class).toMsg(msg);
		msgObj.setCust_id(10);
		msgMapper.saveMsgDetail(msgObj);
		msgMapper.saveTextMsgDetail(msgObj);
		assertNotEquals(msgObj.getId(),0);
	}
	
	@Test
	public void testSaveMusicMsg(){
		String msg = "<xml><ToUserName><![CDATA[weixin]]></ToUserName><FromUserName><![CDATA[lemon]]></FromUserName><CreateTime>1377754486787</CreateTime><MsgType><![CDATA[music]]></MsgType><MusicUrl><![CDATA[http://music.baidu.com/a/a/d.mp3]]></MusicUrl><HQMusicUrl><![CDATA[HQmusic  ss s]]></HQMusicUrl></xml>";
		MusicMessage msgObj = acx.getBean(MusicMsgParser.class).toMsg(msg);
		msgObj.setCust_id(10);
		msgMapper.saveMsgDetail(msgObj);
		msgMapper.saveMusicMsgDetail(msgObj);
		assertNotEquals(msgObj.getId(),0);
	}
	
	@Test
	public void testSaveNewsMsg(){
		String msg = "<xml><ToUserName><![CDATA[weixin]]></ToUserName><FromUserName><![CDATA[lemon]]></FromUserName><CreateTime>1377754656922</CreateTime><MsgType><![CDATA[news]]></MsgType><ArticleCount>2</ArticleCount><Articles><item><Title><![CDATA[Title A1]]></Title><Description><![CDATA[DESC A1]]></Description><PicUrl><![CDATA[pic.taobao.com/aaas/asdf.jpg]]></PicUrl><Url><![CDATA[http://www.baidu.com]]></Url></item><item><Title><![CDATA[Title A2]]></Title><Description><![CDATA[DESC A2]]></Description><PicUrl><![CDATA[pic2.taobao.com/aaas/asdf222.jpg]]></PicUrl><Url><![CDATA[http://www.yousas.com]]></Url></item></Articles></xml>";
		NewsMessage msgObj = acx.getBean(NewsMsgParser.class).toMsg(msg);
		msgObj.setCust_id(10);
		msgMapper.saveMsgDetail(msgObj);
		msgMapper.saveNewsMsgDetail(msgObj);
		for (Article article : msgObj.getArticles()) {
			article.setId(msgObj.getId());
			msgMapper.saveNewsArticles(article);
		}
		assertNotEquals(msgObj.getId(),0);
	}
}
