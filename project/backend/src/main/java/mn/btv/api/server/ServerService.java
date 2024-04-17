package mn.btv.api.server;

import io.ipinfo.api.model.IPResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ServerService {
    @Value("${server1}")
    private String SERVER1;

    @Value("${server2}")
    private String SERVER2;

    public String findClosest(IPResponse ipResponse) {
        String timezone = ipResponse.getTimezone();
        if (timezone != null && timezone.startsWith("America/")) {
            return SERVER2;
        } else {
            return SERVER1;
        }
    }
}

