package bg_softuni.cardealerxml.service.dtos.exports;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.Set;

@XmlRootElement(name = "cars")
@XmlAccessorType(XmlAccessType.FIELD)
public class CarsWithParts {

    @XmlElement(name = "car")
    private Set<CarWithPartsDto> cars;

    public CarsWithParts() {
    }

    public Set<CarWithPartsDto> getCars() {
        return cars;
    }

    public void setCars(Set<CarWithPartsDto> cars) {
        this.cars = cars;
    }
}
