package com.alta189.simple.gallery;

import com.alta189.simple.gallery.objects.Album;
import com.alta189.simple.gallery.objects.Image;
import com.alta189.simplesave.Database;
import com.alta189.simplesave.DatabaseFactory;
import com.alta189.simplesave.exceptions.ConnectionException;
import com.alta189.simplesave.exceptions.TableRegistrationException;
import com.alta189.simplesave.h2.H2Configuration;

import java.io.File;

public class DatabaseManager {
	private final File file = new File("data/h2_database.db");
	private Database database;

	public void init() {
		H2Configuration config = new H2Configuration();
		config.setDatabase(file.getAbsolutePath().substring(0, file.getAbsolutePath().indexOf(".db")));
		database = DatabaseFactory.createNewDatabase(config);
	}

	public void load() {
		try {
			database.registerTable(Album.class);
			database.registerTable(Image.class);
		} catch (TableRegistrationException e) {
			e.printStackTrace();
		}
	}

	public void connect() {
		try {
			database.connect();
		} catch (ConnectionException e) {
			e.printStackTrace();
		}
	}

	public Database getDatabase() {
		return database;
	}

	public void setDatabase(Database database) {
		this.database = database;
	}
}
