package com.airline.management.service;

import com.airline.management.dto.request.AeroportRequest;
import com.airline.management.dto.response.AeroportResponse;
import java.util.List;

public interface AeroportService {
    AeroportResponse create(AeroportRequest req);
    AeroportResponse update(String id, AeroportRequest req);
    void delete(String id);
    AeroportResponse getById(String id);
    List<AeroportResponse> listAll();
}
