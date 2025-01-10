// AddDestinationToTourRequest.java
package openerp.openerpresourceserver.dto.request;

import lombok.Data;

@Data
public class AddDestinationToTourRequest {
    private Long tourId;
    private Long destinationId;
}