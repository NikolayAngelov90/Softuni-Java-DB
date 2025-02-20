package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.VolcanoImportDto;
import softuni.exam.models.entity.Country;
import softuni.exam.models.entity.Volcano;
import softuni.exam.repository.CountryRepository;
import softuni.exam.repository.VolcanoRepository;
import softuni.exam.service.VolcanoService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashSet;
import java.util.stream.Collectors;

@Service
public class VolcanoServiceImpl implements VolcanoService {

    private static final String IMPORT_JSON_PATH = "src/main/resources/files/json/volcanoes.json";

    private final VolcanoRepository volcanoRepository;
    private final CountryRepository countryRepository;
    private final Gson gson;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    public VolcanoServiceImpl(VolcanoRepository volcanoRepository, CountryRepository countryRepository, Gson gson,
                              ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.volcanoRepository = volcanoRepository;
        this.countryRepository = countryRepository;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return this.volcanoRepository.count() > 0;
    }

    @Override
    public String readVolcanoesFileContent() throws IOException {
        return Files.readString(Path.of(IMPORT_JSON_PATH));
    }

    @Override
    public String importVolcanoes() throws IOException {
        StringBuilder sb = new StringBuilder();

        VolcanoImportDto[] volcanoImportDtos = this.gson.fromJson(
                readVolcanoesFileContent(), VolcanoImportDto[].class);

        for (VolcanoImportDto volcanoImportDto : volcanoImportDtos) {
            if (!this.validationUtil.isValid(volcanoImportDto) ||
                    this.volcanoRepository.findByName(volcanoImportDto.getName()).isPresent()) {
                sb.append("Invalid volcano").append(System.lineSeparator());
                continue;
            }

            Volcano volcano = this.modelMapper.map(volcanoImportDto, Volcano.class);

            Country country = this.countryRepository.findById(volcanoImportDto
                    .getCountry()).orElse(null);

            volcano.setCountry(country);
            this.volcanoRepository.save(volcano);

            sb.append(String.format("Successfully imported volcano %s of type %s",
                            volcanoImportDto.getName(), volcanoImportDto.getVolcanoType()))
                    .append(System.lineSeparator());
        }
        return sb.toString().trim();
    }

    @Override
    public String exportVolcanoes() {
        StringBuilder sb = new StringBuilder();

        LinkedHashSet<Volcano> volcanoes = this.volcanoRepository
                .findByElevationGreaterThanAndIsActiveTrueOrderByElevationDesc(3000)
                .stream()
                .filter(v -> v.getLastEruption() != null)
                .collect(Collectors.toCollection(LinkedHashSet::new));

        for (Volcano volcano : volcanoes) {
            sb.append(String.format("Volcano: %s", volcano.getName())).append(System.lineSeparator());
            sb.append(String.format("   *Located in: %s\n", volcano.getCountry().getName()));
            sb.append(String.format("   **Elevation: %d\n", volcano.getElevation()));
            sb.append(String.format("   ***Last eruption on: %s\n", volcano.getLastEruption()));
        }

        return sb.toString();
    }
}