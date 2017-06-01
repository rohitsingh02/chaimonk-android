package www.chaayos.com.chaimonkbluetoothapp.printer;

import android.app.Activity;
import android.graphics.Bitmap;
import android.util.Log;

import com.epson.epos2.Epos2Exception;
import com.epson.epos2.discovery.DeviceInfo;
import com.epson.epos2.discovery.Discovery;
import com.epson.epos2.discovery.DiscoveryListener;
import com.epson.epos2.discovery.FilterOption;
import com.epson.epos2.printer.Printer;
import com.epson.epos2.printer.PrinterStatusInfo;
import com.epson.epos2.printer.ReceiveListener;

import java.util.ArrayList;
import java.util.List;
import static www.chaayos.com.chaimonkbluetoothapp.printer.PrintTexts.FULL_LENGTH_DASHED;
import static www.chaayos.com.chaimonkbluetoothapp.printer.PrintTexts.NL;

public class EpsonHelper implements ReceiveListener {


    private static final String LOG_TAG = EpsonHelper.class.getSimpleName();
    private static EpsonHelper sInstance;
    private Activity mActivity;
    private List<PrintEventsCallback> mEventsCallbacks = new ArrayList<>();
    private Printer mPrinter;
    private ArrayList<DeviceInfo> mPrinters = new ArrayList<>();
    private FilterOption mFilterOption = null;
    private DiscoveryListener mDiscoveryListener = new DiscoveryListener() {
        @Override
        public void onDiscovery(final DeviceInfo deviceInfo) {

            mActivity.runOnUiThread(new Runnable() {
                @Override
                public synchronized void run() {
                    mPrinters.add(deviceInfo);
                    notifyPrinterDiscovered(deviceInfo);
                }
            });
        }
    };

    public EpsonHelper(Activity activity) {
        mActivity = activity;
    }


    public static EpsonHelper getInstance(Activity activity) {
        if (sInstance == null) {
            sInstance = new EpsonHelper(activity);
        } else {
            sInstance.mActivity = activity;
        }
        return sInstance;
    }


    public ArrayList<DeviceInfo> getPrinters() {
        return mPrinters;
    }

    public void startDiscovery() {
        mFilterOption = new FilterOption();
        mFilterOption.setDeviceType(Discovery.TYPE_PRINTER);
        mFilterOption.setEpsonFilter(Discovery.FILTER_NAME);
        try {
            Discovery.start(mActivity, mFilterOption, mDiscoveryListener);
        } catch (Exception e) {
            PrinterUtils.showException(e, "start", mActivity);
        }
    }

    public void stopDiscovery() {
        while (true) {
            try {
                if(Discovery.isRunning()){
                    Discovery.stop();
                }
                break;
            } catch (Epos2Exception e) {
                if (e.getErrorStatus() != Epos2Exception.ERR_PROCESSING) {
                    break;
                }
            }
        }

        mFilterOption = null;
    }


    public void restartDiscovery() {
        while (true) {
            try {
                Discovery.stop();
                break;
            } catch (Epos2Exception e) {
                if (e.getErrorStatus() != Epos2Exception.ERR_PROCESSING) {
                    PrinterUtils.showException(e, "stop", mActivity);
                    return;
                }
            }
        }

        mPrinters.clear();

        try {
            Discovery.start(mActivity, mFilterOption, mDiscoveryListener);
        } catch (Exception e) {
            PrinterUtils.showException(e, "stop", mActivity);
        }
    }

    public boolean initializeObject() {
        try {
            mPrinter = new Printer(Printer.TM_T82, Printer.MODEL_ANK, mActivity);
        } catch (Exception e) {
            PrinterUtils.showException(e, "PrintFeature", mActivity);
            return false;
        }
        mPrinter.setReceiveEventListener(this);
        return true;
    }

    private void disconnectPrinter() {
        if (mPrinter == null) {
            return;
        }

        try {
            mPrinter.endTransaction();
        } catch (final Exception e) {
            mActivity.runOnUiThread(new Runnable() {
                @Override
                public synchronized void run() {
                    PrinterUtils.showException(e, "endTransaction", mActivity);
                }
            });
        }

        try {
            mPrinter.disconnect();
        } catch (final Exception e) {
            mActivity.runOnUiThread(new Runnable() {
                @Override
                public synchronized void run() {
                    PrinterUtils.showException(e, "disconnect", mActivity);
                }
            });
        }

        finalizeObject();
    }


    public void finalizeObject() {
        if (mPrinter == null) {
            return;
        }
        mPrinter.clearCommandBuffer();
        mPrinter.setReceiveEventListener(null);
        mPrinter = null;
    }

    private boolean connectPrinter(String target) {
        boolean isBeginTransaction = false;

        if (mPrinter == null) {
            return false;
        }

        try {
            mPrinter.connect(target, Printer.PARAM_DEFAULT);
        } catch (Exception e) {PrinterUtils.showException(e, "connect", mActivity);
            return false;
        }

        try {
            mPrinter.beginTransaction();
            isBeginTransaction = true;
        } catch (Exception e) {
            PrinterUtils.showException(e, "beginTransaction", mActivity);
        }

        if (!isBeginTransaction) {
            try {
                mPrinter.disconnect();
            } catch (Epos2Exception e) {
                // Do nothing
                return false;
            }
        }

        return true;
    }

