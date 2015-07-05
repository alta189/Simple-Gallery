package com.alta189.simple.gallery.api;

import com.alta189.auto.spark.Controller;
import com.alta189.auto.spark.ResourceMapping;
import com.alta189.simple.gallery.SimpleGalleryServer;
import com.alta189.simple.gallery.exceptions.NotFoundException;
import org.apache.commons.io.IOUtils;
import spark.Request;
import spark.Response;

import javax.activation.MimetypesFileTypeMap;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@Controller
public class FileServ {
	private final MimetypesFileTypeMap mimetypesFileTypeMap = new MimetypesFileTypeMap();

	@ResourceMapping("/images/*")
	public void imageRender(Request request, Response response) throws NotFoundException {
		if (request.splat() == null || request.splat().length != 1) {
			throw new NotFoundException();
		}

		File file = new File(SimpleGalleryServer.IMAGES_DIRECTORY, request.splat()[0]);
		if (!file.exists() || !file.isFile()) {
			throw new NotFoundException();
		}

		byte[] out;
		try {
			out = IOUtils.toByteArray(new FileInputStream(file));
			response.raw().setContentType(mimetypesFileTypeMap.getContentType(file));
			response.raw().getOutputStream().write(out, 0, out.length);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
