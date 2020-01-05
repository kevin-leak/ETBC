package club.crabglory.www.data.model.net;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class Test {
    public static void main(String[] arg){
        RspModel<AccountRspModel> rspModel = new RspModel<>();
        rspModel.setResult(new AccountRspModel());
        ModifyRspModel model = new ModifyRspModel("", "", 1);
//        ETCBFile file = new ETCBFile();
//        file.setId("");
//        file.setCreateAt(new Date());
//        file.setPath("");
//        System.out.print(file.toString());
//        MaterialRspModel materialRspModel = new MaterialRspModel();
//        System.out.print(materialRspModel.toString());
//        RspModel<List<Goods>> listRspModel = new RspModel<>();
//        ArrayList<Goods> goods = new ArrayList<>();
//        Goods g = new Goods();
//        g.setBook(new Book());
//        g.setCreateAt(new Date());
//        goods.add(g);
//        listRspModel.setResult(goods);
//        System.out.print(listRspModel.toString());

//        List<PayRspModel> rspModels = new ArrayList<>();
//        PayRspModel payRspModel = new PayRspModel();
//        payRspModel.setId("");
//        payRspModel.setType(1);
//        payRspModel.setConsumer("");
//        rspModels.add(payRspModel);
//        System.out.println(Arrays.toString(rspModels.toArray()));

        System.out.printf(UUID.randomUUID().toString());
    }
}
