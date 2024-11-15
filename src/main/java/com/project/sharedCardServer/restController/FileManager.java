package com.project.sharedCardServer.restController;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Hashtable;
import java.util.UUID;

@Component
public class FileManager {
   private static final String FOLDER = "pic";
    private static final String GROUP_PATH = "/group/";
    private static final String PRODUCT_PATH = "/product/";
    private static final String RECIPE_PATH = "/recipe/";
    private static final String USER_PATH = "/user/";
    private static final String QR_CODE_PATH = "/qr-code/";
    private static final String CATEGORY_PATH = "/category/";

    public static String getDefaulGroupUri(UUID groupId){
        return FOLDER + GROUP_PATH + groupId + "_1";

    }
    public static byte[] getGroupPic(String name) {
        String path = FOLDER + GROUP_PATH + name;
        File file = new File(path);
        return getFile(file);
    }
    public static byte[] getCategory(String name) {
        String path = FOLDER + CATEGORY_PATH + name;
        File file = new File(path);
        return getFile(file);
    }
    public static byte[] getRecipe(String name) {
        String path = FOLDER + RECIPE_PATH + name;
        File file = new File(path);
        return getFile(file);
    }
    public static byte[] getProduct(String name) {
        String path = FOLDER + PRODUCT_PATH + name;
        File file = new File(path);
        return getFile(file);
    }
    public static String saveGroupPic(String uri, byte[] pic) {
        return saveFile(uri, pic);
    }

    public static byte[] getUserPic(String name) {
        String path = FOLDER + USER_PATH + name;
        File file = new File(path);
        return getFile(file);
    }

    public static String saveUserPic(String uri, byte[] pic) {
        return saveFile(uri, pic);
    }

    public static String saveDefaultUserPic(UUID userId) {
        String path = FOLDER + USER_PATH + userId + "_1";
        File file = new File(path);
        File defPic = new File(FOLDER + USER_PATH + "default-user");
        try {
            FileCopyUtils.copy(defPic, file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return path;
    }
    public static String saveDefaultGroupPic(UUID groupId) {
        String path = FOLDER + GROUP_PATH + groupId + "_1";
        File file = new File(path);
        File defPic = new File(FOLDER + GROUP_PATH + "default-group");
        try {
            FileCopyUtils.copy(defPic, file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return path;
    }

    private static byte[] getFile(File file) {
        if (file.exists()) {
            try {
                BufferedInputStream stream =
                        new BufferedInputStream(new FileInputStream(file));
                byte[] bytes = stream.readAllBytes();
                stream.close();
                return bytes;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return new byte[0];
    }

    private static String saveFile(String uri, byte[] pic) {
        System.out.println(uri);
        File file = new File(uri);
        if (file.exists()) {
            file.delete();
            String[] split = uri.split("_");
            String uriId = split[0];
            Integer number = Integer.parseInt(split[1]);
            uri = uriId + "_" + ++number;
            System.out.println(uri);
            file = new File(uri);
        }
        try(BufferedOutputStream stream =
                    new BufferedOutputStream(new FileOutputStream(file))) {
            stream.write(pic);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return uri;
    }
    public static byte[] getQRCode(String name) {
        String path = FOLDER + QR_CODE_PATH + name;
        File file = new File(path);
        return getFile(file);
    }

    public static String createQRImage(UUID groupId, String qrCodeText)
            throws WriterException, IOException {
        int size = 600;
        String fileType = "png";
        String path = FOLDER + QR_CODE_PATH + groupId;
        File file = new File(path);
        Hashtable<EncodeHintType, ErrorCorrectionLevel> hintMap = new Hashtable<>();
        hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);

        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix byteMatrix = qrCodeWriter.encode(qrCodeText, BarcodeFormat.QR_CODE, size, size, hintMap);
        int matrixWidth = byteMatrix.getWidth();

        BufferedImage image = new BufferedImage(matrixWidth, matrixWidth, BufferedImage.TYPE_INT_RGB);
        image.createGraphics();

        Graphics2D graphics = (Graphics2D) image.getGraphics();
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, matrixWidth, matrixWidth);
        graphics.setColor(Color.BLACK);

        for (int i = 0; i < matrixWidth; i++) {
            for (int j = 0; j < matrixWidth; j++) {
                if (byteMatrix.get(i, j)) {
                    graphics.fillRect(i, j, 1, 1);
                }
            }
        }
        ImageIO.write(image, fileType, file);
        return path;
    }

}
