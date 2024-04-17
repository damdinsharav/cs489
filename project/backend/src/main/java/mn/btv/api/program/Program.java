package mn.btv.api.program;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import mn.btv.api.channel.Channel;

import java.time.Duration;
import java.time.LocalDateTime;


@NoArgsConstructor
@Data
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"channel", "start"})})
public class Program {
    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "channel", foreignKey = @ForeignKey(name = "FK_CHANNEL"))
    private Channel channel;
    private String title;
    private LocalDateTime start;
    private LocalDateTime stop;
    private int duration;

    public Program(Channel channel, String title, LocalDateTime start, LocalDateTime stop) {
        this.channel = channel;
        this.title = title;
        this.start = start;
        this.stop = stop;
        this.duration = (int) Duration.between(start, stop).toSeconds();
    }
}
