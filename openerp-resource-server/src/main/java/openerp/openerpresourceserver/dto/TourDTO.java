// TourDTO.java
package openerp.openerpresourceserver.dto;

import lombok.Data;

import java.util.List;

@Data
public class TourDTO {
    private Long id;
    private String name;
    private int price;
    private int duration;
    private boolean active;
    private List<DestinationDTO> destinations;
}