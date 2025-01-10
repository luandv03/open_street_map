package openerp.openerpresourceserver.service;

import openerp.openerpresourceserver.entity.Agency;

import java.util.List;

public interface AgencyService {

    List<Agency> getAllUsers();

    Agency getAgencyById(Long id);

}
