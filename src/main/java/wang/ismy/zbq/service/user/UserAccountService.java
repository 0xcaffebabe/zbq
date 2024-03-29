package wang.ismy.zbq.service.user;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import wang.ismy.zbq.dao.user.UserAccountMapper;
import wang.ismy.zbq.model.entity.user.User;
import wang.ismy.zbq.model.entity.user.UserAccount;
import wang.ismy.zbq.enums.UserAccountEnum;
import wang.ismy.zbq.resources.R;
import wang.ismy.zbq.service.system.EmailService;
import wang.ismy.zbq.service.system.ExecuteService;
import wang.ismy.zbq.util.ErrorUtils;

import javax.inject.Inject;
import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * @author my
 */
@Service
@Slf4j

public class UserAccountService {

    private static final String EMAIL_REGEX = "^([A-Za-z0-9_\\-\\.])+\\@([A-Za-z0-9_\\-\\.])+\\.([A-Za-z]{2,4})$";

    @Setter(onMethod_ = @Inject)
    private UserAccountMapper accountMapper;

    @Setter(onMethod_ = @Inject)
    private UserService userService;

    @Setter(onMethod_ = @Inject)
    private EmailService emailService;

    @Setter(onMethod_ = @Inject)
    private ExecuteService executeService;

    @Value("${config.host}")
    private String host;

    private static final String SALT = "fdknfdjkw556dt4023vu[pofdswry7824;ld15.;'[.kje4fdk./.jk";

    public int createUserAccountRecord(UserAccount userAccount) {

        return accountMapper.insertNew(userAccount);
    }

    public UserAccount selectByAccountTypeAndUserId(UserAccountEnum accountType, Integer userId) {
        return accountMapper.selectByAccountTypeAndUserId(accountType.getCode(), userId);
    }

    public String selectAccountName(UserAccountEnum accountType, Integer userId) {
        var account = accountMapper.selectByAccountTypeAndUserId(accountType.getCode(), userId);
        if (account == null)
        {
            return null;
        }

        return account.getAccountName();
    }

    public User selectUser(UserAccountEnum accountEnum, String accountName){
        var account = accountMapper.selectByAccountTypeAndAccountName(accountEnum.getCode(),accountName);
        if (account != null){
            return account.getUser();
        }
        return null;
    }

    /**
     * TODO 需要重构
     */
    public void currentUserBindEmail(String email) {
        var currentUser = userService.getCurrentUser();

        if (!email.matches(EMAIL_REGEX)) {
            ErrorUtils.error(R.INCORRECT_EMAIL);
        }

        UserAccount account = accountMapper.selectByAccountTypeAndAccountName(UserAccountEnum.EMAIL.getCode(), email);

        if (account != null) {
            ErrorUtils.error(R.EMAIL_BOUND);
        }

        String t = currentUser.getUsername() + SALT + email;
        String sign = DigestUtils.md5DigestAsHex(t.getBytes()).toUpperCase();

        try {
            String link = host + "/userAccount/valid?username=" + URLEncoder.encode(currentUser.getUsername(), "utf8")
                    + "&email=" + URLEncoder.encode(email, "utf8")
                    + "&sign=" + sign;

            executeService.submit(() -> {
                try {
                    emailService.sendHtmlMail(email, "转笔圈邮箱绑定确认",
                            "您正在进行转笔圈邮箱绑定，请<a href ='" + link + "' style='font-size:24px'>点击我</a>完成绑定");
                } catch (MessagingException e) {
                    log.error("发送邮件发送错误:{}", e.getMessage());
                }
            });

        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

    }

    public boolean validEmailBinding(String username, String email, String sign) {
        var user = userService.selectByUsername(username);

        if (user == null) {
            ErrorUtils.error(R.USERNAME_NOT_EXIST);
        }
        String t = username + SALT + email;
        if (DigestUtils.md5DigestAsHex(t.getBytes()).toUpperCase().equals(sign)) {

            UserAccount account = accountMapper.selectByAccountTypeAndUserId(UserAccountEnum.EMAIL.getCode(), user.getUserId());

            // 如果该用户已有绑定关系
            if (account != null) {
                // 修改原有绑定关系
                return accountMapper.updateByAccountNameByAccountTypeAndUserId(UserAccountEnum.EMAIL.getCode(), user.getUserId(), email) == 1;
            } else {
                // 添加新绑定关系
                UserAccount userAccount = new UserAccount();
                userAccount.setValid(true);
                userAccount.setAccountType(0);
                userAccount.setAccountName(email);
                userAccount.setUser(user);
                return accountMapper.insertNew(userAccount) == 1;
            }

        } else {
            ErrorUtils.error(R.EMAIL_VALID_ERROR);
        }
        return false;
    }


    public Object getCurrentUserAccountList() {

        var currentUser = userService.getCurrentUser();

        Map<String, String> map = new HashMap<>();

        var list = accountMapper.selectByUser(currentUser.getUserId());

        for (var i : list) {
            if (i.getAccountType() == 0) {
                map.put("email", i.getAccountName());
            }
        }
        return map;
    }

    public static boolean isEmail(String email){
        if (email != null){
            return email.matches(EMAIL_REGEX);
        }
        return false;
    }
}
