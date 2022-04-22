package com.broadwave.backend.performance.reference;

import com.broadwave.backend.performance.reference.economy.ReferenceEconomy;
import com.broadwave.backend.performance.reference.economy.ReferenceEconomyRepository;
import com.broadwave.backend.performance.reference.policy.ReferencePolicy;
import com.broadwave.backend.performance.reference.policy.ReferencePolicyRepository;
import com.broadwave.backend.performance.reference.technicality.ReferenceTechnicality;
import com.broadwave.backend.performance.reference.technicality.ReferenceTechnicalityRepository;
import com.broadwave.backend.performance.reference.weightSetting.ReferenceWeight;
import com.broadwave.backend.performance.reference.weightSetting.ReferenceWeightRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Minkyu
 * Date : 2021-11-02
 * Time :
 * Remark : NewDeal Performance Reference Service
*/
@Slf4j
@Service
public class ReferenceService {

    private final ReferenceTechnicalityRepository referenceTechnicalityRepository;
    private final ReferenceEconomyRepository referenceEconomyRepository;
    private final ReferencePolicyRepository referencePolicyRepository;
    private final ReferenceWeightRepository referenceWeightRepository;

    @Autowired
    public ReferenceService(ReferenceTechnicalityRepository referenceTechnicalityRepository, ReferenceEconomyRepository referenceEconomyRepository, ReferencePolicyRepository referencePolicyRepository,
                            ReferenceWeightRepository referenceWeightRepository) {
        this.referenceTechnicalityRepository = referenceTechnicalityRepository;
        this.referenceEconomyRepository = referenceEconomyRepository;
        this.referencePolicyRepository = referencePolicyRepository;
        this.referenceWeightRepository = referenceWeightRepository;
    }

    // 기술성 저장
    public void techSave(ReferenceTechnicality referenceTechnicality){
        referenceTechnicalityRepository.save(referenceTechnicality);
    }

    // 경제성 저장
    public void ecoSave(ReferenceEconomy referenceEconomy){
        referenceEconomyRepository.save(referenceEconomy);
    }

    // 정책성 저장
    public void policySave(ReferencePolicy referencePolicy){
        referencePolicyRepository.save(referencePolicy);
    }

    // 가중치 저장
    public void weightSettingSave(ReferenceWeight referenceWeight){
        referenceWeightRepository.save(referenceWeight);
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

    public ReferenceWeight findByWeightSettingData(String id){
        return referenceWeightRepository.findByWeightSettingData(id);
    }


}
