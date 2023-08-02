package ruby.loghistory;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import ruby.loghistory.encrypt.AES256Util;

import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class LogInterceptor implements HandlerInterceptor {

    private final LogRepository logRepository;
    private final AES256Util aes256Util;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        saveLogHistory(request);

        return true;
    }

    private void saveLogHistory(HttpServletRequest request) {
        String username = getUsername();

        String ip = getIp(request);

        LogHistory logHistory = LogHistory.builder()
            .username(aes256Util.encrypt(username))
            .ip(aes256Util.encrypt(ip))
            .url(aes256Util.encrypt(request.getRequestURI()))
            .time(LocalDateTime.now())
            .build();

        logRepository.save(logHistory);
    }

    private String getUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if ("anonymousUser".equals(principal)) {
            return "anonymousUser";
        }

        User user = (User)principal;
        return user.getUsername();
    }

    private String getIp(HttpServletRequest request) {
        String ip = request.getHeader("X-FORWARDED-FOR");
        if (ip == null) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
