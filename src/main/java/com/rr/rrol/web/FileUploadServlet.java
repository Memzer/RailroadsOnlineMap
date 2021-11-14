package com.rr.rrol.web;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import com.rr.rrol.game.render.MapRenderer;
import com.rr.rrol.se.io.SaveReader;
import com.rr.rrol.se.model.Save;

import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

@WebServlet(asyncSupported = true, name = "FileUploadServlet", urlPatterns = {"/map"})
@MultipartConfig(
		fileSizeThreshold = 1024*1024*1,
		maxFileSize = 1024*1024*1,
		maxRequestSize = 1024*1024*1
)
public class FileUploadServlet extends HttpServlet {

	private static final long serialVersionUID = 5651572001261083480L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			Part filePart = request.getPart("file");
			InputStream in = filePart.getInputStream();
			Save save = SaveReader.read(in);
			MapRenderer renderer = new MapRenderer();
			BufferedImage image = renderer.getImage(save);
			ServletOutputStream os = response.getOutputStream();
			ImageIO.write(image, "gif", os);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
