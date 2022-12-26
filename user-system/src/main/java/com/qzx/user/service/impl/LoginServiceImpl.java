package com.qzx.user.service.impl;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.CircleCaptcha;
import cn.hutool.captcha.ShearCaptcha;
import cn.hutool.core.img.Img;
import cn.hutool.core.util.RandomUtil;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.qzx.user.utils.Constant;
import com.qzx.user.dto.CustomAuthenticationToken;
import com.qzx.user.dto.LoginUser;
import com.qzx.user.entity.VoRoleEntity;
import com.qzx.user.entity.VoUserEntity;
import com.qzx.user.entity.VoUserRoleEntity;
import com.qzx.user.exception.BusinessException;
import com.qzx.user.feign.service.EmailService;
import com.qzx.user.sentinel.SentinelBlockExceptionHandler;
import com.qzx.user.service.ILoginService;
import com.qzx.user.service.IVoUserService;
import com.qzx.user.service.VoRoleService;
import com.qzx.user.service.VoUserRoleService;
import com.qzx.user.utils.*;
import io.seata.core.context.RootContext;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Slf4j
public class LoginServiceImpl implements ILoginService {


    private final AuthenticationManager authenticationManager;

    private final RedisUtil redisUtil;

    private final EmailService emailService;

    private final IVoUserService voUserService;

    private final VoUserRoleService voUserRoleService;

    private final VoRoleService voRoleService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final RedissonClient redissonClient;


