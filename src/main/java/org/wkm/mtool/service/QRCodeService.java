/**
 * Apache LICENSE-2.0
 * Project name : mtool
 * Package name : org.wkm.mtool.service
 * Author : Wukunmeng
 * User : wkm
 * Date : 15-11-22
 * Time : 下午1:53
 * 版权所有,侵权必究！
 */
package org.wkm.mtool.service;

import com.swetake.util.Qrcode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * Create with IntelliJ IDEA
 * Project name : mtool
 * Package name : org.wkm.mtool.service
 * Author : Wukunmeng
 * User : wkm
 * Date : 15-11-22
 * Time : 下午1:53
 * 版权所有,侵权必究！
 * To change this template use File | Settings | File and Code Templates.
 */
public class QRCodeService {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    public String createImage(String cotent){

        String projectPath = System.getProperty("user.dir");

        File image = new File(new File(projectPath),"src/main/webapp/images");

        log.info("image path:" + image.getAbsolutePath());
        String cl = String.valueOf(System.currentTimeMillis());
        String fileName = cl + ".png";
        log.info("file name:" + fileName);
        encoderQRCode(cotent,new File(image,fileName));
        return cl;
    }

    /**
     * 生成二维码(QRCode)图片
     * @param content
     * @param imgFile
     */
    private void encoderQRCode(String content, File imgFile) {
        try {
            Qrcode qrcodeHandler = new Qrcode();
            qrcodeHandler.setQrcodeErrorCorrect('M');
            qrcodeHandler.setQrcodeEncodeMode('B');
            qrcodeHandler.setQrcodeVersion(7);

            System.out.println(content);
            byte[] contentBytes = content.getBytes("gb2312");

            BufferedImage bufImg = new BufferedImage(140, 140,
                    BufferedImage.TYPE_INT_RGB);

            Graphics2D gs = bufImg.createGraphics();

            gs.setBackground(Color.WHITE);
            gs.clearRect(0, 0, 140, 140);

            // 设定图像颜色 > BLACK
            gs.setColor(Color.BLACK);

            // 设置偏移量 不设置可能导致解析出错
            int pixoff = 2;
            // 输出内容 > 二维码
            if (contentBytes.length > 0 && contentBytes.length < 120) {
                boolean[][] codeOut = qrcodeHandler.calQrcode(contentBytes);
                for (int i = 0; i < codeOut.length; i++) {
                    for (int j = 0; j < codeOut.length; j++) {
                        if (codeOut[j][i]) {
                            gs.fillRect(j * 3 + pixoff, i * 3 + pixoff, 3, 3);
                        }
                    }
                }
            } else {
                System.err.println("QRCode content bytes length = "
                        + contentBytes.length + " not in [ 0,120 ]. ");
            }

            gs.dispose();
            bufImg.flush();

            // 生成二维码QRCode图片
            ImageIO.write(bufImg, "png", imgFile);

        } catch (Exception e) {
            log.info("Exception:" + e.getMessage());
        }
    }
}
