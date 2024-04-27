package mn.btv.api.channel;

import io.ipinfo.api.model.IPResponse;
import io.ipinfo.spring.strategies.attribute.AttributeStrategy;
import lombok.AllArgsConstructor;
import mn.btv.api.server.ServerService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class ChannelService {
    private AttributeStrategy attributeStrategy;
    private ChannelRepository channelRepository;
    private ServerService serverService;

    public List<ChannelDTO> findAll(IPResponse ipResponse) {
        String server = serverService.findClosest(ipResponse);
        return channelRepository.findAllByOrderByNumberAsc().stream().map(
                channel -> new ChannelDTO(channel.getNumber(), channel.getName(),
                        "http://" + server + channel.getUrlPath())
        ).collect(Collectors.toList());
    }


    public Optional<Channel> findByXmlId(String xmlId) {
        return channelRepository.findByXmlId(xmlId);
    }
}
