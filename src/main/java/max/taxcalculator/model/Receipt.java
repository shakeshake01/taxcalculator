package max.taxcalculator.model;


import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static java.math.BigDecimal.ROUND_HALF_EVEN;

@Data
public class Receipt {

    //This implements strategy pattern where the taxCalculation algorithm can be injected at runtime
    private final Calculator taxCalculator;

    private final Basket basket;
    private List<ReceiptRow> items;
    private BigDecimal salesTaxes;
    private BigDecimal total;

    public Receipt(TaxCalculator taxCalculator,Basket basket) {
        this.taxCalculator = taxCalculator;
        salesTaxes = total = new BigDecimal(0);
        this.basket = basket;
        items = new ArrayList<>();
    }

    public void generate() {
        basket.getItems().forEach(i -> {
            BigDecimal itemTaxes = taxCalculator.calculate(i);
            salesTaxes = salesTaxes.add(itemTaxes).setScale(2,ROUND_HALF_EVEN);
            BigDecimal itemTotAmountBeforeTaxes = i.getItem().getPrice().multiply(new BigDecimal(i.getQuantity()));
            total= total.add(itemTotAmountBeforeTaxes).add(itemTaxes).setScale(2,ROUND_HALF_EVEN);
            items.add(new ReceiptRow()
                    .withDescription(i.getItem().getDescription())
                    .withQuantity(i.getQuantity())
                    .withTotal(itemTotAmountBeforeTaxes.add(itemTaxes).setScale(2,ROUND_HALF_EVEN))
                    .withTaxTotal(itemTaxes.setScale(2, ROUND_HALF_EVEN)));

        });
    }
}
