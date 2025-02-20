package softuni.exam.models.dto;

import com.google.gson.annotations.Expose;
import org.hibernate.validator.constraints.Length;
import softuni.exam.models.enums.VolcanoType;

import javax.validation.constraints.Positive;

public class VolcanoImportDto {

    @Expose
    @Length(min = 2, max = 30)
    private String name;

    @Expose
    @Positive
    private int elevation;

    @Expose
    private VolcanoType volcanoType;

    @Expose
    private boolean isActive;

    @Expose
    private String lastEruption;

    @Expose
    private Long country;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getElevation() {
        return elevation;
    }

    public void setElevation(int elevation) {
        this.elevation = elevation;
    }

    public VolcanoType getVolcanoType() {
        return volcanoType;
    }

    public void setVolcanoType(VolcanoType volcanoType) {
        this.volcanoType = volcanoType;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getLastEruption() {
        return lastEruption;
    }

    public void setLastEruption(String lastEruption) {
        this.lastEruption = lastEruption;
    }

    public Long getCountry() {
        return country;
    }

    public void setCountry(Long country) {
        this.country = country;
    }

}
