package mn.btv.api.channel;

import io.ipinfo.spring.strategies.attribute.AttributeStrategy;
import lombok.AllArgsConstructor;
import mn.btv.api.server.ServerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api")
public class ChannelController {
    private ChannelService channelService;
    private AttributeStrategy attributeStrategy;
    private ServerService serverService;

    @GetMapping("/channels")
    public ResponseEntity<List<ChannelDTO>> getAllChannels() {
        return ResponseEntity.ok().body(channelService.findAll());
    }

}