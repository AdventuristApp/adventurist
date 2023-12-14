package com.amplifyframework.datastore.generated.model;

import com.amplifyframework.core.model.temporal.Temporal;
import com.amplifyframework.core.model.ModelIdentifier;

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

/** This is an auto generated class representing the Plan type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "Plans", type = Model.Type.USER, version = 1, authRules = {
  @AuthRule(allow = AuthStrategy.PUBLIC, operations = { ModelOperation.CREATE, ModelOperation.UPDATE, ModelOperation.DELETE, ModelOperation.READ })
})
public final class Plan implements Model {
  public static final QueryField ID = field("Plan", "id");
  public static final QueryField PLAN_NAME = field("Plan", "planName");
  public static final QueryField NUMBER_OF_DAYS = field("Plan", "numberOfDays");
  public static final QueryField DESTINATION = field("Plan", "destination");
  public static final QueryField BUDGET = field("Plan", "budget");
  public static final QueryField USER_ID = field("Plan", "userId");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String", isRequired = true) String planName;
  private final @ModelField(targetType="Int", isRequired = true) Integer numberOfDays;
  private final @ModelField(targetType="String", isRequired = true) String destination;
  private final @ModelField(targetType="Float", isRequired = true) Double budget;
  private final @ModelField(targetType="ID", isRequired = true) String userId;
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
  
  public String getPlanName() {
      return planName;
  }
  
  public Integer getNumberOfDays() {
      return numberOfDays;
  }
  
  public String getDestination() {
      return destination;
  }
  
  public Double getBudget() {
      return budget;
  }
  
  public String getUserId() {
      return userId;
  }
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  private Plan(String id, String planName, Integer numberOfDays, String destination, Double budget, String userId) {
    this.id = id;
    this.planName = planName;
    this.numberOfDays = numberOfDays;
    this.destination = destination;
    this.budget = budget;
    this.userId = userId;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      Plan plan = (Plan) obj;
      return ObjectsCompat.equals(getId(), plan.getId()) &&
              ObjectsCompat.equals(getPlanName(), plan.getPlanName()) &&
              ObjectsCompat.equals(getNumberOfDays(), plan.getNumberOfDays()) &&
              ObjectsCompat.equals(getDestination(), plan.getDestination()) &&
              ObjectsCompat.equals(getBudget(), plan.getBudget()) &&
              ObjectsCompat.equals(getUserId(), plan.getUserId()) &&
              ObjectsCompat.equals(getCreatedAt(), plan.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), plan.getUpdatedAt());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getPlanName())
      .append(getNumberOfDays())
      .append(getDestination())
      .append(getBudget())
      .append(getUserId())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("Plan {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("planName=" + String.valueOf(getPlanName()) + ", ")
      .append("numberOfDays=" + String.valueOf(getNumberOfDays()) + ", ")
      .append("destination=" + String.valueOf(getDestination()) + ", ")
      .append("budget=" + String.valueOf(getBudget()) + ", ")
      .append("userId=" + String.valueOf(getUserId()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()))
      .append("}")
      .toString();
  }
  
  public static PlanNameStep builder() {
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
  public static Plan justId(String id) {
    return new Plan(
      id,
      null,
      null,
      null,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      planName,
      numberOfDays,
      destination,
      budget,
      userId);
  }
  public interface PlanNameStep {
    NumberOfDaysStep planName(String planName);
  }
  

  public interface NumberOfDaysStep {
    DestinationStep numberOfDays(Integer numberOfDays);
  }
  

  public interface DestinationStep {
    BudgetStep destination(String destination);
  }
  

  public interface BudgetStep {
    UserIdStep budget(Double budget);
  }
  

  public interface UserIdStep {
    BuildStep userId(String userId);
  }
  

  public interface BuildStep {
    Plan build();
    BuildStep id(String id);
  }
  

  public static class Builder implements PlanNameStep, NumberOfDaysStep, DestinationStep, BudgetStep, UserIdStep, BuildStep {
    private String id;
    private String planName;
    private Integer numberOfDays;
    private String destination;
    private Double budget;
    private String userId;
    public Builder() {
      
    }
    
    private Builder(String id, String planName, Integer numberOfDays, String destination, Double budget, String userId) {
      this.id = id;
      this.planName = planName;
      this.numberOfDays = numberOfDays;
      this.destination = destination;
      this.budget = budget;
      this.userId = userId;
    }
    
    @Override
     public Plan build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new Plan(
          id,
          planName,
          numberOfDays,
          destination,
          budget,
          userId);
    }
    
    @Override
     public NumberOfDaysStep planName(String planName) {
        Objects.requireNonNull(planName);
        this.planName = planName;
        return this;
    }
    
    @Override
     public DestinationStep numberOfDays(Integer numberOfDays) {
        Objects.requireNonNull(numberOfDays);
        this.numberOfDays = numberOfDays;
        return this;
    }
    
    @Override
     public BudgetStep destination(String destination) {
        Objects.requireNonNull(destination);
        this.destination = destination;
        return this;
    }
    
    @Override
     public UserIdStep budget(Double budget) {
        Objects.requireNonNull(budget);
        this.budget = budget;
        return this;
    }
    
    @Override
     public BuildStep userId(String userId) {
        Objects.requireNonNull(userId);
        this.userId = userId;
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
    private CopyOfBuilder(String id, String planName, Integer numberOfDays, String destination, Double budget, String userId) {
      super(id, planName, numberOfDays, destination, budget, userId);
      Objects.requireNonNull(planName);
      Objects.requireNonNull(numberOfDays);
      Objects.requireNonNull(destination);
      Objects.requireNonNull(budget);
      Objects.requireNonNull(userId);
    }
    
    @Override
     public CopyOfBuilder planName(String planName) {
      return (CopyOfBuilder) super.planName(planName);
    }
    
    @Override
     public CopyOfBuilder numberOfDays(Integer numberOfDays) {
      return (CopyOfBuilder) super.numberOfDays(numberOfDays);
    }
    
    @Override
     public CopyOfBuilder destination(String destination) {
      return (CopyOfBuilder) super.destination(destination);
    }
    
    @Override
     public CopyOfBuilder budget(Double budget) {
      return (CopyOfBuilder) super.budget(budget);
    }
    
    @Override
     public CopyOfBuilder userId(String userId) {
      return (CopyOfBuilder) super.userId(userId);
    }
  }
  

  public static class PlanIdentifier extends ModelIdentifier<Plan> {
    private static final long serialVersionUID = 1L;
    public PlanIdentifier(String id) {
      super(id);
    }
  }
  
}
