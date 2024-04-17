package mn.btv.api.channel;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class ChannelService {
    private ChannelRepository channelRepository;

    public List<Channel> findAll() {
        return channelRepository.findAllByOrderByNumberAsc();
    }

    public void saveAll(List<Channel> channels) {
        channelRepository.saveAll(channels);
    }

    public Optional<Channel> findByXmlId(String xmlId) {
        return channelRepository.findByXmlId(xmlId);
    }
}
