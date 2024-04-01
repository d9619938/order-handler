package com.local.orderhandler.repository;

import com.local.orderhandler.entity.Invoice;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface InvoiceRepository extends CrudRepository<Invoice, Integer> {
    Optional<List<Invoice>> findAllInvoiceByUser_Id(int UserId);
    Optional<List<Invoice>> findInvoiceByUserId(int UserId);
    Optional<Invoice> findInvoiceById(int InvoiceId);

}
