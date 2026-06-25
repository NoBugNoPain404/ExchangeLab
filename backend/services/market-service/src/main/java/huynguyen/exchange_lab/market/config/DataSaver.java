package huynguyen.exchange_lab.market.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import huynguyen.exchange_lab.market.common.KlineCache;
import huynguyen.exchange_lab.market.common.PriceEngine;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class DataSaver {
    private final Path path =
            Path.of("services/market-service/src/main/resources/data-snapshot.json");
    private final PriceEngine priceEngine;
    private final ObjectMapper objectMapper = new ObjectMapper()
            .enable(SerializationFeature.INDENT_OUTPUT);


    @PreDestroy
    public void save() throws IOException {
        Files.createDirectories(path.getParent());
        Map<String, BigDecimal> prices = priceEngine.getAllPrices();
        try {
            if (!Files.exists(path)) {
                Files.createFile(path);
            }
            objectMapper.writeValue(path.toFile(), prices);
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
    }

    @PostConstruct
    public void read() throws IOException {
        try {
            if (Files.exists(path)) {
                Map<String, BigDecimal> prices = new HashMap<>();
                prices = objectMapper.readValue(path.toFile(), new TypeReference<Map<String, BigDecimal>>() {
                });
                priceEngine.setAllPrices(prices);
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
