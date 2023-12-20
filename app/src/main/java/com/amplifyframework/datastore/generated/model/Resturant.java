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

/** This is an auto generated class representing the Resturant type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "Resturants",  authRules = {
  @AuthRule(allow = AuthStrategy.PUBLIC, operations = { ModelOperation.CREATE, ModelOperation.UPDATE, ModelOperation.DELETE, ModelOperation.READ })
})
public final class Resturant implements Model {
  public static final QueryField ID = field("Resturant", "id");
  public static final QueryField RESTURANT = field("Resturant", "resturant");
  public static final QueryField USER_ID = field("Resturant", "userId");
  public static final QueryField TYPE = field("Resturant", "type");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String", isRequired = true) String resturant;
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
  
  public String getResturant() {
      return resturant;
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
  
  private Resturant(String id, String resturant, String userId, String type) {
    this.id = id;
    this.resturant = resturant;
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
      Resturant resturant = (Resturant) obj;
      return ObjectsCompat.equals(getId(), resturant.getId()) &&
              ObjectsCompat.equals(getResturant(), resturant.getResturant()) &&
              ObjectsCompat.equals(getUserId(), resturant.getUserId()) &&
              ObjectsCompat.equals(getType(), resturant.getType()) &&
              ObjectsCompat.equals(getCreatedAt(), resturant.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), resturant.getUpdatedAt());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getResturant())
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
      .append("Resturant {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("resturant=" + String.valueOf(getResturant()) + ", ")
      .append("userId=" + String.valueOf(getUserId()) + ", ")
      .append("type=" + String.valueOf(getType()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()))
      .append("}")
      .toString();
  }
  
  public static ResturantStep builder() {
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
  public static Resturant justId(String id) {
    return new Resturant(
      id,
      null,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      resturant,
      userId,
      type);
  }
  public interface ResturantStep {
    UserIdStep resturant(String resturant);
  }
  

  public interface UserIdStep {
    BuildStep userId(String userId);
  }
  

  public interface BuildStep {
    Resturant build();
    BuildStep id(String id);
    BuildStep type(String type);
  }
  

  public static class Builder implements ResturantStep, UserIdStep, BuildStep {
    private String id;
    private String resturant;
    private String userId;
    private String type;
    public Builder() {
      
    }
    
    private Builder(String id, String resturant, String userId, String type) {
      this.id = id;
      this.resturant = resturant;
      this.userId = userId;
      this.type = type;
    }
    
    @Override
     public Resturant build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new Resturant(
          id,
          resturant,
          userId,
          type);
    }
    
    @Override
     public UserIdStep resturant(String resturant) {
        Objects.requireNonNull(resturant);
        this.resturant = resturant;
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
    private CopyOfBuilder(String id, String resturant, String userId, String type) {
      super(id, resturant, userId, type);
      Objects.requireNonNull(resturant);
      Objects.requireNonNull(userId);
    }
    
    @Override
     public CopyOfBuilder resturant(String resturant) {
      return (CopyOfBuilder) super.resturant(resturant);
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
