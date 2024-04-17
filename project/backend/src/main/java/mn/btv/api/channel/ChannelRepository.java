package mn.btv.api.channel;

import org.springframework.data.repository.ListCrudRepository;

import java.util.List;
import java.util.Optional;

public interface ChannelRepository extends ListCrudRepository<Channel, Integer> {
    List<Channel> findAllByOrderByNumberAsc();

    Optional<Channel> findByXmlId(String xmlId);
}
