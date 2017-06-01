package www.chaayos.com.chaimonkbluetoothapp.printer;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

import www.chaayos.com.chaimonkbluetoothapp.domain.model.new_model.Order;
import www.chaayos.com.chaimonkbluetoothapp.domain.model.new_model.OrderInfo;
import www.chaayos.com.chaimonkbluetoothapp.domain.model.new_model.OrderItem;
import www.chaayos.com.chaimonkbluetoothapp.domain.model.new_model.Settlement;
import www.chaayos.com.chaimonkbluetoothapp.domain.model.new_model.TransactionDetail;
import www.chaayos.com.chaimonkbluetoothapp.domain.model.new_model.Unit;
import www.chaayos.com.chaimonkbluetoothapp.fragments.PrinterSettingsFragment;

import static www.chaayos.com.chaimonkbluetoothapp.printer.PrintTexts.AMOUNT_LIMIT;
import static www.chaayos.com.chaimonkbluetoothapp.printer.PrintTexts.BILL_TOTAL;
import static www.chaayos.com.chaimonkbluetoothapp.printer.PrintTexts.CIN_LABEL;
import static www.chaayos.com.chaimonkbluetoothapp.printer.PrintTexts.CIN_VALUE;
import static www.chaayos.com.chaimonkbluetoothapp.printer.PrintTexts.DASHED_WITH_PADDING;
import static www.chaayos.com.chaimonkbluetoothapp.printer.PrintTexts.DUPLICATE_BILL;
import static www.chaayos.com.chaimonkbluetoothapp.printer.PrintTexts.FULL_LENGTH;
import static www.chaayos.com.chaimonkbluetoothapp.printer.PrintTexts.KK_CESS_LABEL;
import static www.chaayos.com.chaimonkbluetoothapp.printer.PrintTexts.L_PADDING;
import static www.chaayos.com.chaimonkbluetoothapp.printer.PrintTexts.NAME_LIMIT;
import static www.chaayos.com.chaimonkbluetoothapp.printer.PrintTexts.NL;
import static www.chaayos.com.chaimonkbluetoothapp.printer.PrintTexts.PERCENT_LABEL;
import static www.chaayos.com.chaimonkbluetoothapp.printer.PrintTexts.PRICE_LIMIT;
import static www.chaayos.com.chaimonkbluetoothapp.printer.PrintTexts.QTY_LIMIT;
import static www.chaayos.com.chaimonkbluetoothapp.printer.PrintTexts.ROUND_OFF_LABEL;
import static www.chaayos.com.chaimonkbluetoothapp.printer.PrintTexts.SB_CESS_LABEL;
import static www.chaayos.com.chaimonkbluetoothapp.printer.PrintTexts.SERVICE_TAX_LABEL;
import static www.chaayos.com.chaimonkbluetoothapp.printer.PrintTexts.TOTAL_LABEL;
import static www.chaayos.com.chaimonkbluetoothapp.printer.PrintTexts.VAT_LABEL;


/**
 * Created by rohitsingh on 16/07/16.
 */
public class PrintFeature {

    private EpsonHelper mPrinter;
    private String mManualTarget;
    private DecimalFormat dcmFormat = new DecimalFormat("0.00");
    private Activity mActivity;

    public void setupPrinter(final Activity activity, EpsonHelper.PrintEventsCallback
            printEventsCallback) {
        mActivity = activity;
        if (mPrinter == null) {
            mPrinter = new EpsonHelper(activity);
           // mPrinter = EpsonHelper.getInstance(activity);
        } else {
            mPrinter.setActivity(activity);
        }
        mPrinter.attachPrintEventCallbacks(printEventsCallback);
        mPrinter.startDiscovery();

    }


