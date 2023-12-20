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

/** This is an auto generated class representing the Nearest type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "Nearests",  authRules = {
  @AuthRule(allow = AuthStrategy.PUBLIC, operations = { ModelOperation.CREATE, ModelOperation.UPDATE, ModelOperation.DELETE, ModelOperation.READ })
})
public final class Nearest implements Model {
  public static final QueryField ID = field("Nearest", "id");
  public static final QueryField PLACE_NAME = field("Nearest", "placeName");
  public static final QueryField USER_ID = field("Nearest", "userId");
  public static final QueryField TYPE = field("Nearest", "type");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String", isRequired = true) String placeName;
  private final @ModelField(targetType="ID", isRequired = true) String userId;
  private final @ModelField(targetType="String") String type;
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
  
  public String getPlaceName() {
      return placeName;
  }
  
  public String getUserId() {
      return userId;
  }
  
  public String getType() {
      return type;
  }
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  private Nearest(String id, String placeName, String userId, String type) {
    this.id = id;
    this.placeName = placeName;
    this.userId = userId;
    this.type = type;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      Nearest nearest = (Nearest) obj;
      return ObjectsCompat.equals(getId(), nearest.getId()) &&
              ObjectsCompat.equals(getPlaceName(), nearest.getPlaceName()) &&
              ObjectsCompat.equals(getUserId(), nearest.getUserId()) &&
              ObjectsCompat.equals(getType(), nearest.getType()) &&
              ObjectsCompat.equals(getCreatedAt(), nearest.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), nearest.getUpdatedAt());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getPlaceName())
      .append(getUserId())
      .append(getType())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("Nearest {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("placeName=" + String.valueOf(getPlaceName()) + ", ")
      .append("userId=" + String.valueOf(getUserId()) + ", ")
      .append("type=" + String.valueOf(getType()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()))
      .append("}")
      .toString();
  }
  
  public static PlaceNameStep builder() {
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
  public static Nearest justId(String id) {
    return new Nearest(
      id,
      null,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      placeName,
      userId,
      type);
  }
  public interface PlaceNameStep {
    UserIdStep placeName(String placeName);
  }
  

  public interface UserIdStep {
    BuildStep userId(String userId);
  }
  

  public interface BuildStep {
    Nearest build();
    BuildStep id(String id);
    BuildStep type(String type);
  }
  

  public static class Builder implements PlaceNameStep, UserIdStep, BuildStep {
    private String id;
    private String placeName;
    private String userId;
    private String type;
    public Builder() {
      
    }
    
    private Builder(String id, String placeName, String userId, String type) {
      this.id = id;
      this.placeName = placeName;
      this.userId = userId;
      this.type = type;
    }
    
    @Override
     public Nearest build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new Nearest(
          id,
          placeName,
          userId,
          type);
    }
    
    @Override
     public UserIdStep placeName(String placeName) {
        Objects.requireNonNull(placeName);
        this.placeName = placeName;
        return this;
    }
    
    @Override
     public BuildStep userId(String userId) {
        Objects.requireNonNull(userId);
        this.userId = userId;
        return this;
    }
    
    @Override
     public BuildStep type(String type) {
        this.type = type;
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
    private CopyOfBuilder(String id, String placeName, String userId, String type) {
      super(id, placeName, userId, type);
      Objects.requireNonNull(placeName);
      Objects.requireNonNull(userId);
    }
    
    @Override
     public CopyOfBuilder placeName(String placeName) {
      return (CopyOfBuilder) super.placeName(placeName);
    }
    
    @Override
     public CopyOfBuilder userId(String userId) {
      return (CopyOfBuilder) super.userId(userId);
    }
    
    @Override
     public CopyOfBuilder type(String type) {
      return (CopyOfBuilder) super.type(type);
    }
  }
  


  
}
