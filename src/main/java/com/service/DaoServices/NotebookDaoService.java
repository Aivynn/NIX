package com.service.DaoServices;

import com.model.DAO.NotebookDAO;
import com.model.Manufacturer;
import com.model.Notebook;
import com.repository.JDBC.NotebookJDBCRepository;
import com.repository.NotebookRepository;
import com.repository.hibernate.CrudRepositoryDAO;
import com.repository.hibernate.NotebookDaoRepository;
import com.service.ProductService;
import com.util.Autowired;
import com.util.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.Random;

@Singleton
public class NotebookDaoService extends ProductDaoService<NotebookDAO> {
    private static final Logger LOGGER = LoggerFactory.getLogger(NotebookDaoService.class);
    private static final Random RANDOM = new Random();
    private NotebookDaoRepository repository;

    private static NotebookDaoService instance;

    @Autowired
    public NotebookDaoService(NotebookDaoRepository repository) {
        super(repository);
        this.repository = repository;
    }

    protected NotebookDAO createProduct() {
        NotebookDAO notebook = new NotebookDAO(
                Notebook.class.getSimpleName() + "-" + RANDOM.nextInt(1000),
                RANDOM.nextInt(500),
                RANDOM.nextDouble(1000.0),
                "Model-" + RANDOM.nextInt(10),
                getRandomManufacturer()
        );
        repository.save(notebook);
        return notebook;
    }


    @Override
    public void update(String id) {
        repository.update(id);
        LOGGER.info("Notebook {} has been updated", id);
    }

    private Manufacturer getRandomManufacturer() {
        final Manufacturer[] values = Manufacturer.values();
        final int index = RANDOM.nextInt(values.length);
        return values[index];
    }




}