    private void getCaptchaOld(HttpServletResponse response) {
        ////定义图形验证码的长、宽、验证码字符数、干扰元素个数
        ShearCaptcha captcha = CaptchaUtil.createShearCaptcha(200, 100, 4, 4);
        try {
            response.setContentType("image/png");
            final ServletOutputStream outputStream = response.getOutputStream();
            Img.from(captcha.getImage()).write(outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
//    @SentinelResource(value = "getCaptcha",blockHandler = "getCaptchaBlockHandler",fallback = "getCaptchaByNameFallback")
//    @SentinelResource(value = "getCaptcha",blockHandlerClass = {SentinelBlockExceptionHandler.class},blockHandler = "getCaptchaBlockHandler",fallbackClass = {SentinelFallbackHandle.class},fallback = "getCaptchaByNameFallback")
    @SentinelResource(value = "getCaptcha",blockHandlerClass = {SentinelBlockExceptionHandler.class},blockHandler = "getCaptchaBlockHandler")
    public ResponseResult<?> getCaptcha() {
        Map<String,Object> result = new ConcurrentHashMap<>();
        //定义图形验证码的长、宽、验证码字符数、干扰元素个数
        CircleCaptcha captcha = CaptchaUtil.createCircleCaptcha(200, 100, 4, 20);
        String redisKey = UUIDUtils.getLowerCode();
        redisUtil.set(Constant.USER_CAPTCHA_KEY+redisKey,captcha.getCode(),120);
        result.put("captchaId",redisKey);
        result.put("captcha","data:image/png;base64,"+captcha.getImageBase64());
        return ResponseResult.success(result);
    }

    @Override
    public ResponseResult<?> sendEmailCode(String emailAddress) {
        Map<String,String > result = new ConcurrentHashMap<>();
        /**获取随机验证码*/
        final String emailCode = RandomUtil.randomString(4);
        /**获取redisKey*/
        String redisKey = UUIDUtils.getTokenLower();
        /**
         * 发送邮件
         * */
        final ExecutorService executorService = Executors.newFixedThreadPool(5);
        executorService.execute(()->{
            emailService.sendEmailCode(emailCode,emailAddress);
        });
        redisUtil.set(Constant.USER_EMAIL_CODE_KEY+redisKey,emailCode,120);
        result.put("emailCodeId",redisKey);
        /**
         * 退出线程池
         * */
        executorService.shutdown();
        return ResponseResult.success(result);
    }

    @Override
    public ResponseResult<?> loginByPassword(LoginUser loginUser) {
        /**校验验证码*/
        if (!redisUtil.hasKey(Constant.USER_CAPTCHA_KEY+loginUser.getCaptchaId())){
            return ResponseResult.error(500,"验证码已失效");
        }
        System.out.println(redisUtil.get(Constant.USER_CAPTCHA_KEY+loginUser.getCaptchaId()).toString().toLowerCase(Locale.ROOT));
        if (!Objects.deepEquals(loginUser.getCaptcha().toLowerCase(Locale.ROOT),redisUtil.get(Constant.USER_CAPTCHA_KEY+loginUser.getCaptchaId()).toString().toLowerCase(Locale.ROOT))){
            return ResponseResult.error(500,"验证码不正确");
        }
        /**
         * 删除验证码
         * */
        redisUtil.del(Constant.USER_CAPTCHA_KEY+loginUser.getCaptchaId());
        final Authentication authenticate = authenticationManager.authenticate(new CustomAuthenticationToken(loginUser.getUserName(), loginUser.getPassword(),1));
        if (authenticate==null){
            throw new BusinessException("用户认证失败");
        }
        return ResponseResult.success(JwtTokenUtil.createToken(authenticate.getName(),false));
    }

    @Override
    public ResponseResult<?> loginByEmailCode(LoginUser loginUser) {
        /**校验邮箱验证码*/
        if (!redisUtil.hasKey(Constant.USER_EMAIL_CODE_KEY+loginUser.getEmailCodeId())){
            return ResponseResult.error(500,"邮箱验证码已失效");
        }
        if (!Objects.deepEquals(loginUser.getEmailCode().toLowerCase(Locale.ROOT),redisUtil.get(Constant.USER_EMAIL_CODE_KEY+loginUser.getEmailCodeId()).toString().toLowerCase(Locale.ROOT))){
            return ResponseResult.error(500,"验证码不正确");
        }
        /**
         * 删除验证码
         * */
        redisUtil.del(Constant.USER_EMAIL_CODE_KEY+loginUser.getEmailCodeId());
        final Authentication authenticate = authenticationManager.authenticate(new CustomAuthenticationToken(loginUser.getUserName(), null,2));
        if (authenticate==null){
            throw new BusinessException("用户认证失败,确认是否激活");
        }
        return ResponseResult.success(JwtTokenUtil.createToken(authenticate.getName(),false));
    }

    @Override
    public ResponseResult<?> checkUserCode(String userCode) {
        final VoUserEntity one = voUserService.getOne(new LambdaQueryWrapper<VoUserEntity>() {{
            eq(VoUserEntity::getUserCode, userCode);
        }});
        if (!ObjectUtils.isEmpty(one)){
            return ResponseResult.error(ResponseResult.ResultEnum.REPEAT_USER_CODE);
        }
        return ResponseResult.success();
    }

    @Override
    public ResponseResult<?> checkEmail(String email) {
        final VoUserEntity one = voUserService.getOne(new LambdaQueryWrapper<VoUserEntity>() {{
            eq(VoUserEntity::getEmail, email);
        }});
        if (!ObjectUtils.isEmpty(one)){
            return ResponseResult.error(ResponseResult.ResultEnum.REPEAT_USER_EMAIL);
        }
        return ResponseResult.success();
    }

    @Override
    @Transactional
//    @SentinelResource(value = "register",blockHandlerClass = {SentinelBlockExceptionHandler.class},blockHandler = "registerBlockHandler",fallbackClass = {SentinelFallbackHandle.class},fallback = "registerFallback")
    @SentinelResource(value = "register",blockHandlerClass = {SentinelBlockExceptionHandler.class},blockHandler = "registerBlockHandler")
    public ResponseResult<?> register(VoUserEntity user) {
        // 1. 幂等性验证
        final RLock lock = redissonClient.getLock(Constant.USER_REGISTER_LOCK + user.getUserCode());
        try {
            if (lock.tryLock(3,2, TimeUnit.SECONDS)){
                /**
                 * 校验账号是否存在
                 */
                if (ObjectUtils.isEmpty(user)||ObjectUtils.isEmpty(user.getUserCode())){
                    return ResponseResult.error(ResponseResult.ResultEnum.NONE_USER_CODE);
                }
                /**
                 * 校验用户名是否存在
                 */
                if (ObjectUtils.isEmpty(user)||ObjectUtils.isEmpty(user.getUserName())){
                    return ResponseResult.error(ResponseResult.ResultEnum.NONE_USER_NAME);
                }
                /**
                 * 校验邮箱是否存在
                 */
                if (ObjectUtils.isEmpty(user)||ObjectUtils.isEmpty(user.getEmail())){
                    return ResponseResult.error(ResponseResult.ResultEnum.NONE_USER_EMAIL);
                }
                /**
                 * 校验密码是否存在
                 */
                if (ObjectUtils.isEmpty(user)||ObjectUtils.isEmpty(user.getPassword())){
                    return ResponseResult.error(ResponseResult.ResultEnum.NONE_USER_PASSWORD);
                }
                ResponseResult<?> responseResult = checkUserCode(user.getUserCode());
                if (responseResult.getCode()==500){
                    return responseResult;
                }
                responseResult = checkEmail(user.getEmail());
                if (responseResult.getCode()==500){
                    return responseResult;
                }
                /**
                 * 保存用户信息
                 * */
                user.setStatus(CommonEnum.UserStatusEnum.NO_ACTIVITY_ACCOUNT.getCode());
                user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
                if (voUserService.save(user)){
                    /**
                     * 添加权限
                     * */
                    final VoRoleEntity roleNormal = voRoleService.getOne(new LambdaQueryWrapper<VoRoleEntity>() {{
                        eq(VoRoleEntity::getRoleCode, "role_normal");
                    }});
                    voUserRoleService.save(new VoUserRoleEntity(){{
                        setRoleId(roleNormal.getRoleId());
                        setUserId(user.getId());
                    }});
                }
                /**
                 * 发送邮件
                 * */
                final ExecutorService executorService = Executors.newFixedThreadPool(5);
                executorService.execute(()->{
                    emailService.activityAccount(user.getUserCode(), user.getEmail());
                });
                executorService.shutdown();
                return ResponseResult.success("请前往邮件激活账户");
            }
            return ResponseResult.error(500,"请勿重复提交");
        } catch (InterruptedException e) {
            e.printStackTrace();
            return ResponseResult.error(500,"注册失败");
        } finally {
            if (Objects.nonNull(lock) && lock.isLocked()) {
                lock.unlock();
            }
        }

    }

    @Override
    @Transactional
    public ResponseResult<?> activeAccount(String userCode) {
        return voUserService.update(new LambdaUpdateWrapper<VoUserEntity>(){{
            set(VoUserEntity::getStatus,CommonEnum.UserStatusEnum.ACTIVITY_ACCOUNT.getCode());
        }})?ResponseResult.success("操作成功"):ResponseResult.error(500,"账号激活失败");
    }

    @Override
    public void logout(String token) {
        final String key = Constant.USER_LOGIN_TOKEN + JwtTokenUtil.getUsername(token);
        if (redisUtil.hasKey(key)){
            redisUtil.del(key);
        }
    }

    /**
     * seata--测试
     * @return
     */
    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public ResponseResult<?> testSeate() {
        log.info("Seata全局事务id=================>{}", RootContext.getXID());
        voUserService.save(new VoUserEntity(){{
            setUserName("123");
            setUserCode("111");
            setPassword("123");
            setEmail("sddd");
        }});
        final ResponseResult<?> result = emailService.addUser();
        if (result.getCode()==500){
            throw new BusinessException(result.getMsg());
        }
        return ResponseResult.success("成功");
    }
}
