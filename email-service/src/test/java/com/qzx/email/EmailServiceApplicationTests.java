package com.qzx.email;

import cn.hutool.extra.qrcode.QrCodeUtil;
import cn.hutool.extra.qrcode.QrConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.awt.*;
import java.io.File;

@SpringBootTest
class EmailServiceApplicationTests {

    @Test
    void contextLoads() {
        QrConfig config = new QrConfig(300, 300);
        // 设置边距，既二维码和背景之间的边距
        config.setMargin(3);
        // 设置前景色，既二维码颜色（青色）
        config.setForeColor(Color.cyan);
        // 设置背景色（灰色）
        config.setBackColor(Color.gray);
        //设置长、宽
        config.setWidth(300);
        config.setHeight(300);
        // 生成二维码到文件，也可以到流
        QrCodeUtil.generate("http://192.168.3.33:9090/author/activeAccount?userCode=123", config,new File("H:/test/qrcode.jpg"));
    }

}
