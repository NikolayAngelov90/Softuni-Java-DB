package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.VolcanologistImportDto;
import softuni.exam.models.dto.VolcanologistImportRootDto;
import softuni.exam.models.entity.Volcanologist;
import softuni.exam.repository.VolcanoRepository;
import softuni.exam.repository.VolcanologistRepository;
import softuni.exam.service.VolcanologistService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class VolcanologistServiceImpl implements VolcanologistService {

    private static final String IMPORT_XML_PATH = "src/main/resources/files/xml/volcanologists.xml";

    private final VolcanologistRepository volcanologistRepository;
    private final VolcanoRepository volcanoRepository;
    private final XmlParser xmlParser;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    public VolcanologistServiceImpl(VolcanologistRepository volcanologistRepository,
                                    VolcanoRepository volcanoRepository, XmlParser xmlParser,
                                    ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.volcanologistRepository = volcanologistRepository;
        this.volcanoRepository = volcanoRepository;
        this.xmlParser = xmlParser;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return this.volcanologistRepository.count() > 0;
    }

    @Override
    public String readVolcanologistsFromFile() throws IOException {
        return Files.readString(Path.of(IMPORT_XML_PATH));
    }

    @Override
    public String importVolcanologists() throws JAXBException {
        StringBuilder sb = new StringBuilder();

        VolcanologistImportRootDto volcanologistImportRootDto = this.xmlParser.fromFile(
                IMPORT_XML_PATH, VolcanologistImportRootDto.class);

        for (VolcanologistImportDto volcanologist : volcanologistImportRootDto.getVolcanologists()) {
            if (this.volcanologistRepository.findByFirstNameAndLastName(
                    volcanologist.getFirstName(), volcanologist.getLastName()).isPresent() ||
                    this.volcanoRepository.findById(volcanologist.getExploringVolcanoId()).isEmpty() ||
                    !this.validationUtil.isValid(volcanologist)) {
                sb.append("Invalid volcanologist").append(System.lineSeparator());
                continue;
            }

            Volcanologist mappedVolcanologist = this.modelMapper.map(
                    volcanologist, Volcanologist.class);
            mappedVolcanologist.setVolcano(this.volcanoRepository.findById(
                    volcanologist.getExploringVolcanoId()).get());

            this.volcanologistRepository.saveAndFlush(mappedVolcanologist);

            sb.append(String.format("Successfully imported volcanologist %s %s",
                    volcanologist.getFirstName(), volcanologist.getLastName()));
            sb.append(System.lineSeparator());
        }

        return sb.toString();
    }
}