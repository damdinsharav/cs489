package mn.btv.api.server;


import io.ipinfo.api.model.IPResponse;
import io.ipinfo.spring.strategies.attribute.AttributeStrategy;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/api")
public class ServerController {
    private AttributeStrategy attributeStrategy;
    private ServerService serverService;

    @GetMapping("/server")
    public ResponseEntity<String> getServer(HttpServletRequest request) {
        IPResponse ipResponse = attributeStrategy.getAttribute(request);
        String server = serverService.findClosest(ipResponse);

        return ResponseEntity.ok().body(server);
    }
}
