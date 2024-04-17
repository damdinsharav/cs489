package mn.btv.api.ipaddress;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Ip {
    @Id
    @Column(length = 15)
    private String address;
    private String city;
    private String region;
    private String country;
    private double latitude;
    private double longitude;
    private String organization;
    private String timezone;
}