    public boolean printData(String target) {
        if (mPrinter == null) {
            return false;
        }

        if (!connectPrinter(target)) {
            return false;
        }

        PrinterStatusInfo status = mPrinter.getStatus();
        if (!isPrintable(status)) {
            PrinterUtils.showMsg(status, mActivity);
            try {
                mPrinter.disconnect();
            } catch (Exception ex) {
                // Do nothing
            }
            return false;
        }

        try {
            mPrinter.sendData(Printer.PARAM_DEFAULT);
        } catch (Exception e) {
            PrinterUtils.showException(e, "sendData", mActivity);
            try {
                mPrinter.disconnect();
            } catch (Exception ex) {

            }
            return false;
        }

        return true;
    }

    private boolean isPrintable(PrinterStatusInfo status) {
        if (status == null) {
            return false;
        }

        if (status.getConnection() == Printer.FALSE) {
            return false;
        } else if (status.getOnline() == Printer.FALSE) {
            return false;
        } else {
            ;//print available
        }

        return true;
    }

    void addPrintLogo(Bitmap logo) throws Epos2Exception {
        if (logo != null) {
            mPrinter.addImage(logo, 0, 0,
                    logo.getWidth(),
                    logo.getHeight(),
                    Printer.COLOR_1,
                    Printer.MODE_MONO,
                    Printer.HALFTONE_DITHER,
                    Printer.PARAM_DEFAULT,
                    Printer.COMPRESS_AUTO);
        }
    }


    public void setActivity(Activity mActivity) {
        this.mActivity = mActivity;
    }

    public void attachPrintEventCallbacks(PrintEventsCallback callback) {
        mEventsCallbacks.add(callback);
    }

    public void removePrintEventCallback(PrintEventsCallback callback) {
        mEventsCallbacks.remove(callback);
    }

    @Override
    public void onPtrReceive(Printer printer, final int code, final PrinterStatusInfo status,
                             String s) {

        mActivity.runOnUiThread(new Runnable() {
            @Override
            public synchronized void run() {
                if (code == 0) {
                    notifyPrintSuccess();
                } else if (code == 255) {
                    notifyPrintFailure();
                }
                PrinterUtils.showResult(code, status, mActivity);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        disconnectPrinter();
                    }
                }).start();
            }
        });

    }

    public void addText(String text) {
        try {
            mPrinter.addText(text);
        } catch (Epos2Exception e) {
            e.printStackTrace();
        }
    }

    private void notifyPrintSuccess() {
        for (PrintEventsCallback callback : mEventsCallbacks) {
            callback.onPrintSuccess();
        }
    }

    private void notifyPrintFailure() {
        for (PrintEventsCallback callback : mEventsCallbacks) {
            callback.onPrintFailure();
        }
    }

    private void notifyPrinterDiscovered(DeviceInfo deviceInfo) {
        for (PrintEventsCallback callback : mEventsCallbacks) {
            callback.onDiscovery(deviceInfo);
        }
    }

    public void addFeedLine(int i) {
        try {
            mPrinter.addFeedLine(i);
        } catch (Epos2Exception e) {
            e.printStackTrace();
        }
    }

    public void addCut() {
        try {
            mPrinter.addCut(Printer.CUT_FEED);
        } catch (Epos2Exception e) {
            e.printStackTrace();
        }
    }


    public void addTextSize(int i, int i1) {
        try {
            mPrinter.addTextSize(i, i1);
        } catch (Epos2Exception e) {
            e.printStackTrace();
        }
    }


    public void setStyleBold() {
        try {
            mPrinter.addTextStyle(Printer.PARAM_DEFAULT, Printer.PARAM_DEFAULT, Printer.TRUE, Printer.PARAM_DEFAULT);
        } catch (Epos2Exception e) {
            e.printStackTrace();
        }
    }

    public boolean testPrint(String target) {

        if (target == null) {
            if (mPrinters != null && mPrinters.size() > 0) {
                target = mPrinters.get(0).getTarget();
            } else {
                return false;
            }

        }
        if (!initializeObject()) {
            return false;
        }

        if (!createTestPrintReceipt()) {
            finalizeObject();
            return false;
        }
        final String finalTarget = target;
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (!printData(finalTarget)) {
                    finalizeObject();
                }
            }
        }).start();


        return true;
    }

    private boolean createTestPrintReceipt() {
        if (mPrinter == null) {
            return false;
        }
        try {
            mPrinter.addTextAlign(Printer.PARAM_DEFAULT);
            StringBuilder text = new StringBuilder();
            text.append(FULL_LENGTH_DASHED).append(NL);
            text.append("Test Print").append(NL);
            text.append(FULL_LENGTH_DASHED).append(NL);
            mPrinter.addText(text.toString());
            mPrinter.addFeedLine(2);
            mPrinter.addCut(Printer.CUT_FEED);
        } catch (Epos2Exception e) {
            Log.e(LOG_TAG, "testPrint()", e);
        }
        return true;

    }

    public void setStyleNormal() {

    }

    public interface PrintEventsCallback {

        void onDiscovery(DeviceInfo deviceInfo);

        void onPrintSuccess();

        void onPrintFailure();
    }

}
