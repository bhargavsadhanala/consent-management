package com.consent.service;

import com.consent.entity.Purpose;

import java.util.List;

public interface PurposeService {

    Purpose addPurpose(Purpose purpose);

    Purpose fetchPurpose(Long purposeId);

    List<Purpose> getAllPurposes();

    Purpose updatePurpose();

    Purpose deletePurpose();

}
