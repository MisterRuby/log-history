package ruby.loghistory;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static lombok.AccessLevel.PROTECTED;

@Entity
@NoArgsConstructor(access = PROTECTED)
@Getter
public class LogHistory {

    @Id
    @GeneratedValue
    private Long id;

    private String username;
    private String ip;
    private String url;
    private LocalDateTime time;

    @Builder
    public LogHistory(String username, String ip, String url, LocalDateTime time) {
        this.username = username;
        this.ip = ip;
        this.url = url;
        this.time = time;
    }
}
