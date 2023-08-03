package ruby.loghistory;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ruby.loghistory.encrypt.AES256Util;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
public class LogController {

    private final LogRepository logRepository;
    private final AES256Util aes256Util;

    @GetMapping("/log")
    public List<LogHistoryDto> getLogList() {
        return logRepository.findAll().stream()
            .map(logHistory -> LogHistoryDto.builder()
                .id(logHistory.getId())
                .username(aes256Util.decrypt(logHistory.getUsername()))
                .ip(aes256Util.decrypt(logHistory.getIp()))
                .url(aes256Util.decrypt(logHistory.getUrl()))
                .time(logHistory.getTime())
                .build()
            )
            .collect(Collectors.toList());
    }

    @GetMapping("/log/encrypt")
    public List<LogHistoryDto> getEncryptLogList() {
        return logRepository.findAll().stream()
            .map(logHistory -> LogHistoryDto.builder()
                .id(logHistory.getId())
                .username(logHistory.getUsername())
                .ip(logHistory.getIp())
                .url(logHistory.getUrl())
                .time(logHistory.getTime())
                .build()
            )
            .collect(Collectors.toList());
    }

    @GetMapping("/log/info")
    public void logInfo() {
        log.info("log info!! [{}]", LocalDateTime.now());
    }

    @GetMapping("/log/error")
    public void logError() {
        log.error("log error!! [{}]", LocalDateTime.now());
    }

}
