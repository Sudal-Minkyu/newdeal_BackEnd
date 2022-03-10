package com.broadwave.backend.performance.reference;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author Minkyu
 * Date : 2021-11-02
 * Time :
 * Remark : NewDeal Performance Reference Service
*/
@Slf4j
@Service
public class ReferenceService {

    @Autowired
    ReferenceTechnicalityRepository referenceTechnicalityRepository;

    @Autowired
    ReferenceEconomyRepository referenceEconomyRepository;

    @Autowired
    ReferencePolicyRepository referencePolicyRepository;

    public void techSave(ReferenceTechnicality referenceTechnicality){
        referenceTechnicalityRepository.save(referenceTechnicality);
    }

    public void ecoSave(ReferenceEconomy referenceEconomy){
        referenceEconomyRepository.save(referenceEconomy);
    }

    public void policySave(ReferencePolicy referencePolicy){
        referencePolicyRepository.save(referencePolicy);
    }

    public ReferenceTechnicality techData(String id){
        return referenceTechnicalityRepository.techData(id);
    }

    public ReferenceEconomy ecoData(String id){
        return referenceEconomyRepository.ecoData(id);
    }

    public ReferencePolicy policyData(String id){
        return referencePolicyRepository.policyData(id);
    }



}
