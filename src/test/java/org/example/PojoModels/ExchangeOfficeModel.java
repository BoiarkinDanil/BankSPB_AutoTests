package org.example.PojoModels;

import lombok.Data;
import java.util.ArrayList;

@Data
public class ExchangeOfficeModel {
    private String address;
    private int id;
    private String name;
    private ArrayList<Rate> rates;
}