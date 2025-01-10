// DestinationDTO.java
package openerp.openerpresourceserver.dto;

import lombok.Data;

@Data
public class DestinationDTO {
    private Long id;
    private String name;
    private String description;
    private String imageUrl;
    private String longitude;
    private String latitude;
}