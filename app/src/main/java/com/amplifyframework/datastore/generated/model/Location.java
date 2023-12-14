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

/** This is an auto generated class representing the Location type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "Locations", authRules = {
  @AuthRule(allow = AuthStrategy.PUBLIC, operations = { ModelOperation.CREATE, ModelOperation.UPDATE, ModelOperation.DELETE, ModelOperation.READ })
})
public final class Location implements Model {
  public static final QueryField ID = field("Location", "id");
  public static final QueryField LONGITUDE = field("Location", "longitude");
  public static final QueryField LATITUDE = field("Location", "latitude");
  public static final QueryField COUNTRY = field("Location", "country");
  public static final QueryField CITY = field("Location", "city");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String", isRequired = true) String longitude;
  private final @ModelField(targetType="String", isRequired = true) String latitude;
  private final @ModelField(targetType="String") String country;
  private final @ModelField(targetType="String") String city;
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
  
  public String getLongitude() {
      return longitude;
  }
  
  public String getLatitude() {
      return latitude;
  }
  
  public String getCountry() {
      return country;
  }
  
  public String getCity() {
      return city;
  }
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  private Location(String id, String longitude, String latitude, String country, String city) {
    this.id = id;
    this.longitude = longitude;
    this.latitude = latitude;
    this.country = country;
    this.city = city;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      Location location = (Location) obj;
      return ObjectsCompat.equals(getId(), location.getId()) &&
              ObjectsCompat.equals(getLongitude(), location.getLongitude()) &&
              ObjectsCompat.equals(getLatitude(), location.getLatitude()) &&
              ObjectsCompat.equals(getCountry(), location.getCountry()) &&
              ObjectsCompat.equals(getCity(), location.getCity()) &&
              ObjectsCompat.equals(getCreatedAt(), location.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), location.getUpdatedAt());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getLongitude())
      .append(getLatitude())
      .append(getCountry())
      .append(getCity())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("Location {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("longitude=" + String.valueOf(getLongitude()) + ", ")
      .append("latitude=" + String.valueOf(getLatitude()) + ", ")
      .append("country=" + String.valueOf(getCountry()) + ", ")
      .append("city=" + String.valueOf(getCity()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()))
      .append("}")
      .toString();
  }
  
  public static LongitudeStep builder() {
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
  public static Location justId(String id) {
    return new Location(
      id,
      null,
      null,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      longitude,
      latitude,
      country,
      city);
  }
  public interface LongitudeStep {
    LatitudeStep longitude(String longitude);
  }
  

  public interface LatitudeStep {
    BuildStep latitude(String latitude);
  }
  

  public interface BuildStep {
    Location build();
    BuildStep id(String id);
    BuildStep country(String country);
    BuildStep city(String city);
  }
  

  public static class Builder implements LongitudeStep, LatitudeStep, BuildStep {
    private String id;
    private String longitude;
    private String latitude;
    private String country;
    private String city;
    public Builder() {
      
    }
    
    private Builder(String id, String longitude, String latitude, String country, String city) {
      this.id = id;
      this.longitude = longitude;
      this.latitude = latitude;
      this.country = country;
      this.city = city;
    }
    
    @Override
     public Location build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new Location(
          id,
          longitude,
          latitude,
          country,
          city);
    }
    
    @Override
     public LatitudeStep longitude(String longitude) {
        Objects.requireNonNull(longitude);
        this.longitude = longitude;
        return this;
    }
    
    @Override
     public BuildStep latitude(String latitude) {
        Objects.requireNonNull(latitude);
        this.latitude = latitude;
        return this;
    }
    
    @Override
     public BuildStep country(String country) {
        this.country = country;
        return this;
    }
    
    @Override
     public BuildStep city(String city) {
        this.city = city;
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
    private CopyOfBuilder(String id, String longitude, String latitude, String country, String city) {
      super(id, longitude, latitude, country, city);
      Objects.requireNonNull(longitude);
      Objects.requireNonNull(latitude);
    }
    
    @Override
     public CopyOfBuilder longitude(String longitude) {
      return (CopyOfBuilder) super.longitude(longitude);
    }
    
    @Override
     public CopyOfBuilder latitude(String latitude) {
      return (CopyOfBuilder) super.latitude(latitude);
    }
    
    @Override
     public CopyOfBuilder country(String country) {
      return (CopyOfBuilder) super.country(country);
    }
    
    @Override
     public CopyOfBuilder city(String city) {
      return (CopyOfBuilder) super.city(city);
    }
  }
  

  
}
