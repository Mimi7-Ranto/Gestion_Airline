package com.airline.management.service;

import com.airline.management.dto.request.VolRequest;
import com.airline.management.dto.response.VolResponse;
import java.time.LocalDateTime;
import java.util.List;

public interface VolService {
    VolResponse create(VolRequest req);
    VolResponse update(String id, VolRequest req);
    void delete(String id);
    VolResponse getById(String id);
    List<VolResponse> listAll();
    List<VolResponse> searchByDepartAndDateRange(String idAeroportDepart, LocalDateTime from, LocalDateTime to);
}
