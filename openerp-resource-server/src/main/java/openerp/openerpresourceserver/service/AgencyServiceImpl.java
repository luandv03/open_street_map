package openerp.openerpresourceserver.service;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import openerp.openerpresourceserver.entity.Agency;
import openerp.openerpresourceserver.repo.AgencyRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@AllArgsConstructor(onConstructor_ = @Autowired)
@Service
public class AgencyServiceImpl implements AgencyService {

    private AgencyRepo agencyRepo;

    @Override
    public List<Agency> getAllUsers() {
        return agencyRepo.findAll();
    }

    @Override
    public Agency getAgencyById(Long id) {
        return agencyRepo.findById(String.valueOf(id)).orElse(null);
    }
}