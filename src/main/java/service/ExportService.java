package service;

import dao.SalarieDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExportService {
    private final Logger log = LogManager.getLogger(String.valueOf(ExportService.class));

    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    private final SalarieDAO salarieDAO;

    public ExportService(SalarieDAO salarieDAO) {
        this.salarieDAO = salarieDAO;
    }

    public void exportSalaries() {

    }
}
