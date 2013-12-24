package lemon.weixin.test.message;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import lemon.shared.api.MmtAPI;
import lemon.shared.customer.Customer;
import lemon.shared.customer.persistence.CustomerRepository;
import lemon.shared.message.MsgManager;
import lemon.shared.message.metadata.TextMessage;
import lemon.shared.message.metadata.send.NewsMessage;
import lemon.shared.message.metadata.specific.weixin.WXVideoMessage;
import lemon.shared.message.metadata.specific.weixin.WXVoiceMessage;
import lemon.shared.message.parser.NewsMsgParser;
import lemon.shared.message.parser.TextMsgParser;
import lemon.shared.message.parser.weixin.VideoMsgParser;
import lemon.shared.message.parser.weixin.VoiceMsgParser;
import lemon.weixin.config.WeiXin;
import lemon.weixin.config.bean.AccountType;
import lemon.weixin.config.bean.WeiXinConfig;
import lemon.weixin.config.mapper.WXConfigMapper;
import lemon.weixin.message.processor.SimpleWeiXinMsgProcessor;
import lemon.weixin.test.base.BaseWeiXinTest;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.github.cjm0000000.mmt.core.config.Status;
import com.github.cjm0000000.mmt.core.service.ServiceType;

public class SimpleWeiXinMsgProcessorTest extends BaseWeiXinTest {
	private final String Subscribe_msg = "Welcome to Subscribe Lemon Test.";
	private final String Welcome_msg = "Welcome";
	private final String TOKEN = "1230!)*!)*#)!*Q)@)!*";
	private final String MMT_TOKEN = "lemonxoewfnvowensofcewniasdmfo";
	private final String bizClass = SimpleWeiXinMsgProcessor.class.getName();
	private final int CUST_ID = -5744;
	@Autowired
	private MsgManager msgManager;
	@Autowired
	private CustomerRepository customerMapper;
	@Autowired
	private WXConfigMapper	wxConfigMapper;
	@Autowired @Qualifier("weiXinAPI")
	private MmtAPI api;
	@Autowired @Qualifier("MMT_text")
	private TextMsgParser textMsgParser;
	@Autowired @Qualifier("MMT_news")
	private NewsMsgParser newsMsgParser;
	@Autowired @Qualifier("WEIXIN_video")
	private VideoMsgParser videoMsgParser;
	@Autowired @Qualifier("WEIXIN_voice")
	private VoiceMsgParser voiceMsgParser;
	
