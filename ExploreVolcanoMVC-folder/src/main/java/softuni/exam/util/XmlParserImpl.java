package softuni.exam.util;

import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.io.File;

@Component
public class XmlParserImpl implements XmlParser {

    @SuppressWarnings("unchecked")
    @Override
    public <T> T fromFile(String filePath, Class<T> tClass) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(tClass);

        return (T) jaxbContext.createUnmarshaller().unmarshal(new File(filePath));
    }
}
