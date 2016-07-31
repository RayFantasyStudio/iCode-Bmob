package rayfantasy.icode.Util;

import android.app.*;
import android.content.*;
import android.util.*;
import android.view.*;
import java.util.*;
import rayfantasy.icode.R;

public class TransitionUtil {
    public static void startActivity(Activity activity, Intent intent, Pair<? extends View, String>... pairs) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ArrayList<Pair<? extends View, String>> pairList = new ArrayList<>(Arrays.asList(pairs));

            View statusbar = activity.findViewById(android.R.id.statusBarBackground);
            if (statusbar != null)
                pairList.add(Pair.create(statusbar, Window.STATUS_BAR_BACKGROUND_TRANSITION_NAME));

            View navbar = activity.findViewById(android.R.id.navigationBarBackground);
            if (navbar != null)
                pairList.add(Pair.create(navbar, Window.NAVIGATION_BAR_BACKGROUND_TRANSITION_NAME));

            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(activity, pairList.toArray(new Pair[pairList.size()]));
            activity.startActivity(intent, options.toBundle());
        } else {
            activity.startActivity(intent);
        }
        activity.overridePendingTransition(R.anim.slide_up, R.anim.scale_down);
    }
}

