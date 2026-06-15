package util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.nio.file.Path;
import java.nio.file.Paths;

public class QRGenerator {

    public static String generateQR(String text) {

        try {

            String fileName =
                    "qr_" + text + ".png";

            QRCodeWriter writer =
                    new QRCodeWriter();

            BitMatrix matrix =
                    writer.encode(
                            text,
                            BarcodeFormat.QR_CODE,
                            300,
                            300
                    );

            Path path =
                    Paths.get(fileName);

            MatrixToImageWriter.writeToPath(
                    matrix,
                    "PNG",
                    path
            );

            return path.toAbsolutePath().toString();

        } catch (Exception e) {

            e.printStackTrace();
            return null;
        }
    }
}