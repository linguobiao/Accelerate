package com.lgb.accelerate.utils;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;

import com.lgb.accelerate.R;

public class FragmentUtils {

	public static class FragmentUtilsInstance {
		private static final FragmentUtils instance = new FragmentUtils();
	}

	public static FragmentUtils getInstance() {
		return FragmentUtilsInstance.instance;
	}

	public FragmentUtils() {

	}

	public void setfMgr(FragmentManager fMgr) {
		this.fMgr = fMgr;
	}

	private FragmentManager fMgr;


	public static final String FRAGMENT_DASHBOARD = "FRAGMENT_DASHBOARD";
	public static final String FRAGMENT_DASHBOARD_DETAIL = "FRAGMENT_DASHBOARD_DETAIL";
	public static final String FRAGMENT_DASHBOARD_MONTH = "FRAGMENT_DASHBOARD_MONTH";
	public static final String[] COUNT_FRAGMENT_DASHBOARD = {FRAGMENT_DASHBOARD_DETAIL, FRAGMENT_DASHBOARD_MONTH};

	public static final String FRAGMENT_TRACKS = "FRAGMENT_TRACKS";
	public static final String FRAGMENT_TRACKS_ADD = "FRAGMENT_TRACKS_ADD";
	public static final String[] COUNT_FRAGMENT_TRACKS = {FRAGMENT_TRACKS_ADD};

	public static final String FRAGMENT_GOALS = "FRAGMENT_GOALS";
	public static final String FRAGMENT_GOALS_DAILY = "FRAGMENT_GOALS_DAILY";
	public static final String[] COUNT_FRAGMENT_GOALS = {FRAGMENT_GOALS_DAILY};

	public static final String FRAGMENT_FRIENDS = "FRAGMENT_FRIENDS";
	public static final String[] COUNT_FRAGMENT_FRIENDS = {};

	public static final String FRAGMENT_SETTINGS = "FRAGMENT_SETTINGS";
	public static final String FRAGMENT_SETTINGS_PROFILE = "FRAGMENT_SETTINGS_PROFILE";
	public static final String FRAGMENT_SETTINGS_UNIT = "FRAGMENT_SETTINGS_UNIT";
	public static final String FRAGMENT_SETTINGS_FAQ = "FRAGMENT_SETTINGS_FAQ";
	public static final String[] COUNT_FRAGMENT_SETTINGS = {FRAGMENT_SETTINGS_PROFILE, FRAGMENT_SETTINGS_UNIT, FRAGMENT_SETTINGS_FAQ};

	public static final String[] COUNT_FRAGMENT_ALL = {FRAGMENT_DASHBOARD, FRAGMENT_DASHBOARD_DETAIL, FRAGMENT_DASHBOARD_MONTH, FRAGMENT_TRACKS, FRAGMENT_TRACKS_ADD, FRAGMENT_GOALS, FRAGMENT_GOALS_DAILY, FRAGMENT_FRIENDS, FRAGMENT_SETTINGS, FRAGMENT_SETTINGS_PROFILE, FRAGMENT_SETTINGS_UNIT, FRAGMENT_SETTINGS_FAQ};


	/**
	 * 返回一级主界面
	 * @param cls
	 * @param mainTag
	 */
	public void returnMainFragment(Class cls, String mainTag) {
		if (mainTag.equals(FRAGMENT_DASHBOARD)) {
			for (String tag : COUNT_FRAGMENT_DASHBOARD) {
				removeFragment(tag);
			}
		} else if (mainTag.equals(FRAGMENT_TRACKS)) {
			for (String tag : COUNT_FRAGMENT_TRACKS) {
				removeFragment(tag);
			}
		} else if (mainTag.equals(FRAGMENT_GOALS)) {
			for (String tag : COUNT_FRAGMENT_GOALS) {
				removeFragment(tag);
			}
		} else if (mainTag.equals(FRAGMENT_FRIENDS)) {
			for (String tag : COUNT_FRAGMENT_FRIENDS) {
				removeFragment(tag);
			}
		} else if (mainTag.equals(FRAGMENT_SETTINGS)) {
			for (String tag : COUNT_FRAGMENT_SETTINGS) {
				removeFragment(tag);
			}
		}

		showFragment(cls, mainTag);
	}

