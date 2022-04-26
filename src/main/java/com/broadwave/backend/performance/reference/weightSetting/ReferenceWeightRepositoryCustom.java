package com.broadwave.backend.performance.reference.weightSetting;

import com.broadwave.backend.performance.reference.weightSetting.weightSettingDtos.ReferenceWeightBaseDto;
import com.broadwave.backend.performance.reference.weightSetting.weightSettingDtos.ReferenceWeightOldDto;
import com.broadwave.backend.performance.reference.weightSetting.weightSettingDtos.ReferenceWeightUseDto;

public interface ReferenceWeightRepositoryCustom {
    ReferenceWeightOldDto findByReferenceWeightOld();
    ReferenceWeightUseDto findByReferenceWeightUse();
    ReferenceWeightBaseDto findByReferenceWeightBase();

}