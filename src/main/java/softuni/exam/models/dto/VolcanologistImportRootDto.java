package softuni.exam.models.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;


@XmlRootElement(name = "volcanologists")
@XmlAccessorType(XmlAccessType.FIELD)
public class VolcanologistImportRootDto {

    @XmlElement(name = "volcanologist")
    List<VolcanologistImportDto> volcanologists;

    public List<VolcanologistImportDto> getVolcanologists() {
        return volcanologists;
    }

    public void setVolcanologists(List<VolcanologistImportDto> volcanologists) {
        this.volcanologists = volcanologists;
    }
}
