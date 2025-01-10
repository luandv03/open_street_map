// AgencyDTO.java
package openerp.openerpresourceserver.dto;

import lombok.Data;

import java.util.List;

@Data
public class AgencyDTO {
    private Long id;
    private String name;
    private String email;
    private String phoneNumber;
    private String state;
    private List<TourDTO> tours;
}