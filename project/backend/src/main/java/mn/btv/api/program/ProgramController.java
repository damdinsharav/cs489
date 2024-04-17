package mn.btv.api.program;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/channels")
public class ProgramController {
    private ProgramService programService;

    @GetMapping("/{channelId}/epgs")
    public ResponseEntity<List<Program>> getAllByEpgs(@PathVariable String channelId) {
        return ResponseEntity.ok().body(programService.findAllByChannelId(Integer.parseInt(channelId)));
    }

    @GetMapping("/update")
    public ResponseEntity<String> updateEpgs() {
        programService.loadAndSaveEpgData("https://static.btv.mn/zuragt-epg.xml");
        return ResponseEntity.ok("EPGs have updated!");
    }
}
