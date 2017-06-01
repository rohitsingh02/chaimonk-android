package www.chaayos.com.chaimonkbluetoothapp.utils;

import java.math.BigDecimal;

import www.chaayos.com.chaimonkbluetoothapp.GlobalVariables;
import www.chaayos.com.chaimonkbluetoothapp.domain.model.TaxListModel;
import www.chaayos.com.chaimonkbluetoothapp.domain.model.new_model.BillType;

/**
 * Created by rohitsingh on 14/07/16.
 */
public class CalculateTax {
    BigDecimal subTotal;
    BigDecimal vatOnMRP;
    BigDecimal vatOnNetPrice;
    BigDecimal surchargeOnVat;
    BigDecimal serviceTax;
    BigDecimal kkCess;
    BigDecimal sbCess;

    public TaxListModel calculatePaidAmount(BigDecimal price, int quantity, BillType billType, GlobalVariables globalVariables) {

        subTotal = new BigDecimal(0.0);
        vatOnMRP = new BigDecimal(0.0);
        vatOnNetPrice = new BigDecimal(0.0);
        surchargeOnVat = new BigDecimal(0.0);
        serviceTax = new BigDecimal(0.0);
        kkCess = new BigDecimal(0.0);
        sbCess = new BigDecimal(0.0);


        subTotal = calculateTotalAmountBeforeTaxAndPromoOffer(price, quantity);

        if (billType.value().equals("MRP")) {

            vatOnMRP = calculateVatOnMRP(subTotal, globalVariables.getLatestTransactionDetail().getMrpVat().getPercentage().divide(new BigDecimal(100)));

        } else if (billType.value().equals("NET_PRICE")) {
            vatOnNetPrice = calculateVatOnNetPrice(subTotal, globalVariables.getLatestTransactionDetail().getNetPriceVat().getPercentage().divide(new BigDecimal(100)));
        }

        surchargeOnVat = calculateSurcharceOnVat(vatOnMRP, vatOnNetPrice, globalVariables.getLatestTransactionDetail().getSurchargeOnTax().getPercentage().divide(new BigDecimal(100)));
        serviceTax = calculateServiceTax(subTotal, globalVariables.getLatestTransactionDetail().getServiceTax().getPercentage().divide(new BigDecimal(100)));
        kkCess = calculateKkCess(subTotal, globalVariables.getLatestTransactionDetail().getKkCess().getPercentage().divide(new BigDecimal(100)));
        sbCess = calculateSbCess(subTotal, globalVariables.getLatestTransactionDetail().getSbCess().getPercentage().divide(new BigDecimal(100)));
        return createPerItemTaxDetail();
    }


    private BigDecimal calculateSbCess(BigDecimal taxableAmount, BigDecimal sbCessPercentage) {
        System.out.print(String.valueOf(taxableAmount.multiply(sbCessPercentage)));
        return taxableAmount.multiply(sbCessPercentage);
    }

    private BigDecimal calculateKkCess(BigDecimal taxableAmount, BigDecimal kkCessPercentage) {
        System.out.print(String.valueOf(taxableAmount.multiply(kkCessPercentage)));
        return taxableAmount.multiply(kkCessPercentage);
    }

    private BigDecimal calculateServiceTax(BigDecimal taxableAmount, BigDecimal serviceTaxPercentage) {
        System.out.print(String.valueOf(taxableAmount.multiply(serviceTaxPercentage)));
        return taxableAmount.multiply(serviceTaxPercentage);
    }

    private BigDecimal calculateSurcharceOnVat(BigDecimal vatOnMRP, BigDecimal vatOnNetPrice, BigDecimal surchargeOnVatPercentage) {
        return surchargeOnVatPercentage.multiply(vatOnMRP.add(vatOnNetPrice));
    }

    private BigDecimal calculateVatOnNetPrice(BigDecimal taxableAmount, BigDecimal vatOnNetPricePercentage) {
        return taxableAmount.multiply(vatOnNetPricePercentage);
    }

    private BigDecimal calculateVatOnMRP(BigDecimal taxableAmount, BigDecimal vatOnMRPPercentage) {
        return taxableAmount.multiply(vatOnMRPPercentage);
    }


    public BigDecimal calculateTotalAmountBeforeTaxAndPromoOffer(BigDecimal price, int quantity) {
        return price.multiply(new BigDecimal(quantity));
    }


    public TaxListModel createPerItemTaxDetail() {
        TaxListModel taxListModel = new TaxListModel(subTotal, vatOnMRP, vatOnNetPrice, surchargeOnVat, serviceTax, sbCess, kkCess);
        return taxListModel;
    }

}
