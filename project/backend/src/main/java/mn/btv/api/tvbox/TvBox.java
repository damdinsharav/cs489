package mn.btv.api.tvbox;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class TvBox {
    @Id
    @Column(length = 17)
    private String macAddress;
    private String owner;
    private String contact;
    private boolean status;
    private LocalDateTime lastOnline;
    private LocalDateTime lastStatusChange;

}
