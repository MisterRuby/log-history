package ruby.loghistory;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class LogController {

    @GetMapping("/log1")
    public void log1() {}

    @GetMapping("/log2")
    public void log2() {}
}
