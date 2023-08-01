package ruby.loghistory;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@Slf4j
@RestController
public class LogController {

    @GetMapping("/log1")
    public void log1(Principal principal) {
        log.info("log1 = {}", principal.getName());
    }

    @GetMapping("/log2")
    public void log2(Principal principal) {
        log.info("log2 = {}", principal.getName());
    }
}
