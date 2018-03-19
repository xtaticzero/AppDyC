/*
 * Todos los Derechos Reservados 2016 SAT.
 * Servicio de Administracion Tributaria (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma.
 */
package mx.gob.sat.siat.dyc.generico.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;
import java.util.Map;
import org.apache.log4j.Logger;
import javax.imageio.ImageIO;
import java.io.OutputStream;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;

/**
 *
 * @author root
 */
public final class QRCodeUtil {

    private static final Logger LOG = Logger.getLogger(QRCodeUtil.class.getName());

    
       private QRCodeUtil()
    {
    
    }
       
    /**
     *
     * @param contenido
     * @param width
     * @param height
     * @param formato
     * @return
     */
    public static InputStream getQr(String contenido, int width, int height, String formato) {

        Map<EncodeHintType, ErrorCorrectionLevel> hintMap
                = new Hashtable<EncodeHintType, ErrorCorrectionLevel>();
        hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);

        QRCodeWriter qrCodeWriter = new QRCodeWriter();

        BitMatrix byteMatrix = null;
        try {
            byteMatrix = qrCodeWriter.encode(contenido, BarcodeFormat.QR_CODE, width, height, hintMap);

            BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

            Graphics2D ig2 = bi.createGraphics();
            ig2.setColor(Color.WHITE);
            ig2.fillRect(0, 0, width, height);
            ig2.setColor(Color.BLACK);
            int d = width / width;
            int bitWidth = byteMatrix.getWidth();
            int bitHeight = byteMatrix.getHeight();
            for (int i = 0; i < bitHeight; i++) {
                for (int j = 0; j < bitWidth; j++) {
                    boolean isSet = byteMatrix.get(j, i);
                    if (isSet) {
                        ig2.fillRect(d * j, d * i, d, d);
                    }
                }
            }
            ByteArrayOutputStream out;
            out = new ByteArrayOutputStream();
            ImageIO.write(bi, formato, out);
            out.close();
            return new ByteArrayInputStream(out.toByteArray());

        } catch (WriterException e) {
            LOG.error("e: " + e.getMessage());
            return null;

        } catch (IOException e) {
            LOG.error("e: " + e.getMessage());
            return null;
        }

    }

    /**
     *
     * @param in InputStream de codigo QR
     * @param file imagen QR a guardar
     */
    public static void saveQrCodeToDirectory(InputStream in, File file) {
        LOG.info("saveQrCodeToDirectory: " + file.getPath());
         OutputStream out = null;
        try {
             out = new FileOutputStream(file);
            byte[] buf = new byte[ConstantesDyCNumerico.VALOR_1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            
            
        } catch (IOException e) {
          LOG.error(e.getMessage(), e);
        }finally
        {
            try
            {
                if(out != null)
                {
                    out.close();
                }

                if(in != null)
                {
                 in.close();
                }
                } catch (IOException e) {
             LOG.error(e.getMessage(), e);
           }
        }
    }

}
