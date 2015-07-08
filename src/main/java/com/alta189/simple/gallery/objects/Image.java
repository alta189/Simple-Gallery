package com.alta189.simple.gallery.objects;

import com.alta189.simple.gallery.SimpleGalleryServer;
import com.alta189.simplesave.Field;
import com.alta189.simplesave.Id;
import com.alta189.simplesave.Table;
import com.google.gson.annotations.Expose;
import org.joda.time.DateTime;

@Table("images")
public class Image {
	@Id
	@Expose
	private int id;
	@Field
	@Expose
	private String imageFile;
	@Field
	@Expose
	private String description;
	@Field
	@Expose
	private int user;
	@Field
	@Expose
	private DateTime uploaded;
	@Field
	@Expose
	private int album;

	public int getId() {
		return id;
	}

	public String getImageFile() {
		return imageFile;
	}

	public void setImageFile(String imageFile) {
		this.imageFile = imageFile;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public User getUser() {
		return SimpleGalleryServer.getDatabase().select(User.class).where().equal("id", user).execute().findOne();
	}

	public int getUserId() {
		return user;
	}

	public void setUser(User user) {
		this.user = user.getId();
	}

	public void setUser(int user) {
		this.user = user;
	}

	public DateTime getUploaded() {
		return uploaded;
	}

	public void setUploaded(DateTime uploaded) {
		this.uploaded = uploaded;
	}

	public int getAlbum() {
		return album;
	}

	public void setAlbum(int album) {
		this.album = album;
	}
}
