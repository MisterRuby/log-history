package ruby.loghistory;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LogRepository extends JpaRepository<LogHistory, Long> {
}
