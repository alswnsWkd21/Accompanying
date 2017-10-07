package smc.minjoon.accompanying;

import android.graphics.Bitmap;

public class LockItem {// 한쌍을 저장할 공간 타이틀 링크 desc 저장공간
	private String title;
	private String link;
	private String desc;
	private String imagelink;

	public LockItem() {
		super();
	}
	public LockItem(String title, String link, String desc, String imagelink) {
		setTitle(title);
		setLink(link);
		setDesc(desc);
		setImagelink(imagelink);
	}

	public String getImagelink() {
		return imagelink;
	}

	public void setImagelink(String imagelink) {
		this.imagelink = imagelink;
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	@Override
	public String toString() {
		return getTitle();//타이틀만 갔다가 찍어라 만약에 기호를 앞에다 붙이면 기호 붙어서 나옴
	}
}
