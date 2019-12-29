package club.crabglory.www.factory.presenter;

import android.text.TextUtils;

import java.io.File;
import java.io.IOException;

import club.crabglory.www.common.basic.model.DataSource;
import club.crabglory.www.common.utils.StringsUtils;
import club.crabglory.www.data.model.db.ETCBFile;
import club.crabglory.www.data.model.net.FileRspModel;
import club.crabglory.www.data.model.net.RspModel;
import club.crabglory.www.data.netkit.NetKit;
import club.crabglory.www.data.netkit.RemoteService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FileHelper {

    public static void saveBackgroundFile(String path, DataSource.Callback<ETCBFile> callback) {

        String name = new File(path).getName();
        String imageString = StringsUtils.ImageToStrings(path);
        Call<RspModel<ETCBFile>> task = NetKit.remote().saveFile(new FileRspModel(name, imageString));
        task.enqueue(new FileCallBack(callback));
    }

    private static class FileCallBack implements Callback<RspModel<ETCBFile>> {
        private DataSource.Callback<ETCBFile> callback;

        public FileCallBack(DataSource.Callback<ETCBFile> callback) {

            this.callback = callback;
        }

        @Override
        public void onResponse(Call<RspModel<ETCBFile>> call, Response<RspModel<ETCBFile>> response) {
            // todo 处理失败逻辑
        }

        @Override
        public void onFailure(Call<RspModel<ETCBFile>> call, Throwable t) {

        }
    }

    public static String fetchBackgroundFile(String path) {
        if (TextUtils.isEmpty(path)) return "";
        String name = new File(path).getName();
        String imageString = StringsUtils.ImageToStrings(path);
        Call<RspModel<ETCBFile>> task = NetKit.remote().saveFile(new FileRspModel(name, imageString));
        String urlString = null;
        try {
            Response<RspModel<ETCBFile>> execute = task.execute();
            if (execute.body() == null){
                return null;
            }
            urlString = execute.body().getResult().getPath();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return urlString;
    }


}
