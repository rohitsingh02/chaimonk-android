package www.chaayos.com.chaimonkbluetoothapp.domain.model.new_model;

/**
 * Created by rohitsingh on 16/07/16.
 */
public class OrderInfo {

        private int token;
        private Order order;
        private Customer customer;
        private IdCodeName deliveryPartner;
        private IdCodeName channelPartner;
        private boolean mDuplicatePrinted;

        public Order getOrder() {
            return order;
        }

        public void setOrder(Order order) {
            this.order = order;
        }

        public Customer getCustomer() {
            return customer;
        }

        public void setCustomer(Customer customer) {
            this.customer = customer;
        }

        public IdCodeName getDeliveryPartner() {
            return deliveryPartner;
        }

        public void setDeliveryPartner(IdCodeName deliveryPartner) {
            this.deliveryPartner = deliveryPartner;
        }

        public IdCodeName getChannelPartner() {
            return channelPartner;
        }

        public void setChannelPartner(IdCodeName channelPartner) {
            this.channelPartner = channelPartner;
        }

        @Override
        public boolean equals(Object obj) {

            if (obj != null) {
                if (obj instanceof OrderInfo) {
                    Order order = ((OrderInfo) obj).getOrder();
                    if (order != null) {
                        if (this.getOrder() != null) {
                            return order.getGenerateOrderId().equals(this.getOrder().getGenerateOrderId());
                        }
                    }
                }
            }
            return false;
        }


        public boolean isDuplicatePrinted() {
            return mDuplicatePrinted;
        }

        public void setDuplicatePrinted(boolean mDuplicatePrinted) {
            this.mDuplicatePrinted = mDuplicatePrinted;
        }

    public int getToken() {
        return token;
    }

    public void setToken(int token) {
        this.token = token;
    }
}




