package mn.btv.api.channel;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class ChannelService {
    private ChannelRepository channelRepository;

    public List<ChannelDTO> findAll() {

        return channelRepository.findAllByOrderByNumberAsc().stream().map(
                channel -> new ChannelDTO(channel.getNumber(), channel.getName(), channel.getUrlPath())
        ).collect(Collectors.toList());
    }

    public Optional<Channel> findByXmlId(String xmlId) {
        return channelRepository.findByXmlId(xmlId);
    }
}
