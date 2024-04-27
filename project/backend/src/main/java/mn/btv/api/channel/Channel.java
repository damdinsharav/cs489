package mn.btv.api.channel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import mn.btv.api.program.Program;

import java.util.List;

@NoArgsConstructor
@Data
@Entity
public class Channel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int number;
    private String name;
    private String urlPath;
    private String xmlId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "channel")
    private List<Program> programs;

    public Channel(int number, String name, String urlPath, String xmlId) {
        this.number = number;
        this.name = name;
        this.urlPath = urlPath;
        this.xmlId = xmlId;
    }
}