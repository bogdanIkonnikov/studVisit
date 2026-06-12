package krefature.studvisit.common.exceptions;

import java.util.List;

public class DependentEntityException extends RuntimeException {
    private String entityName;
    private Long entityId;
    private List<String> dependentEntities;

    public DependentEntityException(String message) {
        super(message);
    }

    public DependentEntityException(String message, String entityName, Long entityId, List<String> dependentEntities) {
        super(message);
        this.entityName = entityName;
        this.entityId = entityId;
        this.dependentEntities = dependentEntities;
    }

    public String getEntityName() {
        return entityName;
    }

    public Long getEntityId() {
        return entityId;
    }

    public List<String> getDependentEntities() {
        return dependentEntities;
    }
}

