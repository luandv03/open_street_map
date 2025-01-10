// CustomerDTO.java
package openerp.openerpresourceserver.dto;

import lombok.Data;

@Data
public class CustomerDTO {
    private Long id;
    private String name;
    private String email;
    private String phoneNumber;
    private String avatarUrl;
    private String bod;
    private String job;
    private String address;
}