package eccrm.base.im;

import eccrm.base.im.domain.News;

/**
 * @author Michael
 */
public class NewsMessage extends News implements Message {
    // 接收者
    private String receiverId;


    public NewsMessage(String receiverId) {
        this.receiverId = receiverId;
    }

    @Override
    public String getReceiver() {
        return receiverId;
    }

}
