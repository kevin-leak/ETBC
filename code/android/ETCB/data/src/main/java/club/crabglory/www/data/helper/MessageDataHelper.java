package club.crabglory.www.data.helper;

import com.raizlabs.android.dbflow.sql.language.SQLite;

import club.crabglory.www.common.utils.NetUtils;
import club.crabglory.www.data.DataKit;
import club.crabglory.www.data.model.db.Message;
import club.crabglory.www.data.model.db.Message_Table;
import club.crabglory.www.data.model.net.MessageRspModel;
import club.crabglory.www.data.netkit.NetKit;

public class MessageDataHelper {
    public static void push(MessageRspModel rspModel) {
        // todo push
        //fixme  just for test
        DbHelper.save(Message.class, rspModel.toMessage());
        DataKit.Companion.getMessageCenter().dispatch(rspModel.toMessageViewModel());
    }

    /**
     * @param id chat ID , not is user
     * @return message from db
     */
    public static Message findFromLocal(String id) {
        return SQLite.select().from(Message.class)
                .where(Message_Table.id.eq(id))
                .querySingle();
    }
}
