package org.example.PojoModels;

import lombok.Data;

@Data
public class Rate {
    private double buyRate;
    private double cbRate;
    private String currencyCode;
    private String currencyCodeSecond;
    private int lotSize;
    private double sellRate;
    private int transactionVolume;
}