	@Before
	public void init() {
		//add customer
		Customer cust = customerMapper.getCustomer(CUST_ID);
		if(cust == null){
			cust = new Customer();
			cust.setCust_id(CUST_ID);
			cust.setCust_name("Test");
			cust.setMemo("");
			cust.setStatus(Status.AVAILABLE);
			customerMapper.addCustomer(cust);
			assertNotEquals(cust.getCust_id(), 0);
		}
		
		//add WeiXin configure
		WeiXinConfig cfg = wxConfigMapper.get(CUST_ID);
		if(null == cfg){
			cfg = new WeiXinConfig();
			cfg.setCust_id(CUST_ID);
			cfg.setToken(TOKEN);
			cfg.setApi_url(MMT_TOKEN);
			cfg.setWx_account("lemon_test");
			cfg.setAppid("");
			cfg.setSecret("");
			cfg.setAccount_type(AccountType.DY);
			cfg.setBiz_class(bizClass);
			cfg.setSubscribe_msg(Subscribe_msg);
			cfg.setWelcome_msg(Welcome_msg);
			wxConfigMapper.save(cfg);
			assertNotEquals(cfg.getCust_id(), 0);
		}
		WeiXin.init();
		WeiXin.setConfig(cfg);
	}
	@Test
	public void testSaveTextMsg(){
		String txtMsg = "<xml><ToUserName><![CDATA[gh_de370ad657cf]]></ToUserName><FromUserName><![CDATA[ot9x4jpm4x_rBrqacQ8hzikL9D-M]]></FromUserName><CreateTime>1378050293</CreateTime><MsgType><![CDATA[text]]></MsgType><Content><![CDATA[ss]]></Content><MsgId>5918680940678217795</MsgId></xml>";
		TextMessage msg = textMsgParser.toMsg(txtMsg);
		msg.setService_type(ServiceType.WEIXIN);
		msgManager.saveRecvTextMsg(msg);
	}
	@Test
	public void parserMsgType() throws JDOMException, IOException{
		String msg = "<xml><ToUserName><![CDATA[gh_de370ad657cf]]></ToUserName><FromUserName><![CDATA[ot9x4jpm4x_rBrqacQ8hzikL9D-M]]></FromUserName><CreateTime>1378040271</CreateTime><MsgType><![CDATA[voice]]></MsgType><MediaId><![CDATA[PG_BHErDUcBylPzSDZHgpGa34axYmbe3_HGaQ7VCYQa_ihn9ON8lpevua76VMsHj]]></MediaId><Format><![CDATA[amr]]></Format><MsgId>5918637896515977279</MsgId><Recognition><![CDATA[]]></Recognition></xml>";
		InputStream is = new ByteArrayInputStream(msg.getBytes());
		Document doc = new SAXBuilder().build(is);
		Element msgType = doc.getRootElement().getChild("MsgType");
		Assert.assertTrue("voice".equals(msgType.getValue()));
	}
	@Test
	public void textMsgTest(){
		String txtMsg = "<xml><ToUserName><![CDATA[gh_de370ad657cf]]></ToUserName><FromUserName><![CDATA[ot9x4jpm4x_rBrqacQ8hzikL9D-M]]></FromUserName><CreateTime>1378050293</CreateTime><MsgType><![CDATA[text]]></MsgType><Content><![CDATA[ss]]></Content><MsgId>5918680940678217795</MsgId></xml>";
		String result = api.processMsg(MMT_TOKEN, txtMsg);
		TextMessage msg = textMsgParser.toMsg(result);
		assertEquals(msg.getContent(), Welcome_msg);
	}
	@Test
	public void subscribeTest(){
		String recvMsg = "<xml><ToUserName><![CDATA[gh_de370ad657cf]]></ToUserName><FromUserName><![CDATA[ot9x4jpm4x_rBrqacQ8hzikL9D-M]]></FromUserName><CreateTime>1378090586</CreateTime><MsgType><![CDATA[event]]></MsgType><Event><![CDATA[subscribe]]></Event><EventKey><![CDATA[]]></EventKey></xml>";
		String result = api.processMsg(MMT_TOKEN, recvMsg);
		TextMessage msg = textMsgParser.toMsg(result);
		assertEquals(msg.getContent(), Subscribe_msg);
	}
	@Test
	public void unsubscribe(){
		String recvMsg = "<xml><ToUserName><![CDATA[gh_de370ad657cf]]></ToUserName><FromUserName><![CDATA[ot9x4jpm4x_rBrqacQ8hzikL9D-M]]></FromUserName><CreateTime>1378090569</CreateTime><MsgType><![CDATA[event]]></MsgType><Event><![CDATA[unsubscribe]]></Event><EventKey><![CDATA[]]></EventKey></xml>";
		String result = api.processMsg(MMT_TOKEN, recvMsg);
		assertEquals(result, null);
	}
	@Test
	public void linkMsgTest(){
		String recvMsg = "<xml><ToUserName><![CDATA[weixin]]></ToUserName><FromUserName><![CDATA[lemon]]></FromUserName><CreateTime>1378090569</CreateTime><MsgType><![CDATA[link]]></MsgType><MsgId>1024102410241024</MsgId><Title><![CDATA[Link \"TEST\" Title]]></Title><Description><![CDATA[Link DESC]]></Description><Url><![CDATA[http://www.163.com/s/a/d/f/a]]></Url></xml>";
		String result = api.processMsg(MMT_TOKEN, recvMsg);
		TextMessage msg = textMsgParser.toMsg(result);
		assertEquals(msg.getContent(), "亲，我暂时无法识别链接信息哦，您可以给我发文字消息。");
	}
	@Test
	public void imageMsgTest(){
		String recvMsg = "<xml><ToUserName><![CDATA[gh_de370ad657cf]]></ToUserName><FromUserName><![CDATA[ot9x4jpm4x_rBrqacQ8hzikL9D-M]]></FromUserName><CreateTime>1378091075</CreateTime><MsgType><![CDATA[image]]></MsgType><PicUrl><![CDATA[http://mmsns.qpic.cn/mmsns/QXd6JDcZQ1ls9utpyRLS45ib4XPBm8jLD27oeCgOrlsjgJuUictQTHXw/0]]></PicUrl><MsgId>5918856098034483283</MsgId><MediaId><![CDATA[ZTjFiu7uLSfqupgRn2z4uZT8JqulZXKntm6ERVXrFtcppQOTF9x8Ow-cCb1yoUoy]]></MediaId></xml>";
		String result = api.processMsg(MMT_TOKEN, recvMsg);
		TextMessage msg = textMsgParser.toMsg(result);
		assertEquals(msg.getContent(), "亲，我暂时无法识别图片信息哦，您可以给我发文字消息。");
	}
	@Test
	public void locationMsgTest(){
		String recvMsg = "<xml><ToUserName><![CDATA[gh_de370ad657cf]]></ToUserName><FromUserName><![CDATA[ot9x4jpm4x_rBrqacQ8hzikL9D-M]]></FromUserName><CreateTime>1378091153</CreateTime><MsgType><![CDATA[location]]></MsgType><Location_X>30.278790</Location_X><Location_Y>120.145454</Location_Y><Scale>20</Scale><Label><![CDATA[浙江省杭州市: 310000]]></Label><MsgId>5918856433041932373</MsgId></xml>";
		String result = api.processMsg(MMT_TOKEN, recvMsg);
		NewsMessage msg = newsMsgParser.toMsg(result);
		assertEquals(msg.getArticleCount(), 5);
	}
	
