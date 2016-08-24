package com.mindorks.test.expandable;

import android.graphics.Color;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mindorks.placeholderview.Animation;
import com.mindorks.placeholderview.annotations.Animate;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;
import com.mindorks.placeholderview.annotations.expand.ChildPosition;
import com.mindorks.placeholderview.annotations.expand.Parent;
import com.mindorks.placeholderview.annotations.expand.ParentPosition;
import com.mindorks.test.R;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by janisharali on 19/08/16.
 */

@Animate
@Layout(R.layout.drawer_item)
public class ChildItem {

    private List<List<String>> childTitleList;

    @ParentPosition
    private int mParentPosition;

    @ChildPosition
    private int mChildPosition;

    @View(R.id.itemNameTxt)
    private TextView itemNameTxt;

    @View(R.id.itemIcon)
    private ImageView itemIcon;

    @View(R.id.mainView)
    private LinearLayout mainView;

    public ChildItem() {
        childTitleList = new ArrayList<>();
        List<String>titleList1 = new ArrayList<>();
        titleList1.add("a");
        titleList1.add("b");
        titleList1.add("c");
        titleList1.add("d");
        titleList1.add("e");
        childTitleList.add(titleList1);

        List<String>titleList2 = new ArrayList<>();
        titleList2.add("aa");
        titleList2.add("bb");
        titleList2.add("cc");
        titleList2.add("dd");
        titleList2.add("ee");
        childTitleList.add(titleList2);

        List<String>titleList3 = new ArrayList<>();
        titleList3.add("aaa");
        titleList3.add("bbb");
        titleList3.add("ccc");
        titleList3.add("ddd");
        titleList3.add("eee");
        childTitleList.add(titleList3);

        List<String>titleList4 = new ArrayList<>();
        titleList4.add("aaaa");
        titleList4.add("bbbb");
        titleList4.add("cccc");
        titleList4.add("dddd");
        titleList4.add("eeee");
        childTitleList.add(titleList4);
    }

    @Resolve
    private void onResolved() {
        mainView.setBackgroundColor(Color.GRAY);
        itemNameTxt.setText(childTitleList.get(mParentPosition).get(mChildPosition));
    }

//    @Click(R.id.mainView)
//    private void onMenuItemClick(){
//
//    }
}
