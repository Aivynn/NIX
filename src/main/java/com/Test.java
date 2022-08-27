package com;

import com.model.DAO.InvoiceDAO;
import com.model.DAO.PhoneDAO;
import com.model.DAO.ProductDAO;
import com.model.Manufacturer;
import com.model.OperationSystem;
import com.model.Phone;
import com.repository.hibernate.InvoiceDaoRepository;
import com.repository.hibernate.PhoneDaoRepository;
import com.service.PhoneService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Stream;

public class Test {
    private  static final PhoneDaoRepository repository = new PhoneDaoRepository();
    private  static final InvoiceDaoRepository repo = new InvoiceDaoRepository();
    private static final Random random = new Random();


    public static void main(String[] args) {

        PhoneDAO phone = new PhoneDAO(
                "Title-" + random.nextInt(1000),
                random.nextInt(500),
                random.nextInt(1000),
                "Model-" + random.nextInt(10),
                Manufacturer.APPLE,
                LocalDateTime.now()
        );
        PhoneDAO phone1 = new PhoneDAO(
                "Title-" + random.nextInt(1000),
                random.nextInt(500),
                random.nextInt(1000),
                "Model-" + random.nextInt(10),
                Manufacturer.APPLE,
                LocalDateTime.now()
        );
        List<ProductDAO> productDAOS = new ArrayList<>();
        productDAOS.add(phone);
        productDAOS.add(phone1);
       /* repository.save(phone);
        repository.save(phone1);*/
        InvoiceDAO invoiceDAO = new InvoiceDAO.InvoiceBuilder()
                //.id(UUID.randomUUID().toString())
                .sum(2000)
                .time(LocalDateTime.now())
                .products(productDAOS)
                .build();
        repo.save(invoiceDAO);
        System.out.println(repo.getById("e1c7087a-9627-4f57-aadd-4605e37b8cc6"));
        System.out.println(repo.getById("e1c7087a-9627-4f57-aadd-4605e37b8cc6"));
    }
}
