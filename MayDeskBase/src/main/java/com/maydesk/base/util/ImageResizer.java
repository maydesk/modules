/* 
 * This file is copyright of PROFIDESK (www.profidesk.net)
 * Copyright (C) 2009
 * All rights reserved
 */
package com.maydesk.base.util;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.swing.ImageIcon;

import nextapp.echo.webcontainer.util.PngEncoder;

import com.maydesk.base.model.MMediaFile;

/**
 * @author Alejandro Salas
 */
public class ImageResizer {

	public static byte[] resize(byte[] originalFile, int newWidth, float quality) throws IOException {

		if (quality < 0 || quality > 1) {
			throw new IllegalArgumentException("Quality has to be between 0 and 1");
		}

		ImageIcon ii = new ImageIcon(originalFile);
		Image i = ii.getImage();
		Image resizedImage = null;

		int iWidth = i.getWidth(null);
		int iHeight = i.getHeight(null);

		if (iWidth > iHeight) {
			resizedImage = i.getScaledInstance(newWidth, (newWidth * iHeight) / iWidth, Image.SCALE_SMOOTH);
		} else {
			resizedImage = i.getScaledInstance((newWidth * iWidth) / iHeight, newWidth, Image.SCALE_SMOOTH);
		}

		// This code ensures that all the pixels in the image are loaded.
		Image temp = new ImageIcon(resizedImage).getImage();

		// Create the buffered image.
		BufferedImage bufferedImage = new BufferedImage(temp.getWidth(null), temp.getHeight(null), BufferedImage.TYPE_INT_RGB);

		// Copy image to buffered image.
		Graphics g = bufferedImage.createGraphics();

		// Clear background and paint the image.
		g.setColor(Color.white);
		g.fillRect(0, 0, temp.getWidth(null), temp.getHeight(null));
		g.drawImage(temp, 0, 0, null);
		g.dispose();

		// Soften.
		float softenFactor = 0.05f;
		float[] softenArray = { 0, softenFactor, 0, softenFactor, 1 - (softenFactor * 4), softenFactor, 0, softenFactor, 0 };
		Kernel kernel = new Kernel(3, 3, softenArray);
		ConvolveOp cOp = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
		bufferedImage = cOp.filter(bufferedImage, null);

		// Write the jpeg to a file.
		ByteArrayOutputStream out = new ByteArrayOutputStream();

		// Encodes image as a JPEG data stream
		// JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
		// JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(bufferedImage);
		// param.setQuality(quality, true);
		// encoder.setJPEGEncodeParam(param);
		// encoder.encode(bufferedImage);

		PngEncoder encoder = new PngEncoder(bufferedImage, true, null, 0);
		encoder.encode(out);
		return out.toByteArray();
	}

	public static int xxx;

	public static byte[] convertToDropDownEntry(MMediaFile img, String text1, String text2) throws IOException {

		// Create the buffered image.
		BufferedImage bufferedImage = new BufferedImage(180, 32, BufferedImage.TYPE_BYTE_INDEXED);
		// Copy image to buffered image.
		Graphics g = bufferedImage.createGraphics();

		// Clear background and paint the image.
		// g.setColor(new Color(255, 255, 255, 255));
		g.setColor(new Color(0, 0, 255, 255));
		g.fillRect(0, 0, 180, 32);

		if (img != null) {
			byte[] bytes = img.getFileBytes();
			ImageIcon ii = new ImageIcon(bytes);
			Image i = ii.getImage();
			Image resizedImage = i.getScaledInstance(32, 32, Image.SCALE_SMOOTH);

			// This code ensures that all the pixels in the image are loaded.
			Image temp = new ImageIcon(resizedImage).getImage();
			g.drawImage(temp, 0, 0, null);
		}

		g.setColor(Color.darkGray);
		if (text1 != null)
			g.drawString(text1, 40, 15);
		if (text2 != null)
			g.drawString(text2, 40, 30);
		g.dispose();

		ByteArrayOutputStream out = new ByteArrayOutputStream();

		// Encodes image as a JPEG data stream
		// JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
		// JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(bufferedImage);
		// param.setQuality(1, true);
		// encoder.setJPEGEncodeParam(param);
		// encoder.encode(bufferedImage);

		// PngEncoder encoder = new PngEncoder(bufferedImage, true, null, 0);
		// encoder.encode(out);

		GifEncoder ge = new GifEncoder(bufferedImage);
		ge.setTransparentPixel(5);

		System.out.println("XXXX " + xxx);
		ge.write(out);

		return out.toByteArray();
	}

}