    public boolean printBill(String target, OrderInfo orderInfo, Unit unit, boolean duplicate) {

        if (target == null) {
            if (mPrinter.getPrinters() != null && mPrinter.getPrinters().size() > 0) {
                target = mPrinter.getPrinters().get(0).getTarget();
            }
        }
        if (TextUtils.isEmpty(target)) {
            String savedIp = mActivity.getSharedPreferences("printer", Context.MODE_PRIVATE)
                    .getString(PrinterSettingsFragment.PREFERENCE_IP, "");
            if (TextUtils.isEmpty(savedIp)) {
                //Toast.makeText(mActivity, "No printer attached. Please give IP manually. ", Toast.LENGTH_SHORT)
                       // .show();
                return false;
            } else {
                mManualTarget = "TCP:" + savedIp;
            }
            target = mManualTarget;
        }

        if (!mPrinter.initializeObject()) {
            return false;
        }

        if (!createOrderReceipt(orderInfo, unit, duplicate)) {

            mPrinter.finalizeObject();
            return false;
        }
        final String finalTarget = target;
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (!mPrinter.printData(finalTarget)) {
                    mPrinter.finalizeObject();
                }
            }
        }).start();
        return true;
    }


    private boolean createOrderReceipt(OrderInfo orderInfo, Unit unit, boolean duplicate) {

        Order order = orderInfo.getOrder();
        StringBuilder text = new StringBuilder();
        if (duplicate) {
            addDuplicateHeader();
        }
        addReceiptHeader(unit, text);
        addOrderHeader(orderInfo, text, duplicate);
        addItemTableHeader(text);
        for (OrderItem item : order.getOrders()) {
            text.append(createItemLinePrint(item));
        }
        text.append(DASHED_WITH_PADDING);
        if (duplicate) {
            addDuplicateHeader();
        }
        text.append(NL);
        addAmountTable(order, text);
        mPrinter.addText(text.toString());
        text.delete(0, text.length());
        addBillAmountLine(orderInfo, duplicate);
        text.append(NL);
        text.append(DASHED_WITH_PADDING);
        text.append(NL);
        addSettlementsHeader(order, text);
        if(unit != null){
            addCompanyFooter(text, unit);
        }
        mPrinter.addText(text.toString());
        mPrinter.addFeedLine(2);

        mPrinter.addCut();

        return true;
    }


    private String createItemLinePrint(OrderItem item) {
        String productName = item.getProductName();

        if (!TextUtils.isEmpty(item.getDimension())) {
            //productName = productName; //+ " (" + item.getDimension().substring(0, 1) + ")";
        }

        BigDecimal totalAmount = item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity()));

        String name = productName;
        String qty = "  " + item.getQuantity();
        String price = "" + item.getPrice().setScale(2, RoundingMode.HALF_UP);
        String amount = "  " + totalAmount.setScale(2,RoundingMode.HALF_UP);
        boolean newLine = false;

        if (name.length() > NAME_LIMIT) {
            name = name.substring(0, NAME_LIMIT);
            newLine = true;
        } else {
            String spaces = new String(new char[NAME_LIMIT - name.length()]).replace
                    ("\0", " ");
            name = name + spaces;

        }
        qty = qty + new String(new char[QTY_LIMIT - qty.length()]).replace
                ("\0", " ");
        price = price + new String(new char[PRICE_LIMIT - price.length()]).replace
                ("\0", " ");
        if (amount.length() < AMOUNT_LIMIT) {
            amount =
                    new String(new char[AMOUNT_LIMIT - amount.length()]).replace
                            ("\0", " ") + amount;
        }

        StringBuilder text = new StringBuilder();
        text.append(L_PADDING).append(name).append(qty).append(price).append(amount).append(NL);
        if (newLine) {
            text.append(L_PADDING).append(productName
                    .substring(NAME_LIMIT, productName.length())).append(NL);
        }
        return text.toString();
    }


    private void addBillAmountLine(OrderInfo orderInfo, boolean duplicate) {
        if (duplicate) {
            addDuplicateHeader();
        }
        mPrinter.setStyleBold();
        StringBuilder text = new StringBuilder();
        text.append(L_PADDING).append(BILL_TOTAL);
        String paid = dcmFormat.format(orderInfo.getOrder().getTransactionDetail().getTotalAmount());
        text.append(new String(new char[PrintTexts.FULL_LENGTH - paid
                .length() - L_PADDING.length() - BILL_TOTAL.length()]).
                replace("\0", " ")).append(paid);
        mPrinter.addText(text.toString());
        mPrinter.setStyleNormal();
    }

    private void addDuplicateHeader() {
        mPrinter.setStyleBold();
        mPrinter.addText(L_PADDING + DUPLICATE_BILL + NL);
        mPrinter.setStyleNormal();
    }


    //this will add order header to our order this code is fine(Generated Order Id)
    private void addOrderHeader(OrderInfo orderInfo, StringBuilder text,
                                boolean duplicate) {
        text.append(L_PADDING).append("Order no:          " + orderInfo.getOrder()
                .getGenerateOrderId());
        text.append(NL);
        text.append(L_PADDING).append("Token no:          " + orderInfo.getToken());
        text.append(NL);
        text.append(L_PADDING).append("Order Time:        " + orderInfo.getOrder().getBillCreationTime());
        text.append(NL);

        if (duplicate) {
            addDuplicateHeader();
        }
        text.append(PrintTexts.NL);
        text.append(PrintTexts.DASHED_WITH_PADDING + PrintTexts.NL);
    }


    private void addReceiptHeader(Unit unit, StringBuilder text) {
        if(unit == null){
            return;
        }
        text.append(PrintTexts.L_PADDING).append(PrintTexts.CHAAYOS)
                .append(PrintTexts.RIGHT_PADDING);
        text.append(PrintTexts.NL);
        text.append(PrintTexts.L_PADDING).append(unit.getName()).append(PrintTexts.NL);
        text.append(PrintTexts.L_PADDING).append(unit.getAddress().getLine1())
                .append(PrintTexts.NL);
        text.append(PrintTexts.L_PADDING).append(unit.getAddress().getCity()).append(", ")
                .append(unit
                        .getAddress().getContact1());
        text.append(PrintTexts.NL);
        text.append(PrintTexts.DASHED_WITH_PADDING).append(PrintTexts.NL);
        mPrinter.addText(text.toString());
        text.delete(0, text.length());
    }

    private void addItemTableHeader(StringBuilder text) {
        String name = "Item";
        String qty = "  " + "Qty";
        String price = "" + "Price";
        String amount = "  " + "Amount";

        String spaces = new String(new char[PrintTexts.NAME_LIMIT - name.length()]).replace
                ("\0", " ");
        name = name + spaces;

        qty = qty + new String(new char[PrintTexts.QTY_LIMIT - qty.length()]).replace
                ("\0", " ");

        price = price + new String(new char[PrintTexts.PRICE_LIMIT - price.length()]).replace
                ("\0", " ");
        amount = new String(new char[PrintTexts.AMOUNT_LIMIT - amount.length()]).replace
                ("\0", " ") + amount;
        String line = L_PADDING + name + qty + price + amount + NL;
        text.append(line).append(NL);
    }

    private void addAmountTable(Order order, StringBuilder text) {
        TransactionDetail transaction = order.getTransactionDetail();
        addTotalLine(transaction, text);
        addVatLine(transaction, text);
        addServiceTaxLine(transaction, text);
        addSurchargeLine(transaction, text);
        addSbCessLine(transaction, text);
        addKkCessLine(transaction, text);
        addRoundOffLine(transaction, text);
    }

    @NonNull
    private void addTotalLine(TransactionDetail transaction, StringBuilder text) {
        text.append(PrintTexts.L_PADDING).append(PrintTexts.TOTAL_LABEL);
        String value = dcmFormat.format(transaction.getTaxableAmount());
        text.append(new String(new char[PrintTexts.FULL_LENGTH - L_PADDING.length() - TOTAL_LABEL
                .length() - value.length()]).replace("\0", " ")).append(value)
                .append(PrintTexts.NL);
    }


    @NonNull
    private void addVatLine(TransactionDetail transaction, StringBuilder text) {
        if (transaction.getNetPriceVat().getPercentage() != null && transaction.getNetPriceVat()
                .getPercentage().compareTo(BigDecimal.ZERO) > 0) {
            text.append(PrintTexts.L_PADDING).append(PrintTexts.NET_VAT_LABEL);
            String percentage = String.valueOf(transaction.getNetPriceVat().getPercentage());
            String value = dcmFormat.format(transaction.getNetPriceVat().getValue());
            text.append(percentage).append(PrintTexts.PERCENT_LABEL);
            text.append(new String(new char[PrintTexts.FULL_LENGTH - L_PADDING.length() -
                    VAT_LABEL.length() - percentage.length() - PERCENT_LABEL.length() - value
                    .length()]).replace("\0", " ")).append(value);
            text.append(PrintTexts.NL);
        }

        if (transaction.getMrpVat().getPercentage() != null && transaction.getMrpVat()
                .getPercentage().compareTo(BigDecimal.ZERO) > 0) {
            String percentage = String.valueOf(transaction.getMrpVat().getPercentage());
            String value = dcmFormat.format(transaction.getMrpVat().getValue());
            text.append(PrintTexts.L_PADDING).append(PrintTexts.VAT_LABEL);
            text.append(percentage).append(PrintTexts.PERCENT_LABEL);
            text.append(new String(new char[PrintTexts.FULL_LENGTH - L_PADDING.length() -
                    VAT_LABEL.length() - percentage.length() - PERCENT_LABEL.length() - value
                    .length()]).replace("\0", " ")).append(value);
            text.append(PrintTexts.NL);
        }
    }

    @NonNull
    private void addSurchargeLine(TransactionDetail transaction, StringBuilder text) {
        if (transaction.getSurchargeOnTax().getPercentage().compareTo(BigDecimal.ZERO) > 0) {
            String percent = String.valueOf(transaction.getSurchargeOnTax().getPercentage());
            String value = dcmFormat.format(transaction.getSurchargeOnTax().getValue());
            text.append(PrintTexts.L_PADDING).append(PrintTexts.SURCHARGE_LABEL);
            text.append(transaction.getSurchargeOnTax().getPercentage())
                    .append(PrintTexts.PERCENT_LABEL);
            text.append(new String(new char[PrintTexts.FULL_LENGTH - value
                    .length() - PrintTexts.SURCHARGE_LABEL
                    .length() - PrintTexts.L_PADDING.length() - percent
                    .length() - PrintTexts.PERCENT_LABEL.length()])
                    .replace("\0", " ") + value);
            text.append(PrintTexts.NL);
        }
    }

    @NonNull
    private void addServiceTaxLine(TransactionDetail transaction, StringBuilder text) {
        if (transaction.getServiceTax().getPercentage().compareTo(BigDecimal.ZERO) > 0) {
            String value = dcmFormat.format(transaction.getServiceTax().getValue());
            String percentage = String.valueOf(transaction.getServiceTax().getPercentage());
            text.append(L_PADDING).append(SERVICE_TAX_LABEL);
            text.append(percentage).append(PERCENT_LABEL);
            text.append(new String(new char[FULL_LENGTH - value.length() - SERVICE_TAX_LABEL
                    .length() - L_PADDING.length() - percentage.length() -
                    PERCENT_LABEL.length()]).replace("\0", " "))
                    .append(value);
            text.append(PrintTexts.NL);
        }
    }


    @NonNull
    private void addSbCessLine(TransactionDetail transaction, StringBuilder text) {
        if (transaction.getSbCess().getPercentage().compareTo(BigDecimal.ZERO) > 0) {
            String value = dcmFormat.format(transaction.getSbCess().getValue());
            String percentage = String.valueOf(transaction.getSbCess().getPercentage());
            text.append(L_PADDING).append(SB_CESS_LABEL).append(percentage);
            text.append(new String(new char[FULL_LENGTH - L_PADDING.length() - SB_CESS_LABEL
                    .length() -
                    percentage.length() - value.length()]).replace("\0", " ")).append(value);
            text.append(NL);
        }
    }

    @NonNull
    private void addKkCessLine(TransactionDetail transaction, StringBuilder text) {
        if (transaction.getSbCess().getPercentage().compareTo(BigDecimal.ZERO) > 0) {
            String value = dcmFormat.format(transaction.getSbCess().getValue());
            String percentage = String.valueOf(transaction.getSbCess().getPercentage());
            text.append(L_PADDING).append(KK_CESS_LABEL).append(percentage);
            text.append(new String(new char[FULL_LENGTH - L_PADDING.length() - SB_CESS_LABEL
                    .length() -
                    percentage.length() - value.length()]).replace("\0", " ")).append(value);
            text.append(NL);
        }
    }

    @NonNull
    private void addRoundOffLine(TransactionDetail transaction, StringBuilder text) {
        if (transaction.getRoundOffValue().compareTo(BigDecimal.ZERO) > 0) {
            text.append(L_PADDING).append(ROUND_OFF_LABEL);
            String value = dcmFormat.format(transaction.getRoundOffValue());
            text.append(new String(new char[PrintTexts.FULL_LENGTH - L_PADDING.length() -
                    ROUND_OFF_LABEL.length() - value.length()]).replace("\0", " ")).append(value);
            text.append(NL);
        }
    }

    private void addSettlementsHeader(Order order, StringBuilder builder) {
        for (Settlement settlement : order.getSettlements()) {
            builder.append("  " + settlement.getModeDetail()
                    .getDescription() + new String(new char[PrintTexts.FULL_LENGTH - dcmFormat
                    .format(order.getTotalAmount()).length() - settlement.getModeDetail()
                    .getDescription().length() - 2]).replace("\0", " ") + order.getTotalAmount());
            builder.append(PrintTexts.NL);
        }
        builder.append(DASHED_WITH_PADDING);
        builder.append(NL);
    }


    //this will provide footer for the print

    private void addCompanyFooter(StringBuilder text, Unit unit) {
        text.append(CIN_LABEL + new String(new char[FULL_LENGTH - CIN_VALUE
                .length() -
                CIN_LABEL.length()]).replace("\0", " ") + PrintTexts.CIN_VALUE);
        text.append(PrintTexts.NL);
        text.append(PrintTexts.TIN_LABEL + new String(new char[PrintTexts.FULL_LENGTH - unit
                .getTin().length() -
                PrintTexts.TIN_LABEL.length()]).replace("\0", " ") + unit.getTin());
        text.append(PrintTexts.NL);
        text.append(PrintTexts.STN_LABEL + new String(new char[PrintTexts.FULL_LENGTH - PrintTexts.STN_VALUE
                .length() -
                PrintTexts.STN_LABEL.length()]).replace("\0", " ") + PrintTexts.STN_VALUE);
        text.append(PrintTexts.NL);
        text.append(NL);
        text.append(L_PADDING).append(PrintTexts.COMP_ADDRESS_LINE_1);
        text.append(NL);
        text.append(L_PADDING).append(PrintTexts.COMP_ADDRESS_LINE_2);
        text.append(NL);
        text.append(L_PADDING).append(PrintTexts.COMP_ADDRESS_LINE_3);
        text.append(NL);
        text.append(NL);
        text.append(L_PADDING).append(PrintTexts.BOTTOM_LINE);
    }

    public void closePrinter() {
        mPrinter.stopDiscovery();
    }


    //Test print code.
    public Boolean testPrint(String target) {

        if (target == null) {
            if (mPrinter.getPrinters() != null && mPrinter.getPrinters().size() > 0) {
                target = mPrinter.getPrinters().get(0).getTarget();
            }
        }
        if (TextUtils.isEmpty(target)) {
            String savedIp = mActivity.getSharedPreferences("printer", Context.MODE_PRIVATE)
                    .getString(PrinterSettingsFragment.PREFERENCE_IP, "");
            if (TextUtils.isEmpty(savedIp)) {
                return false;
            } else {
                mManualTarget = "TCP:" + savedIp;
            }
            target = mManualTarget;
        }

        mPrinter.testPrint(target);
        return true;
    }

    public void restartDiscovery() {
       mPrinter.restartDiscovery();
    }


}
