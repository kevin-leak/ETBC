package club.crabglory.www.etcb.frags.book;

import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import club.crabglory.www.common.basic.view.ToolbarActivity;
import club.crabglory.www.etcb.R;

public class UploadBookActivity extends ToolbarActivity {

    @BindView(R.id.sp_category)
    Spinner spCategory;

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_upload_book;
    }

    @Override
    protected void initWindows() {
        super.initWindows();
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        initSpan();
    }

    private void initSpan() {
        List<String> list = new ArrayList<String>(9);
        list.add("类别一");
        list.add("类别二");
        list.add("类别三");
        list.add("类别四");
        setSpinner(spCategory, list);
    }


    public void setSpinner(Spinner sp, List<String> list) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        sp.setAdapter(adapter);
    }

    /**
     * 获取到屏幕信息进行适配
     */
    private void getDisplay() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
    }


}
