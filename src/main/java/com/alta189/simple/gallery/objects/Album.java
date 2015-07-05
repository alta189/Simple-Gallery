package com.alta189.simple.gallery.objects;

import com.alta189.simplesave.Field;
import com.alta189.simplesave.Id;
import com.alta189.simplesave.Table;
import com.google.gson.annotations.Expose;
import org.joda.time.DateTime;

@Table("fields")
public class Album {
	@Id
	@Expose
	private int id;
	@Field
	@Expose
	private String title;
	@Field
	@Expose
	private String subtitle;
	@Field
	@Expose
	private DateTime uploaded;

	public int getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSubtitle() {
		return subtitle;
	}

	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}

	public DateTime getUploaded() {
		return uploaded;
	}

	public void setUploaded(DateTime uploaded) {
		this.uploaded = uploaded;
	}
}
