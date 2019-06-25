package wang.ismy.zbq.service.user;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import wang.ismy.zbq.enums.UserAccountEnum;
import wang.ismy.zbq.model.dto.user.PasswordResetDTO;
import wang.ismy.zbq.resources.R;
import wang.ismy.zbq.service.CacheService;
import wang.ismy.zbq.service.system.EmailService;
import wang.ismy.zbq.util.ErrorUtils;

import javax.inject.Inject;
import java.util.Random;

/**
 * @author my
 */
@Service
@Slf4j
public class UserPasswordService {

    @Setter(onMethod_ = @Inject)
    private UserService userService;

    @Setter(onMethod_ = @Inject)
    private UserAccountService userAccountService;

    @Setter(onMethod_ = @Inject)
    private EmailService emailService;

    @Setter(onMethod_ = @Inject)
    private CacheService cacheService;

    private static final String TABLES = "ABCDEFGHIJKLMNOPQRSTUVWXYZ123456789abcdefghijklmnopqrstuvwxyz";

    /**
     * 根据邮箱随机生成一个验证码，
     * 如果邮箱与系统的用户有绑定关系，
     * 将验证码发送到指定邮箱，并记录到缓存中
     *
     * @param email 邮箱
     */
    public void generateCode(String email) {
        if (userAccountService.selectUser(UserAccountEnum.EMAIL, email) == null) {
            log.info("用户重置密码的邮箱不存在");
            return;
        }

        var code = generateRandomCode();
        cacheService.put("resetPassword" + email, code, 1800);

        emailService.sendtTextMail(email, "【转笔圈】你正在重置密码", "你重置密码的验证码是:" + code
                + "(区分大小写)，验证码将在半小时后失效，请勿将验证码告诉他人");

    }

    public String generateRandomCode() {
        StringBuilder ret = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            int r = random.nextInt(TABLES.length());
            ret.append(TABLES, r, r + 1);
        }
        return ret.toString();
    }

    /**
     * 用户提交重置密码请求
     * 如果邮箱以及验证码都与缓存中的数据符合
     * 则根据邮箱倒查出用户，把用户的密码设置为传进来新密码
     * 最后要把缓存中的验证码销毁
     *
     * @param dto 密码重置数据对象
     */
    public void resetPassword(PasswordResetDTO dto) {
        var s = cacheService.get("resetPassword" + dto.getEmail());

        if (StringUtils.isEmpty(s)) {
            ErrorUtils.error(R.CODE_NOT_MATCH);
        }

        if (!s.equals(dto.getCode())) {
            ErrorUtils.error(R.CODE_NOT_MATCH);
        }

        var user = userAccountService.selectUser(UserAccountEnum.EMAIL,dto.getEmail());
        user.setPassword(dto.getPassword());

        if (userService.update(user) != 1){
            ErrorUtils.error(R.UNKNOWN_ERROR);
        }

        cacheService.delete("resetPassword" + dto.getEmail());

    }
}
