package com.qzx.user.service.impl;

import cn.hutool.core.img.ImgUtil;
import cn.hutool.extra.qrcode.QrCodeUtil;
import cn.hutool.extra.qrcode.QrConfig;
import com.qzx.user.entity.VoUserRoleEntity;
import com.qzx.user.service.IEmailService;
import com.qzx.user.service.VoUserRoleService;
import com.qzx.user.util.EmailInfo;
import com.qzx.user.util.EmailUtil;
import com.qzx.user.utils.ResponseResult;
import io.seata.core.context.RootContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Slf4j
public class EmailServiceImpl implements IEmailService {

    private final EmailUtil emailUtil;

    private final VoUserRoleService userRoleService;

    @Override
    public ResponseResult<?> sendEmailCode(String emailCode,String to) {
        EmailInfo info = new EmailInfo(){{
           setFrom("视频管理");
           setTo(to);
           setSubject("邮箱验证码");
           setTemplate("send_email_code");
           setVariable(new HashMap<String, Object>(){{
               put("emailCode",emailCode);
           }});
        }};
        emailUtil.sendEmail(info);
        return ResponseResult.success();
    }

    @Override
    public ResponseResult<?> activityAccount(String userCode, String to) {
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
        final BufferedImage generate = QrCodeUtil.generate("http://192.168.3.33:9090/author/activeAccount/"+userCode, config);
        final String qrCode = "data:image/png;base64,"+ImgUtil.toBase64(generate, "jpg");
        emailUtil.sendEmail(new EmailInfo(){{
            setFrom("视频管理");
            setTo(to);
            setSubject("账号激活");
            setTemplate("active_account");
            setVariable(new HashMap<String, Object>(){{
                put("userCode",userCode);
                put("qrCode",qrCode);
            }});
        }});
        return ResponseResult.success();
    }

    @Override
    public ResponseResult<?> addUser() {
        // 手动绑定xId（使用传参方式传递xId），不推荐使用
        // RootContext.bind(xId);
        log.info("Seata全局事务id=================>{}", RootContext.getXID());
        userRoleService.save(new VoUserRoleEntity(){{
            setUserId(33L);
            setRoleId(22L);
        }});
        return ResponseResult.success();
    }
}
