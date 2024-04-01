package com.local.orderhandler.dto;

import com.local.orderhandler.entity.Invoice;
import lombok.*;

import java.time.LocalDate;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class StatisticDto {
    private String username;
    private int invoiceAmount;
    private LocalDate lastInvoiceDate;
    private double sum;
    private double volumeSum;
    private List<Invoice> invoices;

    public StatisticDto(String username, List<Invoice> invoices) {
        this.username = username;
        this.invoices = invoices;
    }
}
