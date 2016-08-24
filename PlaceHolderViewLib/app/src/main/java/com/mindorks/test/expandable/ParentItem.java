package com.mindorks.test.expandable;

import android.content.Context;
import android.graphics.Color;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.expand.Collapse;
import com.mindorks.placeholderview.annotations.expand.Expand;
import com.mindorks.placeholderview.annotations.expand.Parent;
import com.mindorks.placeholderview.annotations.Position;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.expand.ParentPosition;
import com.mindorks.placeholderview.annotations.expand.SingleTop;
import com.mindorks.placeholderview.annotations.View;
import com.mindorks.test.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by janisharali on 19/08/16.
 */
@Parent
@SingleTop
@Layout(R.layout.drawer_item)
public class ParentItem {

    private List<String> titleList;

    @ParentPosition
    private int mParentPosition;

    @View(R.id.itemNameTxt)
    private TextView itemNameTxt;

    @View(R.id.itemIcon)
    private ImageView itemIcon;

    @View(R.id.mainView)
    private LinearLayout mainView;

    private Context mContext;

    public ParentItem(Context context) {
        mContext = context;
        titleList = new ArrayList<>();
        titleList.add("Apple");
        titleList.add("Mango");
        titleList.add("Orange");
        titleList.add("Banana");
        titleList.add("Papaya");
    }

    @Resolve
    private void onResolved() {
        itemIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_keyboard_arrow_up_white_24dp));
        mainView.setBackgroundColor(Color.RED);
        itemNameTxt.setText(titleList.get(mParentPosition));
    }

    @Expand
    private void onExpand(){
        itemIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_keyboard_arrow_down_white_24dp));
    }

    @Collapse
    private void onCollapse(){
        itemIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_keyboard_arrow_up_white_24dp));
    }

//    @Click(R.id.mainView)
//    private void onMenuItemClick(){
//
//    }
}
