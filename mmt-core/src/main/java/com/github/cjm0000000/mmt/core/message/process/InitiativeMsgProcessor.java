package com.github.cjm0000000.mmt.core.message.process;

import com.github.cjm0000000.mmt.core.access.JSONResponse;
import com.github.cjm0000000.mmt.core.message.recv.IVideo;
import com.github.cjm0000000.mmt.core.message.send.initiative.ImageMessage;
import com.github.cjm0000000.mmt.core.message.send.initiative.MusicMessage;
import com.github.cjm0000000.mmt.core.message.send.initiative.NewsMessage;
import com.github.cjm0000000.mmt.core.message.send.initiative.TextMessage;
import com.github.cjm0000000.mmt.core.message.send.initiative.VoiceMessage;

/**
 * Initiative message processor
 * @author lemon
 * @version 2.0
 *
 */
public interface InitiativeMsgProcessor extends InitiativeProcessor {
	
	/**
	 * send text message manual
	 * @param msg
	 * @return
	 */
	JSONResponse sendTextMsg(TextMessage msg);
	
	/**
	 * send image message manual
	 * @param msg
	 * @return
	 */
	JSONResponse sendImageMsg(ImageMessage msg);
	
	/**
	 * send voice message manual
	 * @param msg
	 * @return
	 */
	JSONResponse sendVoiceMsg(VoiceMessage msg);
	
	/**
	 * send video message manual
	 * @param msg
	 * @return
	 */
	JSONResponse sendVideoMsg(IVideo msg);
	
	/**
	 * send music message manual
	 * @param msg
	 * @return
	 */
	JSONResponse sendMusicMsg(MusicMessage msg);
	
	/**
	 * send news message manual
	 * @param msg
	 * @return
	 */
	JSONResponse sendNewsMsg(NewsMessage msg);
}
