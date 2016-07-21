package rayfantasy.icode.Ui.SwipeLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;

import rayfantasy.icode.Ui.SwipeLayout.view.SwipeMenuLayout;
import rayfantasy.icode.Ui.SwipeLayout.view.SwipeMenuView;


public class SwapWrapperUtils {

    /***
     * 包装RecyclerView的item
     *
     * @param parent            parent
     * @param layoutId          layoutId
     * @param menuView          menuView 菜单的view
     * @param closeInterpolator closeInterpolator 关闭的差值器
     * @param openInterpolator  openInterpolator  打开的差值器
     * @return SwipeMenuLayout 包装完后的SwipeMenuLayout
     */
    public static SwipeMenuLayout wrap(ViewGroup parent, int layoutId, SwipeMenuView menuView, Interpolator closeInterpolator, Interpolator openInterpolator) {
        View contentView = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
        SwipeMenuLayout swipeMenuLayout = new SwipeMenuLayout(contentView, menuView, closeInterpolator, openInterpolator);
        return swipeMenuLayout;
    }
}
