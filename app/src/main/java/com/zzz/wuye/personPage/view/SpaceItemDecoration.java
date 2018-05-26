package com.zzz.wuye.personPage.view;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by oruret on 2018/3/15.
 */

public class SpaceItemDecoration extends RecyclerView.ItemDecoration {
    int mSpace;
    boolean itop = false,iright = false,ibottom = false,ileft = false;
    /**
     * Retrieve any offsets for the given item. Each field of <code>outRect</code> specifies
     * the number of pixels that the item view should be inset by, similar to padding or margin.
     * The default implementation sets the bounds of outRect to 0 and returns.
     * <p>
     * <p>
     * If this ItemDecoration does not affect the positioning of item views, it should set
     * all four fields of <code>outRect</code> (left, top, right, bottom) to zero
     * before returning.
     * <p>
     * <p>
     * If you need to access Adapter for additional data, you can call
//     * {@link RecyclerView#getChildAdapterPosition(View)} to get the adapter position of the
     * View.
     *
     * @param outRect Rect to receive the output.
     * @param view    The child view to decorate
     * @param parent  RecyclerView this ItemDecoration is decorating
     * @param state   The current state of RecyclerView.
     */
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        if(itop)outRect.top = mSpace;
        if(iright)outRect.right = mSpace;
        if(ibottom)outRect.bottom = mSpace;
        if(ileft)outRect.left = mSpace;

    }

    public SpaceItemDecoration(int space,boolean itop,boolean iright,boolean ibottom,boolean ileft) {
        this.mSpace = space;
        this.itop = itop;
        this.iright = iright;
        this.ibottom = ibottom;
        this.ileft = ileft;
    }
}
