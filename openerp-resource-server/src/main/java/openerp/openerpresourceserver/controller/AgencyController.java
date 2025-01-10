package openerp.openerpresourceserver.controller;

import lombok.AllArgsConstructor;
import openerp.openerpresourceserver.converter.AgencyConverter;
import openerp.openerpresourceserver.dto.AgencyDTO;
import openerp.openerpresourceserver.entity.Agency;
import openerp.openerpresourceserver.service.AgencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor(onConstructor_ = @Autowired)
@RequestMapping("/agency")
public class AgencyController {
    private final AgencyConverter agencyConverter;
    private final AgencyService agencyService;

    @GetMapping("/get-all")
    public ResponseEntity<List<AgencyDTO>> getAllAgencies() {
        List<Agency> agencies = agencyService.getAllUsers();
        List<AgencyDTO> agencyDTOs = agencies.stream()
                .map(agencyConverter::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(agencyDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AgencyDTO> getAgencyById(@PathVariable Long id) {
        Agency agency = agencyService.getAgencyById(id);
        AgencyDTO agencyDTO = agencyConverter.convertToDTO(agency);
        return ResponseEntity.ok().body(agencyDTO);
    }
}