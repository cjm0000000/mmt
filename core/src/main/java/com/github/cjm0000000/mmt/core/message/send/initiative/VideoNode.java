package com.github.cjm0000000.mmt.core.message.send.initiative;

/**
 * video node
 * @author lemon
 * @version 2.0
 *
 */
public final class VideoNode extends MediaNode {
	private String title;
	private String description;
	
	VideoNode(){}
	
	public VideoNode(String media_id, String title, String description){
		super(media_id);
		this.title = title;
		this.description = description;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
}