	/**
	 * 移除所有子界面
	 * @param cls
	 * @param mainTag
	 */
	public void removeSonFragment(Class cls, String mainTag) {
		if (mainTag.equals(FRAGMENT_DASHBOARD)) {
			for (String tag : COUNT_FRAGMENT_DASHBOARD) {
				removeFragment(tag);
			}
		} else if (mainTag.equals(FRAGMENT_TRACKS)) {
			for (String tag : COUNT_FRAGMENT_TRACKS) {
				removeFragment(tag);
			}
		} else if (mainTag.equals(FRAGMENT_GOALS)) {
			for (String tag : COUNT_FRAGMENT_GOALS) {
				removeFragment(tag);
			}
		} else if (mainTag.equals(FRAGMENT_FRIENDS)) {
			for (String tag : COUNT_FRAGMENT_FRIENDS) {
				removeFragment(tag);
			}
		} else if (mainTag.equals(FRAGMENT_SETTINGS)) {
			for (String tag : COUNT_FRAGMENT_SETTINGS) {
				removeFragment(tag);
			}
		}
	}

	public boolean isVisible(String tag) {
		Fragment fragment = fMgr.findFragmentByTag(tag);
		if (fragment != null) {
			return fragment.isVisible();
		}
		return false;
	}


	private boolean showSon(boolean hasSon, String tag) {
		Fragment fragment = fMgr.findFragmentByTag(tag);
		if (fragment != null) {
			hasSon = true;
			showFragment(fragment);
		}
		return hasSon;
	}

	public void showMainFragment(Class cls, String mainTag) {
		if (fMgr != null) {

			boolean hasSon = false;
			if (mainTag.equals(FRAGMENT_DASHBOARD)) {
				for (String tag : COUNT_FRAGMENT_DASHBOARD) {
					hasSon = showSon(hasSon, tag);
					if (hasSon) break;
				}
			} else if (mainTag.equals(FRAGMENT_TRACKS)) {
				for (String tag : COUNT_FRAGMENT_TRACKS) {
					hasSon = showSon(hasSon, tag);
					if (hasSon) break;
				}
			} else if (mainTag.equals(FRAGMENT_GOALS)) {
				for (String tag : COUNT_FRAGMENT_GOALS) {
					hasSon = showSon(hasSon, tag);
					if (hasSon) break;
				}
			} else if (mainTag.equals(FRAGMENT_FRIENDS)) {
				for (String tag : COUNT_FRAGMENT_FRIENDS) {
					hasSon = showSon(hasSon, tag);
				}
			} else if (mainTag.equals(FRAGMENT_SETTINGS)) {
				for (String tag : COUNT_FRAGMENT_SETTINGS) {
					hasSon = showSon(hasSon, tag);
					if (hasSon) break;
				}
			}

			if (!hasSon) {
				showFragment(cls, mainTag);
			}

		}
	}

	/**
	 * 隐藏所有fragment
	 */
	public void hideAllFragment() {

		FragmentTransaction ft = fMgr.beginTransaction();

		for (String tag : COUNT_FRAGMENT_ALL) {
			Fragment fragment = fMgr.findFragmentByTag(tag);
			if (fragment != null) {
				ft.hide(fragment);
			}
		}

		ft.commitAllowingStateLoss();
	}

	/**
	 * 添加fragment
	 * 
	 * @param fragment
	 * @param fragmentTag
	 */
	public void addFragment(Fragment fragment, String fragmentTag) {

		if (fMgr != null) {
			FragmentTransaction ft = fMgr.beginTransaction();
			if (fragment != null) {
				ft.add(R.id.layout_content, fragment, fragmentTag);
				ft.commit();
			}
		}
	}

	/**
	 * 移除一个fragment
	 * 
	 * @param tag
	 */
	public void removeFragment(String tag) {

		Fragment fragment = fMgr.findFragmentByTag(tag);
		if (fragment != null) {
			FragmentTransaction ft = fMgr.beginTransaction();

			ft.remove(fragment);
			ft.commit();
		}
	}

	/**
	 * 显示fragment
	 * 
	 * @param fragment
	 */
	public void showFragment(Fragment fragment) {
		hideAllFragment();

		FragmentTransaction ft = fMgr.beginTransaction();
		if (fragment != null) {
			ft.show(fragment);
			ft.commit();
		}
	}

	public void showFragment(Class cls, String tag) {
		Fragment fragment = fMgr.findFragmentByTag(tag);
		if (fragment != null) {
			showFragment(fragment);
		} else {

			try {
				fragment = (Fragment) cls.newInstance();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}

			hideAllFragment();
			addFragment(fragment, tag);
		}

	}

}
