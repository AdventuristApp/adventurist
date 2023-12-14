package com.amplifyframework.datastore.generated.model;

import com.amplifyframework.core.model.temporal.Temporal;

import java.util.List;
import java.util.UUID;
import java.util.Objects;

import androidx.core.util.ObjectsCompat;

import com.amplifyframework.core.model.AuthStrategy;
import com.amplifyframework.core.model.Model;
import com.amplifyframework.core.model.ModelOperation;
import com.amplifyframework.core.model.annotations.AuthRule;
import com.amplifyframework.core.model.annotations.Index;
import com.amplifyframework.core.model.annotations.ModelConfig;
import com.amplifyframework.core.model.annotations.ModelField;
import com.amplifyframework.core.model.query.predicate.QueryField;

import static com.amplifyframework.core.model.query.predicate.QueryField.field;

/** This is an auto generated class representing the Images type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "Images", authRules = {
  @AuthRule(allow = AuthStrategy.PUBLIC, operations = { ModelOperation.CREATE, ModelOperation.UPDATE, ModelOperation.DELETE, ModelOperation.READ })
})
public final class Images implements Model {
  public static final QueryField ID = field("Images", "id");
  public static final QueryField TASK_IMAGE_S3_KEY = field("Images", "taskImageS3Key");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String", isRequired = true) String taskImageS3Key;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime createdAt;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime updatedAt;
  /** @deprecated This API is internal to Amplify and should not be used. */
  @Deprecated
   public String resolveIdentifier() {
    return id;
  }
  
  public String getId() {
      return id;
  }
  
  public String getTaskImageS3Key() {
      return taskImageS3Key;
  }
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  private Images(String id, String taskImageS3Key) {
    this.id = id;
    this.taskImageS3Key = taskImageS3Key;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      Images images = (Images) obj;
      return ObjectsCompat.equals(getId(), images.getId()) &&
              ObjectsCompat.equals(getTaskImageS3Key(), images.getTaskImageS3Key()) &&
              ObjectsCompat.equals(getCreatedAt(), images.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), images.getUpdatedAt());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getTaskImageS3Key())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("Images {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("taskImageS3Key=" + String.valueOf(getTaskImageS3Key()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()))
      .append("}")
      .toString();
  }
  
  public static TaskImageS3KeyStep builder() {
      return new Builder();
  }
  
  /**
   * WARNING: This method should not be used to build an instance of this object for a CREATE mutation.
   * This is a convenience method to return an instance of the object with only its ID populated
   * to be used in the context of a parameter in a delete mutation or referencing a foreign key
   * in a relationship.
   * @param id the id of the existing item this instance will represent
   * @return an instance of this model with only ID populated
   */
  public static Images justId(String id) {
    return new Images(
      id,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      taskImageS3Key);
  }
  public interface TaskImageS3KeyStep {
    BuildStep taskImageS3Key(String taskImageS3Key);
  }
  

  public interface BuildStep {
    Images build();
    BuildStep id(String id);
  }
  

  public static class Builder implements TaskImageS3KeyStep, BuildStep {
    private String id;
    private String taskImageS3Key;
    public Builder() {
      
    }
    
    private Builder(String id, String taskImageS3Key) {
      this.id = id;
      this.taskImageS3Key = taskImageS3Key;
    }
    
    @Override
     public Images build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new Images(
          id,
          taskImageS3Key);
    }
    
    @Override
     public BuildStep taskImageS3Key(String taskImageS3Key) {
        Objects.requireNonNull(taskImageS3Key);
        this.taskImageS3Key = taskImageS3Key;
        return this;
    }
    
    /**
     * @param id id
     * @return Current Builder instance, for fluent method chaining
     */
    public BuildStep id(String id) {
        this.id = id;
        return this;
    }
  }
  

  public final class CopyOfBuilder extends Builder {
    private CopyOfBuilder(String id, String taskImageS3Key) {
      super(id, taskImageS3Key);
      Objects.requireNonNull(taskImageS3Key);
    }
    
    @Override
     public CopyOfBuilder taskImageS3Key(String taskImageS3Key) {
      return (CopyOfBuilder) super.taskImageS3Key(taskImageS3Key);
    }
  }
}
