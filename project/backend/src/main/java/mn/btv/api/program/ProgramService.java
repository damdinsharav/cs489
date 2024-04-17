package mn.btv.api.program;

import lombok.AllArgsConstructor;
import mn.btv.api.channel.Channel;
import mn.btv.api.channel.ChannelService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class ProgramService {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
    private ProgramRepository programRepository;
    private ChannelService channelService;

    public List<Program> findAllByChannelId(int channelId) {
        return programRepository.findByChannelIdOrderByStart(channelId);
    }

    public void loadAndSaveEpgData(String url) {
        try {
            // Fetch the XML content from the remote URL
            Document doc = Jsoup.connect(url).get();

            // Select all programme elements
            Elements programmeElements = doc.select("programme");

            // Loop through each programme element
            for (Element programme : programmeElements) {
                String xmlId = programme.attr("channel");
                Optional<Channel> channel = channelService.findByXmlId(xmlId);

                if (channel.isPresent()) {
                    String[] startTime = programme.attr("start").split(" ");
                    String[] stopTime = programme.attr("stop").split(" ");
                    LocalDateTime start = LocalDateTime.parse(startTime[0], FORMATTER);
                    LocalDateTime stop = LocalDateTime.parse(stopTime[0], FORMATTER);
                    String title = programme.select("title").text();
                    int hours = Integer.parseInt(startTime[1].substring(1, 3));

                    switch (startTime[1].charAt(0)) {
                        case '+':
                            start = start.minusHours(hours);
                            stop = stop.minusHours(hours);
                            break;
                        case '-':
                            start = start.plusHours(hours);
                            stop = stop.plusHours(hours);
                            break;
                        default:
                            System.out.println("Invalid timezone");
                    }
                    try {
                        Optional<Program> duplicate = programRepository.findByChannelAndStart(channel.get(), start);
                        if (duplicate.isEmpty()) {
                            programRepository.save(new Program(channel.get(), title, start, stop));
                        }
                    } catch (Exception e) {
                        System.out.println("Duplicate: " + channel + title + e.getMessage());
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
