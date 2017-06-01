package www.chaayos.com.chaimonkbluetoothapp.domain.model;

import java.math.BigDecimal;

/**
 * Created by rohitsingh on 15/07/16.
 */
public class TaxListModel {
    private BigDecimal subTotal;
    private BigDecimal vatOnMrp;
    private BigDecimal vatOnNetPrice;
    private BigDecimal surchargeOnVat;
    private BigDecimal serviceTax;
    private BigDecimal sbCess;
    private BigDecimal kkCess;

    public TaxListModel(BigDecimal subTotal, BigDecimal vatOnMrp, BigDecimal vatOnNetPrice, BigDecimal surchargeOnVat,BigDecimal serviceTax, BigDecimal sbCess, BigDecimal kkCess) {
        this.subTotal = subTotal;
        this.vatOnMrp = vatOnMrp;
        this.vatOnNetPrice = vatOnNetPrice;
        this.surchargeOnVat = surchargeOnVat;
        this.serviceTax = serviceTax;
        this.sbCess = sbCess;
        this.kkCess = kkCess;
    }

    public BigDecimal getServiceTax() {
        return serviceTax;
    }

    public BigDecimal getSubTotal() {
        return subTotal;
    }

    public BigDecimal getVatOnMrp() {
        return vatOnMrp;
    }

    public BigDecimal getVatOnNetPrice() {
        return vatOnNetPrice;
    }

    public BigDecimal getSurchargeOnVat() {
        return surchargeOnVat;
    }

    public BigDecimal getSbCess() {
        return sbCess;
    }

    public BigDecimal getKkCess() {
        return kkCess;
    }
}
