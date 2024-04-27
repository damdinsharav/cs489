package mn.btv.api.program;

import java.time.LocalDateTime;

public record ProgramDTO(
        String title,
        LocalDateTime start,
        LocalDateTime stop,
        int duration
) {
}
