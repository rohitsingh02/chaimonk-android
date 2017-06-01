package www.chaayos.com.chaimonkbluetoothapp.printer;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

import com.epson.epos2.Epos2CallbackCode;
import com.epson.epos2.Epos2Exception;
import com.epson.epos2.printer.Printer;
import com.epson.epos2.printer.PrinterStatusInfo;

public class PrinterUtils {
    public static void showException(final Exception e, final String method,
                                     final Activity context) {
        context.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String msg = "";
                if (e instanceof Epos2Exception) {
                    msg = String.format(
                            "%s\n\t%s\n%s\n\t%s",
                            "Error Code",
                            getEposExceptionText(((Epos2Exception) e).getErrorStatus()),
                            "Method",
                            method);
                } else {
                    msg = e.toString();
                }
                //show(msg, context);
            }
        });

    }

    public static void showResult(final int code, PrinterStatusInfo status, final Activity
            context) {
        final String errMsg = makeErrorMessage(status, context);
        context.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String msg = "";
                if (errMsg.isEmpty()) {
                    msg = String.format(
                            "\t%s\n\t%s\n",
                            "Result",
                            getCodeText(code));
                } else {
                    msg = String.format(
                            "\t%s\n\t%s\n\n\t%s\n\t%s\n",
                            "Result",
                            getCodeText(code),
                            "Description",
                            errMsg);
                }
                show(msg, context);
            }
        });

    }

    public static void showMsg(final PrinterStatusInfo status, final Activity context) {
        context.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                show(makeErrorMessage(status, context), context);
            }
        });
    }

    private static void show(String msg, Activity context) {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setMessage(msg);
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                return;
            }
        });
        alertDialog.create();
        //alertDialog.show();
    }

    private static String getEposExceptionText(int state) {
        String return_text = "";
        switch (state) {
            case Epos2Exception.ERR_PARAM:
                return_text = "ERR_PARAM";
                break;
            case Epos2Exception.ERR_CONNECT:
                return_text = "ERR_CONNECT";
                break;
            case Epos2Exception.ERR_TIMEOUT:
                return_text = "ERR_TIMEOUT";
                break;
            case Epos2Exception.ERR_MEMORY:
                return_text = "ERR_MEMORY";
                break;
            case Epos2Exception.ERR_ILLEGAL:
                return_text = "ERR_ILLEGAL";
                break;
            case Epos2Exception.ERR_PROCESSING:
                return_text = "ERR_PROCESSING";
                break;
            case Epos2Exception.ERR_NOT_FOUND:
                return_text = "ERR_NOT_FOUND";
                break;
            case Epos2Exception.ERR_IN_USE:
                return_text = "ERR_IN_USE";
                break;
            case Epos2Exception.ERR_TYPE_INVALID:
                return_text = "ERR_TYPE_INVALID";
                break;
            case Epos2Exception.ERR_DISCONNECT:
                return_text = "ERR_DISCONNECT";
                break;
            case Epos2Exception.ERR_ALREADY_OPENED:
                return_text = "ERR_ALREADY_OPENED";
                break;
            case Epos2Exception.ERR_ALREADY_USED:
                return_text = "ERR_ALREADY_USED";
                break;
            case Epos2Exception.ERR_BOX_COUNT_OVER:
                return_text = "ERR_BOX_COUNT_OVER";
                break;
            case Epos2Exception.ERR_BOX_CLIENT_OVER:
                return_text = "ERR_BOX_CLIENT_OVER";
                break;
            case Epos2Exception.ERR_UNSUPPORTED:
                return_text = "ERR_UNSUPPORTED";
                break;
            case Epos2Exception.ERR_FAILURE:
                return_text = "ERR_FAILURE";
                break;
            default:
                return_text = String.format("%d", state);
                break;
        }
        return return_text;
    }

    private static String getCodeText(int state) {
        String return_text = "";
        switch (state) {
            case Epos2CallbackCode.CODE_SUCCESS:
                return_text = "PRINT_SUCCESS";
                break;
            case Epos2CallbackCode.CODE_PRINTING:
                return_text = "PRINTING";
                break;
            case Epos2CallbackCode.CODE_ERR_AUTORECOVER:
                return_text = "ERR_AUTORECOVER";
                break;
            case Epos2CallbackCode.CODE_ERR_COVER_OPEN:
                return_text = "ERR_COVER_OPEN";
                break;
            case Epos2CallbackCode.CODE_ERR_CUTTER:
                return_text = "ERR_CUTTER";
                break;
            case Epos2CallbackCode.CODE_ERR_MECHANICAL:
                return_text = "ERR_MECHANICAL";
                break;
            case Epos2CallbackCode.CODE_ERR_EMPTY:
                return_text = "ERR_EMPTY";
                break;
            case Epos2CallbackCode.CODE_ERR_UNRECOVERABLE:
                return_text = "ERR_UNRECOVERABLE";
                break;
            case Epos2CallbackCode.CODE_ERR_FAILURE:
                return_text = "ERR_FAILURE";
                break;
            case Epos2CallbackCode.CODE_ERR_NOT_FOUND:
                return_text = "ERR_NOT_FOUND";
                break;
            case Epos2CallbackCode.CODE_ERR_SYSTEM:
                return_text = "ERR_SYSTEM";
                break;
            case Epos2CallbackCode.CODE_ERR_PORT:
                return_text = "ERR_PORT";
                break;
            case Epos2CallbackCode.CODE_ERR_TIMEOUT:
                return_text = "ERR_TIMEOUT";
                break;
            case Epos2CallbackCode.CODE_ERR_JOB_NOT_FOUND:
                return_text = "ERR_JOB_NOT_FOUND";
                break;
            case Epos2CallbackCode.CODE_ERR_SPOOLER:
                return_text = "ERR_SPOOLER";
                break;
            case Epos2CallbackCode.CODE_ERR_BATTERY_LOW:
                return_text = "ERR_BATTERY_LOW";
                break;
            default:
                return_text = String.format("%d", state);
                break;
        }
        return return_text;
    }

    private static String makeErrorMessage(PrinterStatusInfo status, Activity activity) {
        String msg = "";

        if (status.getOnline() == Printer.FALSE) {
            msg += "Printer is offline";
        }
        if (status.getConnection() == Printer.FALSE) {
            msg += "Please check the connection of printer and the mobile";
        }
        if (status.getCoverOpen() == Printer.TRUE) {
            msg += "Please close roll paper cover. ";
        }
        if (status.getPaper() == Printer.PAPER_EMPTY) {
            msg += "Please check roll paper. ";
        }
        if (status.getPaperFeed() == Printer.TRUE || status.getPanelSwitch() == Printer.SWITCH_ON) {
            msg += "Please release a paper feed switch. ";
        }
        if (status.getErrorStatus() == Printer.MECHANICAL_ERR || status
                .getErrorStatus() == Printer.AUTOCUTTER_ERR) {
            msg += "Please release jammed paper and close roll paper cover.  ";
            msg += "Then, If the printer doesn't recover from error,please..... ";
        }
        if (status.getErrorStatus() == Printer.UNRECOVER_ERR) {
            msg += "unrecover error";
        }
        if (status.getErrorStatus() == Printer.AUTORECOVER_ERR) {
            if (status.getAutoRecoverError() == Printer.HEAD_OVERHEAT) {
                msg += "head overheat";
            }
            if (status.getAutoRecoverError() == Printer.MOTOR_OVERHEAT) {
                msg += "motor overheat";
            }
            if (status.getAutoRecoverError() == Printer.BATTERY_OVERHEAT) {
                msg += "battery overheat";

            }
            if (status.getAutoRecoverError() == Printer.WRONG_PAPER) {
                msg += "wrong paper";
            }
        }
        if (status.getBatteryLevel() == Printer.BATTERY_LEVEL_0) {
            msg += "battery level is 0";
        }

        return msg;
    }


}
