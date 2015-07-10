package com.alta189.simple.gallery.objects;

import com.alta189.simple.gallery.SimpleGalleryServer;
import com.alta189.simplesave.Field;
import com.alta189.simplesave.Id;
import com.alta189.simplesave.PostInitialize;
import com.alta189.simplesave.Table;
import com.google.gson.annotations.Expose;
import org.joda.time.DateTime;

import java.util.List;

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
	private int user;
	@Field
	@Expose
	private DateTime created;
	@Field
	@Expose
	private boolean hidden;
	@Expose
	private List<Image> images;

	@PostInitialize
	public void refresh() {
		images = SimpleGalleryServer.getDatabase().select(Image.class).where().equal("album", id).execute().find();
	}

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

	public User getUser() {
		return SimpleGalleryServer.getDatabase().select(User.class).where().equal("id", user).execute().findOne();
	}

	public void setUser(int user) {
		this.user = user;
	}

	public int getUserId() {
		return user;
	}

	public void setUser(User user) {
		this.user = user.getId();
	}

	public DateTime getCreated() {
		return created;
	}

	public void setCreated(DateTime created) {
		this.created = created;
	}

	public boolean isHidden() {
		return hidden;
	}

	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}

	public List<Image> getImages() {
		return images;
	}
}
