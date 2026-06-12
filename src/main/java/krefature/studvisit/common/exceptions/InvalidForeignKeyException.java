package krefature.studvisit.common.exceptions;

public class InvalidForeignKeyException extends RuntimeException {
    private String entityName;
    private String fieldName;
    private Object fieldValue;

    public InvalidForeignKeyException(String message) {
        super(message);
    }

    public InvalidForeignKeyException(String message, String entityName, String fieldName, Object fieldValue) {
        super(message);
        this.entityName = entityName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    public String getEntityName() {
        return entityName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public Object getFieldValue() {
        return fieldValue;
    }
}

