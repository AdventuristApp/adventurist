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

/** This is an auto generated class representing the FavResturants type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "FavResturants", authRules = {
  @AuthRule(allow = AuthStrategy.PUBLIC, operations = { ModelOperation.CREATE, ModelOperation.UPDATE, ModelOperation.DELETE, ModelOperation.READ })
})
public final class FavResturants implements Model {
  public static final QueryField ID = field("FavResturants", "id");
  public static final QueryField RESTURANT = field("FavResturants", "Resturant");
  public static final QueryField USER_ID = field("FavResturants", "userId");
  public static final QueryField TYPE = field("FavResturants", "type");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String", isRequired = true) String Resturant;
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
      return Resturant;
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
  
  private FavResturants(String id, String Resturant, String userId, String type) {
    this.id = id;
    this.Resturant = Resturant;
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
      FavResturants favResturants = (FavResturants) obj;
      return ObjectsCompat.equals(getId(), favResturants.getId()) &&
              ObjectsCompat.equals(getResturant(), favResturants.getResturant()) &&
              ObjectsCompat.equals(getUserId(), favResturants.getUserId()) &&
              ObjectsCompat.equals(getType(), favResturants.getType()) &&
              ObjectsCompat.equals(getCreatedAt(), favResturants.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), favResturants.getUpdatedAt());
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
      .append("FavResturants {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("Resturant=" + String.valueOf(getResturant()) + ", ")
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
  public static FavResturants justId(String id) {
    return new FavResturants(
      id,
      null,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      Resturant,
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
    FavResturants build();
    BuildStep id(String id);
    BuildStep type(String type);
  }
  

  public static class Builder implements ResturantStep, UserIdStep, BuildStep {
    private String id;
    private String Resturant;
    private String userId;
    private String type;
    public Builder() {
      
    }
    
    private Builder(String id, String Resturant, String userId, String type) {
      this.id = id;
      this.Resturant = Resturant;
      this.userId = userId;
      this.type = type;
    }
    
    @Override
     public FavResturants build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new FavResturants(
          id,
          Resturant,
          userId,
          type);
    }
    
    @Override
     public UserIdStep resturant(String resturant) {
        Objects.requireNonNull(resturant);
        this.Resturant = resturant;
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
      super(id, Resturant, userId, type);
      Objects.requireNonNull(Resturant);
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
