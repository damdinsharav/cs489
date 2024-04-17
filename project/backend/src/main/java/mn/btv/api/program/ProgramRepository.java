package mn.btv.api.program;

import mn.btv.api.channel.Channel;
import org.springframework.data.repository.ListCrudRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ProgramRepository extends ListCrudRepository<Program, Integer> {
    List<Program> findByChannelIdOrderByStart(int channelId);

    Optional<Program> findByChannelAndStart(Channel channel, LocalDateTime start);
}
