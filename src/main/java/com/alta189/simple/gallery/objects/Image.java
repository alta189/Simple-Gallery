package com.alta189.simple.gallery.objects;

import com.alta189.simplesave.Field;
import com.alta189.simplesave.Id;
import com.alta189.simplesave.Table;
import com.google.gson.annotations.Expose;

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

	public int getAlbum() {
		return album;
	}

	public void setAlbum(int album) {
		this.album = album;
	}
}
