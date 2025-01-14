package utils;

/**
 * 상수 정의 클래스
 * */
public class Constant {

    //! ------------------------- Xpath ------------------------- //
    public static final String 독서_xPath = "//androidx.appcompat.app.ActionBar.Tab[@content-desc=\"독서\"]/android.view.ViewGroup/android.widget.TextView";
    public static final String 학습_xPath = "//androidx.appcompat.app.ActionBar.Tab[@content-desc=\"학습\"]/android.view.ViewGroup/android.widget.TextView";
    public static final String 라이브러리_xPath = "//androidx.appcompat.app.ActionBar.Tab[@content-desc=\"라이브러리\"]/android.view.ViewGroup/android.widget.TextView";
    public static final String toast_xPath = "/hierarchy/android.widget.Toast";
    /*스마트올 키즈 학년일때 첫 진입시 뜨는 인사 팝업종료 */
    public static final String kidsHello = "//android.widget.LinearLayout[@content-desc=\"웅진 스마트씽크빅\"]/android.widget.ImageView";

    //! ------------------------- ID -------------------------- //
    public static final String 검색_id = "com.wjthinkbig.mlauncher2:id/btnSearch";
    public static final String 검색창_id = "com.wjthinkbig.dictionary:id/input_txt";
    public static final String 검색실행_id = "com.wjthinkbig.dictionary:id/search";
    public static final String 사전_id = "com.wjthinkbig.mepubviewer2:id/btn_right_dictionary";
    public static final String 더보기_id = "com.wjthinkbig.mepubviewer2:id/btnShowButtons";
    public static final String 안내팝업메시지_id = "com.wjthinkbig.mepubviewer2:id/text_confirm_sub";
    public static final String 안내팝업확인_id = "com.wjthinkbig.mepubviewer2:id/btn_ok";
    public static final String 안내팝업취소_id = "com.wjthinkbig.mepubviewer2:id/btn_cancel";
    public static final String 뒤로가기_id = "com.wjthinkbig.mlauncher2:id/btn_back";
    public static final String 독서앨범_id = "com.wjthinkbig.mlauncher2:id/btnAlbum";
    public static final String 스마트올백과_id = "com.wjthinkbig.mlauncher2:id/btnDictionary";

    public static final String 웅진스마트올_id = "com.wjthinkbig.mlauncher2:id/btnSmartAll";
    public static final String 프로필_id = "com.wjthinkbig.mlauncher2:id/btnProfile";
    public static final String viewerClose_id = "com.wjthinkbig.mepubviewer2:id/btnFirst";
    public static final String commonBackButton_id = "com.wjthinkbig.thinkplayground:id/common_back_button";
    public static final String helpViewLayout_id = "com.wjthinkbig.mepubviewer2:id/layout_help";
    public static final String helpViewXBtn_id = "com.wjthinkbig.mepubviewer2:id/button_help_close";
    public static final String cartoonHelpLayoutCloseBtn_id = "com.wjthinkbig.mepubviewer2:id/button_help_close_by_cartoon";
    public static String 전체메뉴단계고르기_xPath = "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout[2]/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/androidx.viewpager.widget.ViewPager/android.view.ViewGroup/android.view.ViewGroup/androidx.viewpager.widget.ViewPager/androidx.recyclerview.widget.RecyclerView/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.widget.ImageView";
    public static String 북클럽닫기_id = "com.wjthinkbig.mlauncher2:id/btn_dialog_toolbar_close";
}
