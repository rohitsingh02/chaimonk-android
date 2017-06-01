package www.chaayos.com.chaimonkbluetoothapp.management.service;

import www.chaayos.com.chaimonkbluetoothapp.data.model.ChaiMonkViewContent;

/**
 * Created by rohitsingh on 23/07/16.
 */
public class ChaiMonkContentViewManagementService {

    public ChaiMonkViewContent createChaiMonkViewContent(int monkNumber,String monkStatus,String monkOrderItemName,int monkOrderItemQty,String monkOrderItemIds){
        ChaiMonkViewContent chaiMonkViewContent = new ChaiMonkViewContent(monkNumber,monkStatus,monkOrderItemName,monkOrderItemQty,monkOrderItemIds);
        return chaiMonkViewContent;
    }
}
