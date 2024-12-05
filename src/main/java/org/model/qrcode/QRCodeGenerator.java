package org.model.qrcode;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

import java.io.File;
import java.nio.file.Path;

public class QRCodeGenerator {
    public static void generateQRCode(String data, String filePath) {
        try {
            BitMatrix matrix = new MultiFormatWriter()
                    .encode(data, BarcodeFormat.QR_CODE, 300, 300);
            Path path = new File(filePath).toPath();
            MatrixToImageWriter.writeToPath(matrix, "PNG", path);
            System.out.println("QR Code created at: " + filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

