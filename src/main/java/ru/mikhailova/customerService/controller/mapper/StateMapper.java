package ru.mikhailova.customerService.controller.mapper;

import org.springframework.stereotype.Component;
import ru.mikhailova.customerService.domain.ClaimState;

import java.util.Map;

@Component
public class StateMapper {
    private static final Map<String, ClaimState> map = Map.of(
            "createdClaim", ClaimState.CREATED,
            "acceptedState", ClaimState.ACCEPTED,
            "assignedState", ClaimState.ASSIGNED,
            "processedState", ClaimState.PROCESSED,
            "notResolvedState", ClaimState.NOT_RESOLVED,
            "finishedState", ClaimState.FINISHED
    );

    public ClaimState getStateByEventId(String eventId) {
        return map.get(eventId);
    }
}
