package com.github.cjm0000000.mmt.shared.media;

import com.github.cjm0000000.mmt.core.access.JSONResponse;
import com.github.cjm0000000.mmt.core.message.process.InitiativeProcessor;

/**
 * media APIs
 * @author lemon
 * @version 2.0
 *
 */
public interface MediaAPI extends InitiativeProcessor {
	/**
     * Upload media to remote server
     * @param type
     * @param file
     * @param fileName
     * @return
     */
	JSONResponse uploadMedia(String type, byte[] file, String fileName);
	
	/**
	 * download media from remote server
	 * @param media_id
	 * @return
	 */
	JSONResponse downloadMedia(String media_id);
}
