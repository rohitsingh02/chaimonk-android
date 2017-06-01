package www.chaayos.com.chaimonkbluetoothapp.data.provider;

import java.util.ArrayList;
import java.util.List;

import www.chaayos.com.chaimonkbluetoothapp.data.model.ChaiMonkViewContent;
import www.chaayos.com.chaimonkbluetoothapp.management.service.ChaiMonkContentViewManagementService;

/**
 * Created by rohitsingh on 05/07/16.
 */
public class OrderDataProvider {
    int[] monkNumber = {1,2,3,4,5,6,7,8,9,10};
    String[] monkStatus = {"Not Connected","Not Connected","Not Connected","Not Connected","Not Connected","Not Connected","Not Connected","Not Connected","Not Connected","Not Connected"};
    String[] monkOrderItemName = {"Desi Chai","Sulemani Chai","Green Tea","Coffee","Desi Chai","Sulemani Chai","Green Tea","Coffee","Desi Chai","Sulemani Chai"};
    int[] orderItemQty = {2,5,4,6,7,9,2,10,6,4};
    String[] orderItemIds = {"342,435","342,433","123,321","143,543","214,765","342,435","342,433","123,321","143,543","214,765"};
    ArrayList<ChaiMonkViewContent> chaiMonkViewContentArrayList = new ArrayList<>();

    public void setData(){
        ChaiMonkContentViewManagementService chaiMonkContentViewManagementService = new ChaiMonkContentViewManagementService();
        for(int i = 0;i<monkNumber.length;i++){
            ChaiMonkViewContent chaiMonkViewContent =  chaiMonkContentViewManagementService.createChaiMonkViewContent(monkNumber[i],monkStatus[i],monkOrderItemName[i],orderItemQty[i],orderItemIds[i]);
           chaiMonkViewContentArrayList.add(chaiMonkViewContent);
        }

    }

    public List<ChaiMonkViewContent> getChaiMonkViewContentList(){
        return chaiMonkViewContentArrayList;
    }
}
