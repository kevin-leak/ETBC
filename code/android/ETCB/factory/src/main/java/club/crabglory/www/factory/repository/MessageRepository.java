package club.crabglory.www.factory.repository;

import android.util.Log;

import com.raizlabs.android.dbflow.sql.language.OperatorGroup;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.List;

import club.crabglory.www.data.model.db.Message;
import club.crabglory.www.data.model.db.Message_Table;
import club.crabglory.www.factory.contract.MessageDataSource;

public class MessageRepository extends BaseDbRepository<Message> implements MessageDataSource {
    public String TAG = this.getClass().getName();
    private String receiverId;

    public MessageRepository(String receiverId) {
        super();
        this.receiverId = receiverId;
    }

    @Override
    public void load(SucceedCallback<List<Message>> callback) {
        super.load(callback);
        Log.e(TAG, "load: " + "-----本地数据加载-----" );
//        SQLite.select()
//                .from(Message.class)
//                .where(OperatorGroup.clause()
//                        .and(Message_Table.type.eq(Message.RECEIVER_TYPE_NONE))
//                        .and(Message_Table.sender_id.eq(receiverId))
//                        .or(Message_Table.receiver_id.eq(receiverId)))
//                .orderBy(Message_Table.createAt, false)
//                .limit(30)
//                .async()
//                .queryListResultCallback(this)
//                .execute();
    }



    @Override
    public boolean isRequired(Message message) {
        return true;
    }
}
