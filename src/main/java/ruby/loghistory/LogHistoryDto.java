package ruby.loghistory;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class LogHistoryDto {

    private final Long id;

    private final String username;
    private final String ip;
    private final String url;
    private final String time;

    @Builder
    public LogHistoryDto(Long id, String username, String ip, String url, LocalDateTime time) {
        this.id = id;
        this.username = username;
        this.ip = ip;
        this.url = url;
        this.time = time.toString();
    }
}