	@Test
	@Ignore
	public void videoMsgTest(){
		String recvMsg = "<xml><ToUserName><![CDATA[gh_de370ad657cf]]></ToUserName><FromUserName><![CDATA[ot9x4jpm4x_rBrqacQ8hzikL9D-M]]></FromUserName><CreateTime>1377961745</CreateTime><MsgType><![CDATA[video]]></MsgType><MediaId><![CDATA[Iy6-lX7dSLa45ztf6AVdjTDcHTWLk3C80VHMGi40HfI1CnpPqixCb6FUJ2ZG4wNd]]></MediaId><ThumbMediaId><![CDATA[gJNpZwX41lZ651onCiBzaYkYOTrqDC_v6oBY9TNocYCMWHG7Zsp67-jq-NRQS1Uk]]></ThumbMediaId><MsgId>5918300629914091537</MsgId></xml>";
		String result = api.processMsg(MMT_TOKEN, recvMsg);
		WXVideoMessage msg = videoMsgParser.toMsg(result);
		assertEquals(msg.getThumbMediaId(), "gJNpZwX41lZ651onCiBzaYkYOTrqDC_v6oBY9TNocYCMWHG7Zsp67-jq-NRQS1Uk");
		assertEquals(msg.getMediaId(), "Iy6-lX7dSLa45ztf6AVdjTDcHTWLk3C80VHMGi40HfI1CnpPqixCb6FUJ2ZG4wNd");
	}
	@Test
	@Ignore
	public void voiceMsgTest(){
		String recvMsg = "<xml><ToUserName><![CDATA[gh_de370ad657cf]]></ToUserName><FromUserName><![CDATA[ot9x4jpm4x_rBrqacQ8hzikL9D-M]]></FromUserName><CreateTime>1378040271</CreateTime><MsgType><![CDATA[voice]]></MsgType><MediaId><![CDATA[PG_BHErDUcBylPzSDZHgpGa34axYmbe3_HGaQ7VCYQa_ihn9ON8lpevua76VMsHj]]></MediaId><Format><![CDATA[amr]]></Format><MsgId>5918637896515977279</MsgId><Recognition><![CDATA[]]></Recognition></xml>";
		String result = api.processMsg(MMT_TOKEN, recvMsg);
		WXVoiceMessage msg = voiceMsgParser.toMsg(result);
		assertEquals(msg.getFormat(), "amr");
		assertEquals(msg.getMediaId(), "PG_BHErDUcBylPzSDZHgpGa34axYmbe3_HGaQ7VCYQa_ihn9ON8lpevua76VMsHj");
		assertEquals(msg.getRecognition(), "");
	}
	
}
