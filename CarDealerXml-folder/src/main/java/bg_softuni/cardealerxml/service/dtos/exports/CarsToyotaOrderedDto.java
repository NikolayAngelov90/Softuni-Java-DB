package bg_softuni.cardealerxml.service.dtos.exports;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.Set;

@XmlRootElement(name = "cars")
@XmlAccessorType(XmlAccessType.FIELD)
public class CarsOrderedDto {

    @XmlElement(name = "car")
    private Set<CarOrderedDto> cars;

    public CarsOrderedDto() {

    }

    public CarsOrderedDto(Set<CarOrderedDto> cars) {
        this.cars = cars;
    }

    public Set<CarOrderedDto> getCars() {
        return cars;
    }

    public void setCars(Set<CarOrderedDto> cars) {
        this.cars = cars;
    }
}
